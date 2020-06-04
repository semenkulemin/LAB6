package Server.Commands;

import Server.Collection.CommandLauncher;
import Server.Collection.CurrentCollection;
import Server.Collection.ObjectsOfOrganizations;
import Server.IOChannel;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Show implements Command {
    ObjectsOfOrganizations objects = new ObjectsOfOrganizations();
    private CurrentCollection collection;
    static Logger LOG;

    public Show(CommandLauncher l, CurrentCollection c){
        l.addCommand("show", this);
        this.collection=c;
        LOG = Logger.getLogger(Show.class.getName());
    }

    @Override
    public void execute(String option, List<String> args, IOChannel io) throws IOException {
        LOG.log(Level.INFO, "Отправка результат на сервер");
        if (collection.getSizeOfColl() == 0){
            objects.setMessage("Коллекция пуста");
            objects.setOrganization(null);
            io.writeObj(objects);
        }else{
            objects.setMessage("Список элементов коллекции: \n");
            collection.sortByName().forEach(organization -> {
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
