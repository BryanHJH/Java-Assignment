import javax.swing.ImageIcon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage/Login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("Images/booking.jpg"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Resort Booking System");
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
