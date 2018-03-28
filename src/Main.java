import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private ListView<String> list = new ListView<String>();
    @Override
    public void start(Stage stage) {
        //Instantiating the BorderPane class
        BorderPane bPane = new BorderPane();
        bPane.setPadding(new Insets(0,20,15, 20));

        list.setItems(FileSource.getAllFiles());
        ListView<String> list2 = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
                "Single", "Double", "Suite", "Family App");
        list2.setItems(items);

        bPane.setLeft(list);
        bPane.setRight(list2);
        bPane.setTop(addHBox());
        bPane.setCenter(addVBox());

        Scene scene = new Scene(bPane, 600, 500);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        stage.setTitle("FILE SHARER V1.0");
        stage.getIcons().add(new Image("https://www.iatfadp.com/test/images/icons/resolutionIcon.jpg"));
        stage.setScene(scene);
        stage.show();
    }
    private HBox addHBox() {
        HBox topElements = new HBox();
        topElements.setPadding(new Insets(10, 0, 10, 60));
        topElements.setSpacing(250);
        topElements.getChildren().addAll(addClientLabel(), addServerLabel());
        return topElements;
    }
    private VBox addVBox() {
        VBox vbox = new VBox();
        vbox.getChildren().addAll(addUploadButton(), addDownloadButton());
        return vbox;
    }
    private Button addUploadButton() {
        Button buttonUpload = new Button();
        buttonUpload.setPrefSize(30, 30);
        buttonUpload.setTranslateY(160);
        buttonUpload.setTranslateX(12);
        buttonUpload.setStyle("-fx-graphic: url('t1.png')");
        return buttonUpload;
    }
    private Button addDownloadButton() {
        Button buttonDownload = new Button();
        buttonDownload.setPrefSize(30, 30);
        buttonDownload.setTranslateY(190);
        buttonDownload.setTranslateX(12);
        buttonDownload.setStyle("-fx-graphic: url('t2.png')");
        return buttonDownload;
    }
    private Label addClientLabel() {return new Label("Your Folder");}
    private Label addServerLabel() {return new Label("Shared Folder");}

    public static void main(String args[]){
        launch(args);
    }
}
