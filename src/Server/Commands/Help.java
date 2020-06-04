package Server.Commands;
import Server.Collection.CommandLauncher;
import Server.Commands.Command;
import Server.IOChannel;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Help implements Command {
    static Logger LOG;

    public Help(CommandLauncher l){
        l.addCommand("help", this);
        LOG = Logger.getLogger(Help.class.getName());
    }

    @Override
    public void execute(String option, List<String> args, IOChannel io) throws IOException {
        LOG.log(Level.INFO, "Отправка результат на сервер");
        io.writeln("help:вывести справку по доступным командам\n" +
                "info:вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show:вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "history : вывести последние 13 команд (без их аргументов)\n" +
                "filter_by_type type : вывести элементы, значение поля type которых равно заданному\n" +
                "filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки\n" +
                "filter_greater_than_postal_address postalAddress : вывести элементы, значение поля postalAddress которых больше заданного");
    }
}
