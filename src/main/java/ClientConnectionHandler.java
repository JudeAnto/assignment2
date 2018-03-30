import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//handles MultipleClientConnection
public class ClientConnectionHandler {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(20202)) {
            System.out.println("Waiting for users to connect...");

            //multithreads server
            while (true) {
                Socket socket = serverSocket.accept();
                Runnable r = new ServerThread(socket);
                Thread t = new Thread(r);
                t.start();

            }
        }
    }
}