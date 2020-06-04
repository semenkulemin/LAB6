package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionToServer {
    Socket socket;

    public Socket connection(String HOST, String PORT) throws IOException {
        try{
            socket = new Socket(HOST, Integer.parseInt(PORT));
            System.out.println("Соединение с сервером установлено");
            return socket;
        } catch (UnknownHostException e){
            System.out.println("Не удалось установить соединение с сервером");
            System.exit(0);
            return null;
        } catch (NumberFormatException e){
            System.out.println("Некорректно введен порт");
            System.exit(0);
            return null;
        }
    }

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    public void close() throws IOException {
        socket.close();
    }
}
