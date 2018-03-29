import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
    private ListView<String> clientList = new ListView<String>();
    private ListView<String> serverList = new ListView<String>();
    private Button buttonUpload = new Button();
    private Button buttonDownload = new Button();
    private static String clientFileName = null;
    private static String serverFileName = null;
    @Override
    public void start(Stage stage) {
        //Instantiating the BorderPane class
        BorderPane bPane = new BorderPane();
        bPane.setPadding(new Insets(0,20,15, 20));

        clientList.setItems(new FileSource("C:\\Jude\\a2test\\Client").getAllFiles());

        serverList.setItems(new FileSource("C:\\Jude\\a2test\\Server").getAllFiles());

        bPane.setLeft(clientList);
        bPane.setRight(serverList);
        bPane.setTop(addHBox());
        bPane.setCenter(addVBox());

        clientList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clientFileName = clientList.getSelectionModel().getSelectedItem();
            }
        });

        serverList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                serverFileName = serverList.getSelectionModel().getSelectedItem();
            }

        });


        buttonUpload.setOnAction(action -> {
            FileIO myf = new FileIO("");
                myf.readCharFile();
        });

        buttonDownload.setOnAction(action -> {
            FileIO myf = new FileIO("");
            myf.writeCharFile();
        });


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

        buttonUpload.setPrefSize(30, 30);
        buttonUpload.setTranslateY(160);
        buttonUpload.setTranslateX(12);
        buttonUpload.setStyle("-fx-graphic: url('t1.png')");
        return buttonUpload;
    }
    private Button addDownloadButton() {

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
