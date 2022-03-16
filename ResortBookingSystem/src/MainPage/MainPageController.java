package MainPage;

import java.io.IOException;
import java.sql.Date;

import Classes.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class MainPageController {
    
    @FXML
    private TableView reservationTable;

    @FXML 
    TableColumn<Reservation, String> roomIDCol, roomViewCol;
    
    @FXML
    private TableColumn<Reservation, Date> checkInCol, checkOutCol;
    
    @FXML
    private TableColumn<Reservation, Integer> noFamilyCol;

    @FXML
    private Button addBooking, clearBooking, logoutButton, deleteReservation;

    @FXML
    private Label checkInLabel, checkOutLabel, custICLabel, custNameLabel, noFamilyLabel, roomIDLabel;

    @FXML
    private Tab newBooking, viewBookings;

    @FXML
    private TextField custICTextField, custNameTextField, noFamilyTextField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Adding new reservation
    public void addNewReservation(ActionEvent e) {

    }

    // Clearing all booking fields
    public void clearBookingFields(ActionEvent e) {

    }

    // Remove existing reservations
    public void removeReservation(ActionEvent e) {

    }

    // Logout
    public void logout(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/LoginPage/Login.fxml"));
        stage =  (Stage)((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
