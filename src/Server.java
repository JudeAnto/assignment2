package assignment2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable {
    private Socket socket;
    private InputStream io;
    private OutputStream os;
    private PrintWriter pw;

    Server(Socket socket) {
        this.socket = socket;
    }

    public long getId() {
        return Thread.currentThread().getId();
    }

    @Override
    public void run() {
        try {
            io = socket.getInputStream();
            os = socket.getOutputStream();

            try (Scanner scanner = new Scanner(io)) {
                pw = new PrintWriter(os, true);
                pw.println("Connected...");
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    					//client:
                    System.out.println(line);
                    pw.println("Echo: " + line);
                }
            } finally {
                System.out.println("Client " + getId() + " has disconnected.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}