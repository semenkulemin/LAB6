package Server.Commands;
import Server.Collection.CommandLauncher;
import Server.Collection.CurrentCollection;
import Server.Collection.Organization;
import Server.Commands.Command;
import Server.IOChannel;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddIfMin implements Command {
    static Logger LOG;
    private CurrentCollection collection;
    public AddIfMin(CommandLauncher l, CurrentCollection c){
        l.addCommand("add_if_min", this);
        this.collection=c;
        LOG = Logger.getLogger(AddIfMin.class.getName());
    }

    @Override
    public void execute(String option, List<String> args, IOChannel io) throws IOException {
        LOG.log(Level.INFO, "Отправка результат на сервер");
        if (collection.getSizeOfColl() == 0) {
            io.writeln("Коллекция пуста");
            collection.addElement(new Organization(args));
        } else {
            if (collection.findMin() > Float.parseFloat(args.get(3))) {
                io.writeln("Элемент меньше минимального");
                collection.addElement(new Organization(args));
            } else {
                io.writeln("Элемент больше минимального");
            }
        }
    }
}
