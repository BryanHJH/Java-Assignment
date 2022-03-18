package MainPage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

import Classes.Hotel;
import Classes.Reservation;
import Classes.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainPageController implements Initializable {
    
    @FXML
    private TableView reservationTable;

    @FXML 
    TableColumn<Reservation, String> roomIDCol, roomViewCol;
    
    // @FXML
    // private TableColumn<Reservation, Date> checkInCol, checkOutCol;

    @FXML
    private DatePicker checkInDatePicker, checkOutDatePicker;
    
    @FXML
    private TableColumn<Reservation, Integer> noFamilyCol;

    @FXML
    private Button addBooking, clearBooking, logoutButton, deleteReservation, printReceiptButton;

    @FXML
    private ComboBox<String> roomIDCombo;

    @FXML
    private Label checkInLabel, checkOutLabel, custICLabel, custNameLabel, noFamilyLabel, roomIDLabel, welcomeMessage, custPhonLabel, custEmailLabel;

    @FXML
    private Tab newBooking, viewBookings;

    @FXML
    private TextField custICTextField, custNameTextField, noFamilyTextField, custPhoneTextField, custEmailTextField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    String[] roomIDs = new String[20];

    // Reading files
    public static ArrayList<String> readFile(File file) throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        ArrayList<String> contents = new ArrayList<String>();

        while (reader.hasNextLine()) {
            contents.add(reader.nextLine());
        }
        reader.close();

        return contents;
    }

    public static String[] storeContents(File file) throws FileNotFoundException {
        
        ArrayList<String> storage = new ArrayList<String>();
        storage = readFile(file);
        String joinedArray = String.join(", ", storage);

        return joinedArray.split(", ");
    }

    // LocalDate minDate = LocalDate.of(2022, 3, 13);
    // LocalDate maxDate = LocalDate.of(2022, 3, 19);
    // checkInDatePicker.setDayCellFactory(d ->
    //         new DateCell() {
    //             @Override public void updateItem(LocalDate item, boolean empty) {
    //                 super.updateItem(item, empty);
    //                 setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
    //             }});
    // checkOutDatePicker.setDayCellFactory(d ->
    //         new DateCell() {
    //             @Override public void updateItem(LocalDate item, boolean empty) {
    //                 super.updateItem(item, empty);
    //                 setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
    //             }});

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        try {
            String[] roomList = storeContents(new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.txt"));
            
            for (int i = 0; i < roomList.length; i++) {
                String[] roomDetails = roomList[i].split("; ");

                // Formatting the last element of the roomDetails to an ArrayList<LocalDate>
                ArrayList<LocalDate> availability = new ArrayList<LocalDate>();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                for (String date: roomDetails[4].split(", ")) {
                    availability.add(LocalDate.parse(date, formatter));
                }

                // Creating a temporary Room object
                Room tmpRoom = new Room(roomDetails[0], Integer.parseInt(roomDetails[1]), roomDetails[2], Double.parseDouble(roomDetails[3]), availability); 
                // Check if the room is available on the check in date and if it is, add it to the roomID array
                if (tmpRoom.checkAvailability(checkInDatePicker.getValue())) {
                    roomIDs[i] = roomDetails[0];
                }
            }

            // Adding the list of roomID into the combo box for selection
            roomIDCombo.getItems().addAll(roomIDs);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    // Welcome message
    public void displayWelcomeMessage(String username) {
        welcomeMessage.setText("Welcome, " + username + "!");
    }

    // Adding new reservation
    public void addNewReservation(ActionEvent e) {
        // Getting the customer information
        String customerName = custNameTextField.getText();
        String customerIC = custICTextField.getText();
        int noFamilyMembers = Integer.parseInt(noFamilyTextField.getText());
        String customerPhone = custPhoneTextField.getText();
        String customerEmail = custEmailTextField.getText();
        String roomID = roomIDCombo.getValue();
        LocalDate checkInDate = checkInDatePicker.getValue();
        LocalDate checkOutDate = checkOutDatePicker.getValue();

        // Saving the reservation information to a text file
        Reservation newReservation = new Reservation(roomID, customerName, customerIC, customerPhone, customerEmail, noFamilyMembers, checkInDate, checkOutDate);
        try {
            newReservation.saveReservation();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        // Creating a temporary Hotel class to hold the data
        try {
            String[] roomList = storeContents(new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.txt"));
            Room[] rooms = new Room[roomList.length];
            for (int i = 0; i < roomList.length; i++) {
                String[] roomDetails = roomList[i].split("; ");

                // Formatting the last element of the roomDetails to an ArrayList<Localdate>
                ArrayList<LocalDate> availability = new ArrayList<LocalDate>();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                for (String date: roomDetails[4].split(", ")) {
                    availability.add(LocalDate.parse(date, formatter));
                }

                // Creating the Room item
                rooms[i] = new Room(roomDetails[0], Integer.parseInt(roomDetails[1]), roomDetails[2], Double.parseDouble(roomDetails[3]), availability);
            }

            Hotel tmpHotel = new Hotel(rooms);
            // Getting the list of dates that are booked
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            List<LocalDate> dates = checkInDate.datesUntil(checkInDate).collect(Collectors.toList());
            // Removing the dates from the data store (text file)
            try {
                // Removing the dates between the check in date and check out date (exclusive)
                for (LocalDate date: dates) {
                    tmpHotel.removeDate(tmpHotel.getRoom(tmpHotel.searchRoom(roomID)).getRoomID(), dateFormatter.format(date));
                }
                // Remove the check out date
                tmpHotel.removeDate(tmpHotel.getRoom(tmpHotel.searchRoom(roomID)).getRoomID(), dateFormatter.format(checkOutDate));
                // Saving the new available dates
                tmpHotel.saveRoomData();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (NumberFormatException e1) {
            e1.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }


    }

    // Clearing all booking fields
    public void clearBookingFields(ActionEvent e) {

    }

    // Remove existing reservations
    public void removeReservation(ActionEvent e) {

    }

    // Print receipt button
    public void printReceipt(ActionEvent e) {

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
