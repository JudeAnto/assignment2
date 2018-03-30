import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.net.Socket;
import java.util.Scanner;

class ServerThread implements Runnable {
    private Socket socket;
    private InputStream io;
    private OutputStream os;
    private PrintWriter pw;
    //REQUIREMENT: Necessary to add server file path here
    //Ex: C:\\jude\\assignment2\\resources\\ServerFolder\\
    private static final String filePath = "!MUST-ADD-YOUR-SHARED-FOLDER-PATH-IN-HERE!";

    //constructors
    ServerThread() {}
    ServerThread(Socket socket) {
        this.socket = socket;
    }


    public long getId() {
        return Thread.currentThread().getId();
    }
    //gets server path
    public String getServerFilePath() {
        return filePath;
    }
    //uploades file content to a (new) file in server
    public void uploadFileContents(Scanner scanner, String textFileName) {

        FileIO writeFile = new FileIO(filePath + textFileName);
        String loadFile = "";
        while (scanner.hasNextLine()) {
            loadFile += scanner.nextLine() + System.lineSeparator();
        }
        writeFile.writeCharFile(loadFile);
        //statements
        System.out.printf("ClientID : %d\n" +
                "File    : %s\n" +
                "Successfully uploaded to main Server (Shared Folder)!\n", getId(), textFileName);
    }
    //this echos file contents from server back to client
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
                //this is the textfile name included with abbreviation ("mytext.txt -u")
                String textFileName = scanner.nextLine();
                //splits it by the space
                String[] fileNameSplit = textFileName.split(" ");
                if (fileNameSplit[1].equals("-u")) {
                    uploadFileContents(scanner, fileNameSplit[0]);
                }
                else if (fileNameSplit[1].equals("-d")){
                    echoFileContents(scanner, fileNameSplit[0]);
                }
                //thought doing this would update listview alternately but server cannot access Main
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