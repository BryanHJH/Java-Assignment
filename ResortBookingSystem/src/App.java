import Classes.Hotel;
import Classes.Room;
import Classes.Staff;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage/Login.fxml"));
        Parent root = loader.load();
        // MainPageController mainPageController = loader.getController();
        // mainPageController.buildComboBox();
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("Images/booking.jpg"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Resort Booking System");
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {

        Room[] allRoomList = {
            new Room("101", 3, "Jungle", 350.00),
            new Room("102", 3, "Jungle", 350.00),
            new Room("103", 1, "Jungle", 350.00),
            new Room("104", 3, "Jungle", 350.00),
            new Room("105", 1, "Jungle", 350.00),
            new Room("106", 3, "Jungle", 350.00),
            new Room("107", 2, "Jungle", 350.00),
            new Room("108", 2, "Jungle", 350.00),
            new Room("109", 3, "Jungle", 350.00),
            new Room("110", 2, "Jungle", 350.00),
            new Room("201", 3, "Sea", 350.00),
            new Room("202", 3, "Sea", 350.00),
            new Room("203", 1, "Sea", 350.00),
            new Room("204", 3, "Sea", 350.00),
            new Room("205", 1, "Sea", 350.00),
            new Room("206", 3, "Sea", 350.00),
            new Room("207", 2, "Sea", 350.00),
            new Room("208", 2, "Sea", 350.00),
            new Room("209", 3, "Sea", 350.00),
            new Room("210", 2, "Sea", 350.00)
        };

        Hotel testHotel = new Hotel(allRoomList);
        testHotel.addStaff(new Staff("John", "john@resort.com", "johnresort", "13-3-1990", "111111102938"));
        testHotel.addStaff(new Staff("Ron", "ron@resort.com", "ronresort", "13-3-1992", "111111102928"));
        testHotel.addStaff(new Staff("Ren", "ren@resort.com", "renresort", "14-4-1980", "111111101938"));
        testHotel.addStaff(new Staff("Bam", "bam@resort.com", "bamresort", "15-5-1950", "111111103938"));
        testHotel.addStaff(new Staff("Gavin", "gavin@resort.com", "gavinresort", "13-3-1995", "111111102838"));

        testHotel.saveStaffData();
        testHotel.saveRoomData(); 

        launch(args);
    }
}
