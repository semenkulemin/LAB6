package Client;
/**
 * @author Kulemin Semen
 * @version 1.0
 */

import Server.MapOfCommands;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    static Logger LOG;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        LOG = Logger.getLogger(Client.class.getName());
        IOStream ioClient = new Terminal(System.in, System.out);
        ConnectionToServer connectionToServer = new ConnectionToServer();
        LOG.log(Level.INFO, "Начало работы клиента");
        if (args.length == 2) {
            LOG.log(Level.INFO, "Подключение к серверу");
            try {
                connectionToServer.connection(args[0], args[1]);
            } catch (Exception e){
                System.out.println("Ошибка при соединении с сервером. ");
                System.exit(0);
            }
            ioClient.writeln("Вечер в хату! Введите \"help\", чтобы увидеть список всех команд");
        } else {
            LOG.log(Level.INFO, "Ошибка при соединении с сервером. Неверный хост и/или порт");
            ioClient.writeln("Ошибочка вышла. Введите верный хост и порт");
            System.exit(0);
        }

        IOStream ioServer = new Terminal(connectionToServer.getInputStream(), connectionToServer.getOutputStream());
        LOG.log(Level.INFO, "Ждем сервер");
        while(!ioServer.ready()){

        }
        LOG.log(Level.INFO, "Получение списка команд от сервера");
        MapOfCommands map = (MapOfCommands)ioServer.readObj();
        DeliveryClub deliveryClub = new DeliveryClub(ioServer, ioClient, map, connectionToServer);
        ioClient.writeln(ioServer.readLine());
        ioClient.writeln("Введите команду");

        LOG.log(Level.INFO, "Считываем команду");
        String line;
        while(true){
            try{
                line = ioClient.readLine();
                //if (!line.equals("exit")) {
                    ObjectOfCommand command;
                    while (true) {
                        LOG.log(Level.INFO, "Создание объекта текущей команды");
                        command = new ObjectOfCommand(line, map, null);
                        if (command.getFlag()) {
                            try {
                                LOG.log(Level.INFO, "Отправка команды на сервер");
                                deliveryClub.delivery(command);
                            } catch (StackOverflowError e) {
                                LOG.log(Level.WARNING, "Отправка команды на сервер", e);
                                ioClient.writeln("ПОПАЛСЯ... Зациклился  execute_script. ПОПАЛСЯ... ");
                            }
                        }
                        ioClient.writeln("Введите команду");
                        LOG.log(Level.INFO, "Считывание команды с консоли");
                        line = ioClient.readLine();
                    }
                //} else{
                  //  ioClient.writeln("Введите команду");
                    //System.exit(0);
                //}
            } catch (NullPointerException e){
                ioClient.writeln("Введите команду еще раз");
            }
        }
    }
}
