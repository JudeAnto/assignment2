import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class Client implements Runnable{
    private static final String screenName   = "User 1";
    private static final String host         = "127.0.0.1";
    private static final int port            = 20500;
    private static final String filePath = "C:\\Jude\\a2test\\Client\\";
    public OutputStream os;
    private static String fileName = null;

    Client (String fileName) {
        this.fileName = fileName;
    }
    private void sendFileContents() {

        String fileContents = new FileIO(filePath+fileName).readCharFile();
        PrintWriter pw = new PrintWriter(os, true);

        pw.println(fileName);
        pw.println(fileContents);
        pw.close();

    }
    @Override
    public void run() {
        try {
            Socket socket = new Socket(host, port);
            os = socket.getOutputStream();
            System.out.println("Connected to " + host + ":" + port);
            sendFileContents();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

