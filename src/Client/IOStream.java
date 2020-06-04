package Client;

import java.io.IOException;

/**
 * Команды потока ввода и вывода
 */
public interface IOStream {
    void writeln(String str) throws IOException;
    String readLine() throws IOException;
    boolean ready() throws IOException;
    void writeObj(Object object) throws IOException;
    Object readObj() throws IOException, ClassNotFoundException;
    void close() throws IOException;
}
