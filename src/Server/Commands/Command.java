package Server.Commands; /**
 * Интерфейс, наследуемый классами всех команд
 */
import Server.IOChannel;

import java.io.IOException;
import java.util.List;

public interface Command {
     void execute(String option, List<String> args, IOChannel io) throws IOException;
}
