package sample;
import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application {

    DataOutputStream toServer = null;
    DataInput fromServer = null;

    @Override
    public void start(Stage primaryStage) {

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(5, 5, 5,5));
        pane.setStyle("-fx-border-color: darkgreen");
        pane.setLeft(new Label("Enter new radius"));


        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        pane.setCenter(tf);

        BorderPane mainPane = new BorderPane();

        TextArea textArea = new TextArea();
        mainPane.setCenter(new ScrollPane(textArea));
        mainPane.setTop(pane);

        Scene scene = new Scene(mainPane, 450,200);
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();

        tf.setOnAction(e -> {
            try {
                double radius = Double.parseDouble(tf.getText().trim());

                toServer.writeDouble(radius);
                toServer.flush();

                double area = fromServer.readDouble();

                textArea.appendText("radius is" + radius + "\n");
                textArea.appendText("area recived from server is" + area + '\n');

            }
            catch (IOException exception){
                System.err.println(exception);
            }
        });

        try {
            Socket socket = new Socket("localhost",8000);
            fromServer = new DataInputStream(socket.getInputStream());

            toServer = new DataOutputStream(socket.getOutputStream());

        } catch (IOException exception){
            textArea.appendText(exception.toString() + '\n');

        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
