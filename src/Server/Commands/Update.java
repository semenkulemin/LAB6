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

public class Update implements Command {

    private CurrentCollection collection;
    static Logger LOG;

    public Update(CommandLauncher l, CurrentCollection c){
        l.addCommand("update", this);
        this.collection=c;
        LOG = Logger.getLogger(Update.class.getName());
    }

    @Override
    public void execute(String option, List<String> args, IOChannel io) throws IOException {
        LOG.log(Level.INFO, "Отправка результат на сервер");
        if (option == null){
            io.writeln("Команда не выполнится. Введите аргумент программы");
        } else {
            int amount_before = collection.getSizeOfColl();
            collection.removeById(Long.parseLong(option));
            if (amount_before != collection.getSizeOfColl()) {
                collection.addElement(new Organization(args, Long.parseLong(option)));
                io.writeln("Команда выполнена");
            } else {
                io.writeln("Элемента с таким id не найдено.");
            }
        }
    }
}
