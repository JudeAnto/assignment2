import java.io.*;

public class FileIO {

    private String fileName = null;
    FileIO (String filePath) {
        this.fileName = fileName;
    }

    public void readCharFile() {

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader input = new BufferedReader(fileReader);
            String line = null;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCharFile() {

        try {
            PrintWriter output = new PrintWriter(fileName);

            //boolean keepGoing = true;
            String line = null;
            output.println("hello");
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
