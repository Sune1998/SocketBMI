package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server extends Application {
    @Override
    public void start(Stage stage)  {
        TextArea textArea = new TextArea();

        Scene scene = new Scene(new ScrollPane(textArea), 450,200);
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();

        new Thread(() ->{
            try {

            ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(() -> textArea.appendText("server started at" + new Date() + '\n'));

                Socket socket = serverSocket.accept();

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());


                while (true){
                    double radius = inputStream.readDouble();

                    double area = radius * radius * Math.PI;

                    outputToClient.writeDouble(area);

                    Platform.runLater(() -> {
                        textArea.appendText("Radius recevid from client:" + radius + '\n');
                        textArea.appendText("Area is" + area + '\n');
                    });
                }
            }catch (IOException e){
                e.printStackTrace();

            }
        }).start();
    }
    public static void main(String[] args){
        launch(args);
    }


}
