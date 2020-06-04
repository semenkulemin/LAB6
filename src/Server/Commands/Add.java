package Server.Commands;
import Server.Collection.CommandLauncher;
import Server.Collection.CurrentCollection;
import Server.Collection.Organization;
import Server.IOChannel;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Add implements Command {
    CurrentCollection collection;
    static Logger LOG;

    public Add(CommandLauncher l, CurrentCollection c){
        l.addCommand("add", this);
        this.collection=c;
        LOG = Logger.getLogger(Add.class.getName());
    }

    @Override
    public void execute(String option, List<String> args, IOChannel io) throws IOException {
        collection.addElement(new Organization(args));
        LOG.log(Level.INFO, "Отправка результата на сервер");
        io.writeln("Команда выполнена");
    }
}
