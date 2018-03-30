import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

class FileSource {
    //stores a list of files
    private static File[] folderContents;
    private String dirPath = null;

    //construct directory path
    FileSource(String path) {
        this.dirPath = path;
    }

    //returns an ObservableList<String> consisting of file names
    //i just realized i could've created an observable list of files and it would've been easier to getAbsolutePath()...
    //but this would've used more memory as your storing all the contents of the file
    public ObservableList<String> getAllFiles() {
        ObservableList<String> records = FXCollections.observableArrayList();
        folderContents = new File(dirPath).listFiles();
        for (File file : folderContents) {
            if (file.isFile()) {
                records.add(file.getName());
            }
        }
        return records;
    }

}
