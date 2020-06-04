package Server.Commands;
/**
 * Команда clear, используемая для очистки коллекции
 */
import Server.Collection.CommandLauncher;
import Server.Collection.CurrentCollection;
import Server.Commands.Command;
import Server.IOChannel;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Clear implements Command {
    static Logger LOG;
    private CurrentCollection collection;
    public Clear(CommandLauncher l, CurrentCollection c){
        l.addCommand("clear", this);
        this.collection=c;
        LOG = Logger.getLogger(Clear.class.getName());
    }

    @Override
    public void execute(String option, List<String> args, IOChannel io) throws IOException {
        LOG.log(Level.INFO, "Отправка результат на сервер");
        if (collection.getSizeOfColl()==0){
            io.writeln("Коллекция пуста");
        }
        else{
            collection.clearList();
            io.writeln("Коллекция очищена");
        }
    }
}