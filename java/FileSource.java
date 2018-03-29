import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

class FileSource {
    private static File[] folderContents;
    private String dirPath = null;

    FileSource(String path) {
        this.dirPath = path;
    }

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
