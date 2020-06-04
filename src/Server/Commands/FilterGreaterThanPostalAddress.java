package Server.Commands;
import Server.Collection.CommandLauncher;
import Server.Collection.CurrentCollection;
import Server.Collection.ObjectsOfOrganizations;
import Server.Commands.Command;
import Server.IOChannel;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class FilterGreaterThanPostalAddress implements Command {
    ObjectsOfOrganizations objects = new ObjectsOfOrganizations();
    private CurrentCollection collection;
    static Logger LOG;

    public FilterGreaterThanPostalAddress(CommandLauncher l, CurrentCollection c){
        l.addCommand("filter_greater_than_postal_address", this);
        this.collection=c;
        LOG = Logger.getLogger(FilterGreaterThanPostalAddress.class.getName());
    }

    @Override
    public void execute(String option, List<String> args, IOChannel io) throws IOException {
        if (option == null){
            objects.setMessage("Команда не выполнится. Введите аргумент программы");
            objects.setOrganization(null);
            io.writeObj(objects);
        } else {
            collection.sortByName().forEach(organization -> {
                try {
                    if (organization.getPostalAddress().getZipCode().compareTo(option) > 0) {
                        objects.setOrganization(organization);
                        io.writeObj(objects);
                        objects.setMessage("");
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
    }
}
