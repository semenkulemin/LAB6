package Server;

import Client.IOStream;
import Client.ObjectOfCommand;
import Client.Terminal;
import Server.Collection.CurrentCollection;
import Server.Commands.Save;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static boolean flag;
    static Logger LOG;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectOfCommand currentCommand = null;
        ConnectionToClient connectionToClient = null;
        IOChannel ioClient = null;
        IOStream ioServer = new Terminal(System.in, System.out);

        LOG=Logger.getLogger(Server.class.getName());
        Integer PORT = Integer.parseInt(args[0]);
        try{
            LOG.log(Level.INFO, "Подключение к клиенту");
            connectionToClient = new ConnectionToClient();
            connectionToClient.connect(PORT);
        } catch (NumberFormatException e){
            LOG.log(Level.WARNING, "Ошибка при подключении к клиенту. Проблемы с форматом порта");
            System.out.println("Введен неправильный формат порта");
            System.exit(0);
        } catch (IndexOutOfBoundsException e){
            LOG.log(Level.WARNING, "Ошибка при подключении к клиенту, порт не указан");
            System.out.println("Нужно указать порт");
            System.exit(0);
        } catch (BindException e){
            PORT++;
            connectionToClient.connect(PORT);
        }

        TerminalLauncher launch = new TerminalLauncher();
        MapOfCommands map = new MapOfCommands(launch.getCommands());

        while(true){
            try{
                if(ioServer.ready()){
                    if (ioServer.readLine().equals("save")){
                        new Save(new CurrentCollection()).execute(args[1], null, null);
                    }
                    if (ioServer.readLine().equals("help")){
                        System.out.println("help:вывести справку по доступным командам\n" +
                                "info:вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                                "show:вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                                "add {element} : добавить новый элемент в коллекцию\n" +
                                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                                "clear : очистить коллекцию\n" +
                                "save : сохранить коллекцию в файл\n" +
                                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме\n" +
                                "exit : завершить программу (без сохранения в файл)\n" +
                                "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                                "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                                "history : вывести последние 13 команд (без их аргументов)\n" +
                                "filter_by_type type : вывести элементы, значение поля type которых равно заданному\n" +
                                "filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки\n" +
                                "filter_greater_than_postal_address postalAddress : вывести элементы, значение поля postalAddress которых больше заданного");
                    }
                }
            } catch (NullPointerException e){
                System.out.println("Завершение работы сервера");
                System.exit(1);
            }

            connectionToClient.getSelector().selectNow();
            Iterator iterator = connectionToClient.getSelector().selectedKeys().iterator();
            if (iterator.hasNext()) {
                LOG.log(Level.INFO, "Подключение селектора");
                SelectionKey selectionKey = (SelectionKey) iterator.next();
                iterator.remove();
                try{
                    LOG.log(Level.INFO, "Проверка ключа селектора");
                    if (!selectionKey.isValid()){
                        continue;
                    }
                    if (selectionKey.isAcceptable()){
                        LOG.log(Level.INFO, "Разрешение подключение клиента");
                        connectionToClient.acceptConnection(selectionKey);
                        flag = false;
                    }
                    if (selectionKey.isWritable()){
                        if(!flag){
                            ioClient = new IOClient((SocketChannel) selectionKey.channel());
                            LOG.log(Level.INFO, "Отправление map");
                            ioClient.writeObj(map);
                            try {
                                LOG.log(Level.INFO, "Загрузка содержимого файла");
                                File file = new File(args[1]);
                                if (file.canRead()){
                                    CurrentCollection collection = new CurrentCollection();
                                    collection.fromFileToCollection(file);
                                    ioClient.writeln("Содержимое файла записано в коллекцию");
                                } else {
                                    LOG.log(Level.INFO, "Беды с правами");
                                    ioClient.writeln("Чтение коллекции из файла не возможно. Коллекция пуста");
                                }
                            } catch (FileNotFoundException e) {
                                ioClient.writeln("Файл не найден. Коллекция пуста");
                            }
                             flag = true;

                        } else {
                            LOG.log(Level.INFO, "Запуск команды от клиента");
                            launch.beginProgramm(currentCommand, (IOClient) ioClient);
                        }
                        selectionKey.interestOps(SelectionKey.OP_READ);
                    }
                    if (selectionKey.isReadable()) {
                        LOG.log(Level.INFO, "Чтение полученной команды");
                        ioClient = new IOClient((SocketChannel) selectionKey.channel());
                        currentCommand = (ObjectOfCommand) ioClient.readObj();
                        selectionKey.interestOps(SelectionKey.OP_WRITE);
                    }
                } catch (ConnectException e){
                    System.out.println(e.getMessage());
                    flag = false;
                } catch (CancelledKeyException e){
                    ioServer.writeln("Работа с данным клиентом окончена");
                    connectionToClient.closeServerSocketChannel();
                    connectionToClient.connect(Integer.parseInt(args[0]));
                }
            }
        }
    }
}
