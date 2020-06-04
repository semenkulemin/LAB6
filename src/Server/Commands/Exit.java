package Server.Commands;

import Server.Collection.CommandLauncher;
import Server.Collection.CurrentCollection;
import Server.Collection.Organization;
import Server.IOChannel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Exit implements Command {
    CurrentCollection collection;
    CommandLauncher l;
    static Logger LOG;

    public Exit(CommandLauncher l, CurrentCollection c){
        l.addCommand("exit", this);
        this.collection=c;
        this.l = l;
        LOG = Logger.getLogger(Exit.class.getName());
    }

    @Override
    public void execute(String option, List<String> args, IOChannel io) throws IOException {
        LOG.log(Level.INFO, "Отправка результата на сервер");
        io.writeln(collection.saveCollectionToFile(new File("C:\\Users\\mi\\Desktop\\untitled2\\src\\Organizations.json")));
        collection.clearList();
        l.clearLastCommands();
        io.writeln("Команда exit выполняется. Завершение работы");
        io.writeln("exit");
    }
}
