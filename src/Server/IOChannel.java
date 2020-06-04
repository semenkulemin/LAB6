package Server;

import java.io.IOException;

public interface IOChannel {
     void writeln(String str) throws IOException;
     void writeObj(Object obj) throws IOException;
     Object readObj() throws IOException, ClassNotFoundException;
     void close() throws IOException;
}
