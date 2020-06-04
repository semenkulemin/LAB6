package Server.Commands;
import Server.Collection.CommandLauncher;
import Server.Commands.Command;
import Server.IOChannel;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class History implements Command {

    private CommandLauncher launch;
    static Logger LOG;
    public History(CommandLauncher l){
        l.addCommand("history", this);
        this.launch=l;
        LOG = Logger.getLogger(History.class.getName());
    }

    @Override
    public void execute(String option, List<String> args, IOChannel io) throws IOException {
        LOG.log(Level.INFO, "Отправка результат на сервер");
        io.writeln(launch.getLastCommands());
    }
}
