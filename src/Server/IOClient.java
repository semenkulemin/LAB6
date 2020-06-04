package Server;

import Server.Collection.CommandLauncher;
import Server.Collection.CurrentCollection;

import java.io.*;
import java.net.ConnectException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class IOClient implements IOChannel {
    SocketChannel socketChannel;

    public IOClient(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }

    @Override
    public void writeln(String str) throws IOException {
        ByteBuffer byteBuffer=ByteBuffer.wrap((str+"\n").getBytes());
        socketChannel.write(byteBuffer);
    }

    @Override
    public void writeObj(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream =new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
        socketChannel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
    }

    @Override
    public Object readObj() throws IOException, ClassNotFoundException {
        ByteBuffer byteBuffer=ByteBuffer.allocate(5*1024);
        try{
            socketChannel.read(byteBuffer);
            return new ObjectInputStream(new ByteArrayInputStream(byteBuffer.array())).readObject();
        }
        catch(IOException e){
            socketChannel.close();
            new CurrentCollection().clearList();
            new CommandLauncher().clearLastCommands();
            throw new ConnectException("Соединение с клиентом разорвано :(");

        }
    }

    @Override
    public void close() throws IOException {
        socketChannel.close();;
    }
}
