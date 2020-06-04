package Server;
/**
 * Класс, обрабатывающий ввод команд
 */

import Client.ObjectOfCommand;
import Server.Collection.CommandLauncher;
import Server.Collection.CurrentCollection;
import Server.Commands.*;

import java.io.IOException;
import java.util.List;

public class TerminalLauncher {
    private CommandLauncher run = new CommandLauncher();
    private CurrentCollection collection = new CurrentCollection();

    public TerminalLauncher() {
        Command help = new Help(run);
        Command info = new Info(run, collection);
        Command show = new Show(run, collection);
        Command clear = new Clear(run, collection);
        Command history = new History(run);
        Command add = new Add(run, collection);
        Command update = new Update(run, collection);
        Command filter_by_type = new FilterByType(run, collection);
        Command remove_by_id = new RemoveById(run, collection);
        Command filter_starts_with_name = new FilterStartsWithName(run, collection);
        Command add_if_max = new AddIfMax(run, collection);
        Command add_if_min = new AddIfMin(run, collection);
        Command filter_greater_than_postal_address = new FilterGreaterThanPostalAddress(run, collection);
        Command save = new Save(collection);
        Command exit = new Exit(run, collection);
    }

    public List<String> getCommands(){
        return run.getListOfCommands();
    }

    public  void beginProgramm(ObjectOfCommand command, IOChannel io) throws IOException {
        run.executeCommand(command.getNameOfCommand(), command.getOption(), command.getArgs(), io);
    }
}