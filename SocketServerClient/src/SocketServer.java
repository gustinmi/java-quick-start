


import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    
    private static final String[] countries = {
        "slovenia",
        "italy",
        "germany"
    };
    
    static String protocol(String input){
        switch(input){
            case "slo" : return countries[0];
            case "ita" : return countries[1];
            case "ger" : return countries[2];
            case "END" : return countries[3];
            default: return "unknown";
        }
    } 

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(9999);
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(-1);
        }
        System.out.println("Listening on port 9999");
        while(true){ // run until CTRL + C
            Socket socket = null;
            try {
                socket = server.accept(); // awaiting new connections
                
                System.out.println("Got new client connection ...");
                final DataInputStream in = new DataInputStream(socket.getInputStream());
                final PrintStream out = new PrintStream(socket.getOutputStream());
                out.println("READY");
                String receivedRaw, baked;
                while((receivedRaw = in.readLine())!=null){
                    baked = protocol(receivedRaw);
                    out.println(baked);
                    if (baked.equals("END_OF_PROTOCOL")) break;
                }
                out.close();
                in.close();
                socket.close();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
