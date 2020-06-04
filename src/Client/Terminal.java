package Client;

import java.io.*;

/**
 * Осуществляет работу с потоками ввода и вывода
 */
public class Terminal implements IOStream {
    private Writer writer;
    private transient InputStream in;
    private transient OutputStream out;
    private transient BufferedReader bufferedReader;

    public Terminal(InputStream in, OutputStream out){
        this.in = in;
        this.out = out;
        writer = new OutputStreamWriter(out);
        bufferedReader = new BufferedReader((new InputStreamReader(in)));
    }

    @Override
    public void writeln(String str) throws IOException {
        writer.write(str + "\n");
        writer.flush();
    }

    @Override
    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public boolean ready() throws IOException {
        return bufferedReader.ready();
    }

    @Override
    public void writeObj(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);
        bufferedOutputStream.write(byteArrayOutputStream.toByteArray(),0, byteArrayOutputStream.toByteArray().length);
        bufferedOutputStream.flush();
    }

    @Override
    public Object readObj() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        return objectInputStream.readObject();
    }

    @Override
    public void close() throws IOException {
        out.close();
        in.close();
    }
}
