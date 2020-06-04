package Client;

import Server.MapOfCommands;

import java.util.List;

public class Validation {
    List<String> listOfCommands;
    String command;
    String option;

    public Validation(String command, String option, MapOfCommands commands){
        this.listOfCommands = commands.getMap();
        listOfCommands.add("execute_script");
        this.command = command;
        this.option = option;
    }

    public boolean validative(){
        boolean flag = false;
        for (String c: listOfCommands){
            if (c.equals(command)) flag = true;
        }

        if (!flag){
            System.out.println("Данная команда не существует. Список действующих комманд доступен при помощи команды \"help\"");
            return flag;
        }

        if (command.equals("remove_by_id") || command.equals("update") ){
            try{
                Long.parseLong(option);
            } catch (NumberFormatException e) {
                System.out.println("Команда не может быть выпонена. Введён неверный id. Повторите ввод команды");
                flag =  false;
            }
        }

        if (command.equals("filter_by_type")){
            if (!((option.toUpperCase().equals("PUBLIC") || option.toUpperCase().equals("GOVERNMENT") || option.toUpperCase().equals("TRUST") || option.toUpperCase().equals("PRIVATE_LIMITED_COMPANY") || option.toUpperCase().equals("OPEN_JOINT_STOCK_COMPANY")))) {
                System.out.println("Команда не может быть выпонена. Введён неверный type. Повторите ввод команды");
                flag =  false;
            }
        }

        return flag;
    }
}
