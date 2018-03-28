import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileSource {
    public static ObservableList<String> getAllFiles() {
        ObservableList<String> records = FXCollections.observableArrayList();

        records.addAll("Single", "Double", "Suite", "Family App");

        return records;
    }
}
