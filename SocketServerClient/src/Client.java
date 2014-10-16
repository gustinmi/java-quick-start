

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class Client extends SocketClient {

    public Client(String server, int port) throws IOException {
        super(server, port);
    }

    @Override
    public boolean protocol() {
        StringBuilder buff = new StringBuilder();
        String s;
        int c;

        try {
            System.out.println(readFromSocket());
        } catch (IOException ex) {}

        while (true) {
            try {
                //read from standard input (terminal)
                while ((c = System.in.read()) != '\n') {
                    buff.append((char) c);
                }
                // write to SocketServer
                writeToSocket(buff.toString());
                buff.setLength(0);
                // read from SocketServer
                s = readFromSocket();
                System.out.println(" - " + s);
                if (s.equals("END")) {
                    break;
                }

            } catch (IOException ex) {}

        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client("localhost", 9999);
        c.protocol();
    }
}

abstract class SocketClient {

    Socket socket;
    final DataInputStream inStream;
    final PrintStream outStream;

    public SocketClient(String server, int port) throws IOException {
        socket = new Socket(server, port);
        inStream = new DataInputStream(socket.getInputStream());
        outStream = new PrintStream(socket.getOutputStream());
    }

    public void writeToSocket(String data) {
        outStream.println(data);
    }

    public String readFromSocket() throws IOException {
        return inStream.readLine();
    }

    public abstract boolean protocol();

    public void finalize() throws IOException {
        if (inStream != null) {
            inStream.close();
        }
        if (outStream != null) {
            outStream.close();
        }
        if (socket != null) {
            socket.close();
        }
    }

}
