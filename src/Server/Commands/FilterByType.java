package Server.Commands;
import Server.Collection.CommandLauncher;
import Server.Collection.CurrentCollection;
import Server.Collection.ObjectsOfOrganizations;
import Server.Commands.Command;
import Server.IOChannel;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilterByType implements Command {
    ObjectsOfOrganizations objects = new ObjectsOfOrganizations();
    private CurrentCollection collection;
    static Logger LOG;

    public FilterByType(CommandLauncher l, CurrentCollection c){
        l.addCommand("filter_by_type", this);
        this.collection=c;
        LOG = Logger.getLogger(FilterByType.class.getName());
    }

    @Override
    public void execute(String option, List<String> args, IOChannel io) throws IOException {
        LOG.log(Level.INFO, "Отправка результат на сервер");
        if (option == null){
            objects.setMessage("Команда не выполнится. Введите аргумент программы");
            objects.setOrganization(null);
            io.writeObj(objects);
        } else {
                collection.sortByName().stream().filter(organization -> organization.getType().toString().equals(option.toUpperCase())).forEach(organization -> {
                    try{
                        objects.setOrganization(organization);
                        io.writeObj(objects);
                        objects.setMessage("");
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                });
        }
    }
}
