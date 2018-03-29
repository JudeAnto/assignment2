import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread implements Runnable {
    private Socket socket;
    private InputStream io;
    private PrintWriter pw;
    private static final String filePath = "C:\\Jude\\a2test\\Server\\";

    ServerThread(Socket socket) {
        this.socket = socket;
    }

    public long getId() {
        return Thread.currentThread().getId();
    }

    @Override
    public void run() {
        try {
            io = socket.getInputStream();

            try (Scanner scanner = new Scanner(io)) {
                FileIO writeFile = new FileIO(filePath+scanner.nextLine());
                String loadFile = "";
                while (scanner.hasNextLine()) {
                    loadFile += scanner.nextLine() + System.lineSeparator();
                }
                writeFile.writeCharFile(loadFile);
            } finally {
                //System.out.println("Server has disconnected.");
                socket.close();
                if (socket.isClosed())
                    System.out.println("Successfully Disconnected!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}