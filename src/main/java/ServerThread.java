import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class ServerThread implements Runnable {
    private Socket socket;
    private InputStream io;
    private OutputStream os;
    private PrintWriter pw;
    private static final String filePath = "C:\\Jude\\a2test\\Server\\";

    ServerThread() {}
    ServerThread(Socket socket) {
        this.socket = socket;
    }


    public long getId() {
        return Thread.currentThread().getId();
    }
    public String getServerFilePath() {
        return filePath;
    }

    public void uploadFileContents(Scanner scanner, String textFileName) {

        FileIO writeFile = new FileIO(filePath + textFileName);
        String loadFile = "";
        while (scanner.hasNextLine()) {
            loadFile += scanner.nextLine() + System.lineSeparator();
        }
        writeFile.writeCharFile(loadFile);

        System.out.printf("ClientID : %d\n" +
                "File    : %s\n" +
                "Successfully uploaded to main Server (Shared Folder)!\n", getId(), textFileName);
    }

    public void echoFileContents(Scanner scanner, String textFileName) {

        String fileContents = new FileIO(filePath+textFileName).readCharFile();
        pw = new PrintWriter(os, true);
        pw.println(fileContents);
        pw.close();
        System.out.printf("ClientID : %d\n" +
                "File    : %s\n" +
                "Successfully downloaded to your Folder!\n", getId(), textFileName);
    }

    @Override
    public void run() {
        try {

            io = socket.getInputStream();
            os = socket.getOutputStream();
            try (Scanner scanner = new Scanner(io)) {

                String textFileName = scanner.nextLine();

                String[] fileNameSplit = textFileName.split(" ");
                if (fileNameSplit[1].equals("-u")) {
                    uploadFileContents(scanner, fileNameSplit[0]);
                }
                else if (fileNameSplit[1].equals("-d")){
                    echoFileContents(scanner, fileNameSplit[0]);
                }
                //Main.serverList.setItems(new FileSource(filePath).getAllFiles());
            } finally {

                //socket.close();
                System.out.println("Server status: ");
                if (socket.isClosed()) System.out.print("OFFLINE\n\n");
                else System.out.print("ONLINE\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}