import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client implements Runnable{

    //default setup
    private final String screenName   = "User 1";
    private final String host         = "127.0.0.1";
    private final int port            = 20202;

    //sets the filepath of your client folder
    private String filePath = "NOT NECESSARY TO SET THIS UP";
    private char abbreviation;
    private OutputStream os;
    private InputStream io;

    private String fileName = null;

    //constructor for Client
    Client (String fileName, char ab, String clientPath) {
        this.fileName = fileName;
        this.abbreviation = ab;
        this.filePath = clientPath;
    }

    //this sends file content to server
    private void sendFileContents() {

        String fileContents = new FileIO(filePath+fileName).readCharFile();
        PrintWriter pw = new PrintWriter(os, true);
        //abbreviation for upload
        pw.println(fileName+" -u");
        pw.println(fileContents);
        pw.close();

    }

    //this receives file content from the server
    private void receiveFileContents() throws IOException {

        PrintWriter pw = new PrintWriter(os, true);
        //abbreviation for download
        pw.println(fileName + " -d");
        FileIO writeFile = new FileIO(filePath + fileName);
        String loadFile = "";
        try (Scanner scanner = new Scanner(io)) {
            while (scanner.hasNextLine()) {
                //scan lines and separate new lines
                loadFile += scanner.nextLine() + System.lineSeparator();
            }
            //writes character to file
            writeFile.writeCharFile(loadFile);

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

