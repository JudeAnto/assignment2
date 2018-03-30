import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client implements Runnable{
    private final String screenName   = "User 1";
    private final String host         = "127.0.0.1";
    private final int port            = 20202;
    private static String filePath = "C:\\Jude\\a2test\\Client\\";
    private static char abbreviation;
    private OutputStream os;
    private InputStream io;
    //private PrintWriter pw;
    private static String fileName = null;

    Client (String fileName, char ab, String clientPath) {
        this.fileName = fileName;
        this.abbreviation = ab;
        this.filePath = clientPath;
    }
    private void sendFileContents() {

        String fileContents = new FileIO(filePath+fileName).readCharFile();
        PrintWriter pw = new PrintWriter(os, true);
        pw.println(fileName+" -u");
        pw.println(fileContents);
        pw.close();

    }
    private void receiveFileContents() throws IOException {

        PrintWriter pw = new PrintWriter(os, true);
        pw.println(fileName + " -d");
        FileIO writeFile = new FileIO(filePath + fileName);
        String loadFile = "";
        try (Scanner scanner = new Scanner(io)) {
            while (scanner.hasNextLine()) {
                loadFile += scanner.nextLine() + System.lineSeparator();
            }
            writeFile.writeCharFile(loadFile);
            System.out.println("done!");

        }
    }
    @Override
    public void run() {
        try {
            Socket socket = new Socket(host, port);
            os = socket.getOutputStream();
            System.out.println("Connected to " + host + ":" + port);
            if (abbreviation == 'u') {
                sendFileContents();
            }
            else if (abbreviation == 'd') {
                io = socket.getInputStream();
                receiveFileContents();
            }


            //return;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

