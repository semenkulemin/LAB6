package Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ConnectionToClient {
    ServerSocketChannel serverSocketChannel;
    Selector selector;
    SocketChannel channel;

    public void connect(Integer PORT) throws IOException {
        //создаем канал сервер сокета
        serverSocketChannel =ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        //создаем адрес сокета и связываем его
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        //создаем селектор
        selector=Selector.open();
        //регистрируем
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
    }

    public Selector getSelector(){
        return selector;
    }

    public void acceptConnection(SelectionKey key) throws IOException {
        //принимаем соединение к сокету канала
        ServerSocketChannel sschannel=(ServerSocketChannel)key.channel();
        channel=sschannel.accept();
        channel.configureBlocking(false);
        channel.register(selector,SelectionKey.OP_WRITE);
    }

    public SocketChannel getChannel(){
        return channel;
    }
    public void closeServerSocketChannel() throws IOException {
        serverSocketChannel.socket().close();
    }
}
