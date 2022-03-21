package ReceiptPage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ReceiptController {
    
    @FXML
    private Label custNameLabel, custICLabel, custPhoneLabel, custEmailLabel, roomIDLabel, roomViewLabel, checkInLabel, checkOutLabel, durationOfStayLabel, finalPriceLabel;

    @FXML
    private Button returnButton;

    private Stage stage;
    private Scene scene;
    private Parent root;
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public void displayMessage(String customerName, String customerIC, String customerPhone, String customerEmail, String roomID, String roomView, LocalDate checkInDate, LocalDate checkOutDate, int durationOfStay, double finalPrice) {
        custNameLabel.setText(customerName);
        custICLabel.setText(customerIC);
        custPhoneLabel.setText(customerPhone);
        custEmailLabel.setText(customerEmail);
        roomIDLabel.setText(roomID);
        roomViewLabel.setText(roomView);
        checkInLabel.setText(dateFormatter.format(checkInDate));
        checkOutLabel.setText(dateFormatter.format(checkOutDate));
        durationOfStayLabel.setText(Integer.toString(durationOfStay));
        finalPriceLabel.setText(Double.toString(finalPrice));
    }

    public void returnToMainPage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MainPage/MainPage.fxml"));
        stage =  (Stage)((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
