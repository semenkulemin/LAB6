package Server.Commands;

import Server.Collection.CurrentCollection;
import Server.Commands.Command;
import Server.IOChannel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Save implements Command {
    private CurrentCollection collection;

    public Save(CurrentCollection c){
        this.collection=c;
    }
    @Override
    public void execute(String option, List<String> args, IOChannel io) throws IOException {
        System.out.println(collection.saveCollectionToFile(new File(option)));
    }
}
