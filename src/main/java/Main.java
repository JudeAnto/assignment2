import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    private static BorderPane bPane = new BorderPane();
    private static ListView<String> clientList = new ListView<String>();
    private static ListView<String> serverList = new ListView<String>();
    private Button buttonUpload = new Button();
    private Button buttonDownload = new Button();
    private static String clientFileName = null;
    private static String serverFileName = null;

    //REQUIREMENT: necessary to add the filepath of your client folder
    //Ex: C:\\jude\\assignment2\\resources\\ClientFolder\\
    private static final String clientpath = "!MUST-ADD-YOUR-CLIENT-FOLDER-PATH-IN-HERE!";

    //gets server path from the server
    private final String serverPath = new ServerThread().getServerFilePath();


    @Override
    public void start(Stage stage) {

        //Instantiating the BorderPane class
        bPane.setPadding(new Insets(0,20,15, 20));

        //stores info from FileSource class into Listview
        clientList.setItems(new FileSource(clientpath).getAllFiles());


        serverList.setItems(new FileSource(serverPath).getAllFiles());

        bPane.setLeft(clientList);
        bPane.setRight(serverList);
        bPane.setTop(addHBox());
        bPane.setCenter(addVBox());

        //mouse click event handler for listview
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
            if (!(clientFileName == null)) {

                this.runClientThread(clientFileName, 'u');

                //this doesn't update listview until you run/upload it twice.
                // The refresh DOES work but it's buggy!
                this.updateServerList();

            } else {
                //throws error box
                infoBox("Click on a file from your folder to Upload!"
                        , "__FILE NOT CHOSEN__", null);
            }


        });

        buttonDownload.setOnAction(action -> {
            if (!(serverFileName == null)) {

                this.runClientThread(serverFileName, 'd');
                //this doesn't update until you run it twice. THe refresh DOES work but it's buggy!
                this.updateClientList();

            } else {
                infoBox("Click on a file on the Shared Folder to Upload!"
                        , "__FILE NOT CHOSEN__", null);
            }
        });


        Scene scene = new Scene(bPane, 600, 500);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        stage.setTitle("FILE SHARER V1.0");
        stage.getIcons().add(new Image("resolutionIcon.jpg")); //image for stage
        stage.setScene(scene);
        stage.show();

    }
    //adds the horizontal box and sets the conditions
    private HBox addHBox() {
        HBox topElements = new HBox();
        topElements.setPadding(new Insets(10, 0, 10, 60));
        topElements.setSpacing(250);
        topElements.getChildren().addAll(addClientLabel(), addServerLabel());
        return topElements;
    }
    //adds the vertical box and sets the conditions
    private VBox addVBox() {
        VBox vbox = new VBox();
        vbox.getChildren().addAll(addUploadButton(), addDownloadButton());
        return vbox;
    }
    //adds the buttons and sets their conditions
    private Button addUploadButton() {

        buttonUpload.setPrefSize(30, 30);
        buttonUpload.setTranslateY(160);
        buttonUpload.setTranslateX(12);
        buttonUpload.setStyle("-fx-graphic: url('uploadIcon.png')");
        return buttonUpload;
    }
    private Button addDownloadButton() {

        buttonDownload.setPrefSize(30, 30);
        buttonDownload.setTranslateY(190);
        buttonDownload.setTranslateX(12);
        buttonDownload.setStyle("-fx-graphic: url('downloadIcon.png')");
        return buttonDownload;
    }
    private Label addClientLabel() {return new Label("Your Folder");}
    private Label addServerLabel() {return new Label("Shared Folder");}

    //this is an alert box which is a form of exceptionHandler/System.out.println("ERROR!")
    public void infoBox(String infoMessage, String titleBar, String headerMessage)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
    //updates client/server lists but glitchy
    public void updateClientList() {
        clientList.setItems(new FileSource(clientpath).getAllFiles());
        bPane.setLeft(clientList);
    }
    public void updateServerList() {
        serverList.setItems(new FileSource(serverPath).getAllFiles());
        bPane.setRight(serverList);
    }
    //runs Client on a new thread aside from parent GUI thread
    private void runClientThread(String fileName, char ab) {

        Thread clientThread = new Thread(new Client(fileName, ab, clientpath));
        //links client thread to parent thread so client thread exits with GUI
        clientThread.setDaemon(true);
        clientThread.start();
    }

    public static void main(String args[]){
        launch(args);
    }
}
