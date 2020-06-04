package Client;

import Server.Collection.ObjectsOfOrganizations;
import Server.Collection.OrganizationType;
import Server.MapOfCommands;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliveryClub {
    static Logger LOG;
    private IOStream ioServer;
    private IOStream ioClient;
    private MapOfCommands map;
    private ConnectionToServer connectionToServer;
    public DeliveryClub(IOStream ioServer, IOStream ioClient, MapOfCommands map, ConnectionToServer connectionToServer){
        this.ioServer = ioServer;
        this.ioClient = ioClient;
        this.map = map;
        this.connectionToServer = connectionToServer;
        LOG = Logger.getLogger(DeliveryClub.class.getName());
    }
    public void delivery(ObjectOfCommand command) throws IOException, ClassNotFoundException {
        if (command.getNameOfCommand().equals("execute_script")){
            File file = new File(command.getOption());
            if (file.exists()){
                if (file.canRead()){
                    FileReader fileReader = new FileReader(file);
                    Scanner scanner = new Scanner(fileReader);
                    while (scanner.hasNextLine() && !(scanner.nextLine().equals("exit"))){
                        String lineOfFile = scanner.nextLine();
                        ObjectOfCommand objectOfCommand = new ObjectOfCommand(lineOfFile, map, command.getOption());
                        if (objectOfCommand.equals("add") || objectOfCommand.equals("update") || objectOfCommand.equals("add_if_max") || objectOfCommand.equals("add_if_min")){
                            List<String> args = new ArrayList<>();
                            System.out.println("Введите название организации. Например: ИТМО/МЕГАДУРКА");
                            Boolean flag0 = false;
                            String name = null;
                            while(!flag0){
                                name = scanner.nextLine();
                                if (!name.equals(null)){
                                    flag0 = true;
                                } else {
                                    System.out.println("Название строки не может быть null. Введите название снова");
                                }
                            }
                            System.out.println("Задано название организации: " + name);
                            args.add(name);

                            System.out.println("Введите координату организации по X");
                            Boolean flag1 = false;
                            Long x = 0L;
                            while(!flag1){
                                try{
                                    x = Long.parseLong(scanner.nextLine());
                                    flag1 = true;
                                } catch (NumberFormatException e) {
                                    System.out.println("Формат введенного значения неверный, введите координату ещё раз");
                                }

                            }
                            System.out.println("Задана координата организации по X: " + x);
                            args.add(x.toString());

                            System.out.println("Введите координату организации по Y. Значение должно быть больше -425");
                            Boolean flag2 = false;
                            Float y = 0F;
                            while(!flag2){
                                try{
                                    y = Float.parseFloat(scanner.nextLine());
                                    if (y>-425F) {
                                        flag2 = true;
                                    } else {
                                        System.out.println("Значение должно быть больше -425, введите координату снова");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Формат введенного значения неверный, введите координату снова");
                                }
                            }
                            System.out.println("Задана координата организации по Y: " + y);
                            args.add(y.toString());

                            System.out.println("Введите ежегодный оборот организации. Значение должно быть больше 0");
                            Float annualTurnover = 0F;
                            Boolean flag3 = false;
                            while(!flag3){
                                try{
                                    annualTurnover = Float.parseFloat(scanner.nextLine());
                                    if (annualTurnover>0F) {
                                        flag3 = true;
                                    } else {
                                        System.out.println("Значение должно быть больше 0, введите значение ежегодного оборота снова");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Формат введенного значения неверный, введите значение ежегодного оборота снова");
                                }
                            }
                            System.out.println("Задан ежегодный оборот организации: " + annualTurnover);
                            args.add(annualTurnover.toString());

                            System.out.println("Введите тип организации. Возможные виды:\n" +
                                    "    PUBLIC,\n" +
                                    "    GOVERNMENT,\n" +
                                    "    TRUST,\n" +
                                    "    PRIVATE_LIMITED_COMPANY,\n" +
                                    "    OPEN_JOINT_STOCK_COMPANY;");
                            OrganizationType organizationType = null;
                            Boolean flag4 = false;
                            while(!flag4){
                                String orgType = scanner.nextLine();
                                if (orgType.toUpperCase().equals("PUBLIC")){
                                    organizationType = OrganizationType.PUBLIC;
                                    flag4=true;
                                }
                                else if (orgType.toUpperCase().equals("GOVERNMENT")){
                                    organizationType = OrganizationType.GOVERNMENT;
                                    flag4=true;
                                }
                                else if (orgType.toUpperCase().equals("TRUST")){
                                    organizationType = OrganizationType.TRUST;
                                    flag4=true;
                                }
                                else if (orgType.toUpperCase().equals("PRIVATE_LIMITED_COMPANY")){
                                    organizationType = OrganizationType.PRIVATE_LIMITED_COMPANY;
                                    flag4=true;
                                }
                                else if (orgType.toUpperCase().equals("OPEN_JOINT_STOCK_COMPANY")){
                                    organizationType = OrganizationType.OPEN_JOINT_STOCK_COMPANY;
                                    flag4=true;
                                }
                                else {
                                    System.out.println("Введенное значение некорректно. Введите тип организации снова");
                                }
                            }
                            System.out.println("Задан тип организации: " + organizationType);
                            args.add(organizationType.toString());

                            System.out.println("Введите название улицы, на которой расположена организация");
                            String street = scanner.nextLine();
                            System.out.println("Задано название улицы, на которой расположена организация: " + street);
                            args.add(street);

                            System.out.println("Введите почтовый индекс");
                            Boolean flag5 = false;
                            String zipCode = null;
                            while(!flag5){
                                zipCode = scanner.nextLine();
                                if (!zipCode.equals(null)){
                                    flag5 = true;
                                } else {
                                    System.out.println("Почтовый индекс не может быть null. Введите почтовый индекс снова");
                                }
                            }
                            System.out.println("Задан почтовый индекс организации: " + zipCode);
                            args.add(zipCode);
                            objectOfCommand.setArgs(args);
                            System.out.println("Элемент создан");
                        }
                        if (objectOfCommand.getFlag()){
                            delivery(objectOfCommand);
                        }
                    }
                } else {
                    ioClient.writeln("Чтение из файла невозможно. Команда execute_script не выполнится");
                    return;
                }
            } else {
                ioClient.writeln("Файл не существует. Команда execute_script не выполнится");
                return;
            }
        } else {
            if (command.getFlag()){
                LOG.log(Level.INFO, "Отправка команды на сервер");
                ioServer.writeObj(command);

                long start = System.currentTimeMillis();
                LOG.log(Level.INFO, "Ожидание ответа сервера");
                while(!ioServer.ready()){
                    long now = System.currentTimeMillis();
                    if (now - start > 30000){
                        ioClient.writeln("Сервер недоступен. Завершение работы команды");
                        connectionToServer.close();
                        System.exit(0);
                    }
                }

                LOG.log(Level.INFO, "Получение ответа на команду и вывод на консоль");
                while (ioServer.ready()){
                    if ((command.getNameOfCommand().equals("show")) || (command.getNameOfCommand().equals("filter_by_type")) || (command.getNameOfCommand().equals("filter_starts_with_name")) ||(command.getNameOfCommand().equals("filter_greater_than_postal_address"))) {
                        ObjectsOfOrganizations objects = (ObjectsOfOrganizations) ioServer.readObj();
                        ioClient.writeln(objects.getMessage());
                        if (objects.getOrganization() != null){
                            ioClient.writeln(objects.getOrganization().toString());
                        }
                    } else {
                        String answer = ioServer.readLine();
                        if (answer.equals("exit")){
                            ioServer.close();
                            connectionToServer.close();
                            LOG.log(Level.INFO, "Завершение работы клиента");
                            System.exit(1);
                        }
                        ioClient.writeln(answer);
                    }
                }
            }
        }
    }
}
