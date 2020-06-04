package Server.Collection; /**
 * Класс, используемый для хранения и запуска команд
 */
import Server.Commands.Command;
import Server.IOChannel;

import java.io.IOException;
import java.util.*;

public class CommandLauncher {
    /** Коллекция типа Map для хранения команд*/
    private Map<String, Command> Commands = new HashMap<>();
    private List<String> lastCommands = new ArrayList<>();
    private List<String> listOfCommands = new ArrayList<>();
    private int amountOfCommands = 0;
    /**
     * Функция, используемая для добавления команды в коллекцию Map
     */
    public void addCommand(String key,Command command){
        Commands.put(key,command);
        listOfCommands.add(key);
    }
    public List<String> getListOfCommands(){
        return listOfCommands;
    }
    /**
     * Функция, используемая для исполнения команды
     */
    public void executeCommand(String key, String option, List<String> args, IOChannel io) throws IOException {
        try{
            if (amountOfCommands == 13) {
                amountOfCommands = 0;
            }
            Commands.get(key).execute(option, args, io);
            lastCommands.add(amountOfCommands, key);
            amountOfCommands++;
            if(lastCommands.get(amountOfCommands) != null){
                lastCommands.remove(amountOfCommands);
            }
        } catch (IndexOutOfBoundsException e){

        }
    }



    public String getLastCommands() {
        StringBuilder stringBuilder = new StringBuilder();
        /*if (lastCommands.size() != 13) {
            return "Вы использовали меньше 13 команд. Команда не выполнится, могли бы и сами запомнить)";
        } else {
            lastCommands.forEach(commands -> stringBuilder.append(commands + "\n"));
        }*/
        lastCommands.forEach(commands -> stringBuilder.append(commands + "\n"));
        return "Команда выполнена." + stringBuilder.toString();
    }

    public void clearLastCommands(){
        lastCommands.clear();
    }

}

