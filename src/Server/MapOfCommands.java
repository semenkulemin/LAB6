package Server;

import java.io.Serializable;
import java.util.List;

public class MapOfCommands implements Serializable {
    private List<String> commands;
    public MapOfCommands(List<String> commands){
        this.commands = commands;
    }

    public List<String> getMap(){
        return commands;
    }
}
