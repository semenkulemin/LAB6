package Server.Commands; /**
 * Команда info, используемая для вывода в стандартный поток вывода информацию о коллекции
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

public class Info implements Command {
    static Logger LOG;
    private CurrentCollection collection;

    public Info(CommandLauncher l, CurrentCollection c){
        l.addCommand("info", this);
        this.collection=c;
        LOG = Logger.getLogger(Info.class.getName());
    }

    @Override
    public void execute(String option, List<String> args, IOChannel io) throws IOException {
        LOG.log(Level.INFO, "Отправка результат на сервер");
        io.writeln("Тип коллекции: " + collection.getTypeOfColl() + "\nДата создания коллекции: " + collection.getDataOfColl() + "\nКоличество элементов коллекции: " + collection.getSizeOfColl());
    }
}
