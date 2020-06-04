package Client;

import Server.Collection.OrganizationType;
import Server.MapOfCommands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ObjectOfCommand implements Serializable {
    private String nameOfCommand;
    private String option;
    private Boolean flag = false;
    private List<String> args = new ArrayList<>();
    private transient Scanner scanner = new Scanner(System.in);

    public ObjectOfCommand(String line, MapOfCommands map, String filename){
        if (!line.trim().equals("")){
            if (line.contains(" ")) {
                String[] partsOfLine = line.split(" ", 2);
                Validation validator = new Validation(partsOfLine[0], partsOfLine[1], map);
                flag = validator.validative();
                if (flag) {
                    nameOfCommand = partsOfLine[0];
                    System.out.println(nameOfCommand);
                    option = partsOfLine[1];
                    System.out.println(option);
                }
            } else {
                Validation validator = new Validation(line, null, map);
                flag = validator.validative();
                if (flag) {
                    nameOfCommand = line;
                    option = null;
                }
            }
        }
        if (nameOfCommand!=null && filename == null){
            if (nameOfCommand.equals("add") || nameOfCommand.equals("update") || nameOfCommand.equals("add_if_max") || nameOfCommand.equals("add_if_min")){
                setListOfArgs();
            }
        }
    }

    public String getNameOfCommand(){
        return nameOfCommand;
    }
    public Boolean getFlag(){
        return flag;
    }
    public String getOption(){
        return option;
    }
    public List<String> getArgs(){
        return args;
    }
    public void setArgs(List<String> args){
        this.args = args;
    }

    public void setListOfArgs(){
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
        System.out.println("Элемент создан");
    }
}
