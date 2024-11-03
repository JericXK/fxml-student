package org.Jeric;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primarystage) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/main.fxml")));
        primarystage.setTitle("Registration:");
        primarystage.setScene(new Scene(root, 450, 450));
        primarystage.show();
    }
    DatabaseConnection db = new DatabaseConnection();

    public static void main(String[] args){
        launch(args);
    }
}