package Server.Collection;

import Server.Collection.Address;
import Server.Collection.Coordinates;
import Server.Collection.Organization;
import Server.Collection.OrganizationType;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CurrentCollection {
    /** Поле дата создания коллекции */
    private static Date date;
    /** Блок инициализации*/
    static {
        date = new Date();
    }
    /** Коллекция экземпляров класса Server.Collection.Organization*/
    private static ArrayDeque<Organization> collection = new ArrayDeque<>( );

    public void addElement(Organization organization){
        collection.addLast(organization);
    }

    public String getTypeOfColl() {
        return collection.getClass().getTypeName();
    }

    public int getSizeOfColl() {
        return collection.size();
    }

     public String getDataOfColl(){
        return date.toString();
    }


    /*public void getAllElements(){
        for(Organization organization : collection){
            System.out.println(organization.toString());
        }
    }*/

    public void clearList() {
        collection.clear();
    }

    public void fromFileToCollection(File file) {
        try{
            if (file.canRead()){
                Gson gson = new Gson();
                Scanner scanner = new Scanner(file);
                while(scanner.hasNextLine()){
                    collection.addLast(gson.fromJson(scanner.nextLine(), Organization.class));
                }
            } else {
                System.out.println("Недостаточно прав");
            }
        } catch(IOException e){
            System.out.println("Файл не существует, коллекция не заполнена");
           e.printStackTrace();
        }
    }

    /**
     * Функция сохранения коллекция в файл
     */
    public String saveCollectionToFile(File file) throws IOException {
        Gson gson = new Gson();
        if(file.canWrite()) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            collection.forEach(city -> {
                try {
                    fileOutputStream.write(gson.toJson(city).getBytes());
                    fileOutputStream.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } );
            fileOutputStream.close();
            return "Коллекция сохранена в файл" + file.getName();
        } else {
            return "Недостаточно прав";
        }
    }

    /**
     * Функция, исполняющая команду filter_by_type
     * @param option - строка, по которой происходит поиск
     */
    /*public void filterByType(String option){
        int flag = 0;
        for(int i = 0 ; i < collection.size() ;i++){
            Organization org = collection.pollFirst();
            if(org.getType().toString().equals(option)){
                System.out.println(org.toString());
                flag++;

            }
            collection.addLast(org);
        }
        if (flag == 0){System.out.println("Элементов со значением type, равным " + option + ", не найдено");}
    }*/

    /**
     * Функция, осуществляющая удаление элемента по id
     * @param option - id, по которому выполняется удаление
     * @return измененная коллекция
     */
    public ArrayDeque<Organization> removeById(Long option){
        ArrayDeque<Organization> collection1;
        collection1 = collection;
        for(int i = 0 ; i < collection1.size() ;i++){
            Organization org = collection1.pollFirst();
            if(!org.getIdOfOrganization().equals(option)){
                collection1.addLast(org);
            }
        }
        return collection1;
    }

    /**
     * Функция, исполняющая команду filter_greater_than_postal_address
     * @param option - аргумент программы
     */
    /*public void filterGreaterThanPostalAddress(String option){
        int flag = 0;
        for(int i = 0 ; i < collection.size() ;i++){
            Organization org = collection.pollFirst();
            if (org.getPostalAddress().getZipCode().compareTo(option) > 0){
                System.out.println(org.toString());
                flag++;
            }
            collection.addLast(org);
        }
        if (flag == 0){System.out.println("Элементов, поля postalOffice, большие чем " + option + ", не найдено");}
    }*/

    /**
     * Функция, исполняющая команду filter_starts_with_name
     * @param option - аргумент программы
     */
    /*public void filterStartsWithName(String option){
        int flag = 0;
         for(int i = 0 ; i < collection.size() ;i++){
            Organization org = collection.pollFirst();
            if (org.getNameOfOrganization().indexOf(option) == 0){
                System.out.println(org.toString());
                flag++;
            }
            collection.addLast(org);
        }
        if (flag == 0){System.out.println("Элементов, поля name которых начинаются с " + option + ", не найдено");}
    }*/

    /**
     * Функция, выполняющая поиск максимального значения коллекции
     * @return максимальное значение
     */
    public float findMax(){
        if (collection.size() == 1){
            return collection.getFirst().getAnnualTurnover();
        } else {
            float max = 0;
            for(int i = 0 ; i < collection.size(); i++){
                Organization org = collection.pollFirst();
                if(org.getAnnualTurnover() > max){
                    max = org.getAnnualTurnover();
                }
                collection.addLast(org);
            }
            return max;
        }
    }

    /**
     * Функция, выполняющая поиск минимальное значения коллекции
     * @return минимальное значение
     */
    public float findMin(){
        if (collection.size() == 1){
            return collection.getFirst().getAnnualTurnover();
        } else {
            float min = (float) 3.4E+38;
            for(int i = 0 ; i < collection.size(); i++){
                Organization org = collection.pollFirst();
                if(org.getAnnualTurnover() < min){
                    min = org.getAnnualTurnover();
                }
                collection.addLast(org);
            }
            return min;
        }
    }

    public ArrayDeque<Organization> sortByName(){
        ArrayDeque<Organization> newCollection = collection.stream().sorted(new ComparatorByName()).collect(Collectors.toCollection(ArrayDeque::new));
        return newCollection;
    }

    /**
     * Функция, осуществляющая ввод элемента коллекции
     * @param id - id
     * @param scanner - поток
     * @return - экземпляр класса Server.Collection.Organization
     */
    /*public Organization getElement(Long id, Scanner scanner){
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

        System.out.println("Введите название улицы, на которой расположена организация");
        String street = scanner.nextLine();
        System.out.println("Задано название улицы, на которой расположена организация: " + street);

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

        System.out.println("Элемент создан");
        return new Organization(id, name, new Coordinates(x, y), annualTurnover, organizationType, new Address(street, zipCode));
    }*/
}