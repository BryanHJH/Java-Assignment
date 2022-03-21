package MainPage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import Classes.Hotel;
import Classes.Reservation;
import Classes.Room;
import ReceiptPage.ReceiptController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

public class MainPageController implements Initializable {
    
    @FXML
    private TableView<Reservation> reservationTable;

    @FXML 
    TableColumn<Reservation, String> roomIDCol, custNameCol, custICCol, custPhoneCol, custEmailCol;
    
    @FXML
    private TableColumn<Reservation, LocalDate> checkInCol, checkOutCol;

    @FXML
    private DatePicker checkInDatePicker, checkOutDatePicker;
    
    @FXML
    private TableColumn<Reservation, Integer> noFamilyCol;

    @FXML
    private Button addBooking, clearBooking, logoutButton, deleteReservation, printReceiptButton;

    @FXML
    private ComboBox<String> roomIDCombo;

    @FXML
    private Label checkInLabel, checkOutLabel, custICLabel, custNameLabel, noFamilyLabel, roomIDLabel, welcomeMessage, custPhonLabel, custEmailLabel, warningLabel;

    @FXML
    private Tab newBooking, viewBookings;

    @FXML
    private TextField custICTextField, custNameTextField, noFamilyTextField, custPhoneTextField, custEmailTextField;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private File roomFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.json");
    private File reservationFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Reservations.json");

    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    String[] roomIDs = new String[20];

    // Reading files
    public static Room[] readRoomFile(File file) throws FileNotFoundException {

        Gson gson = new Gson();
        Reader reader = new FileReader(file);
        Room[] roomList = gson.fromJson(reader, Room[].class);
        return roomList;
    }

    public static Reservation[] readReservationFile(File file) throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader(file);
        Reservation[] reservationList = gson.fromJson(reader, Reservation[].class);
        return reservationList;
    }

    /**
     * Function name: Restrict the dates that can be chosen in the DatePicker
     * 
     * Inside the function:
     *  1. Read the room file
     *  2. Get the room id text field text value
     *  3. Find the room and get its available dates
     *  4. set the min date as the first item in the AvailableDates arraylist
     *  5. set the max date as the last item in the AvailableDates arraylist
     * @throws FileNotFoundException
     */
    public void restrictDates() throws FileNotFoundException {
        
        Room[] roomList = readRoomFile(roomFile);
        String roomID = roomIDCombo.getValue();

        for (Room room: roomList) {
            if (room.getRoomID().equals(roomID)) {
            }
        }
        
        
        LocalDate minDate = checkInDatePicker.getValue().plusDays(1);
        LocalDate maxDate = LocalDate.of(2022, 3, 19);
        checkOutDatePicker.setDayCellFactory(d ->
            new DateCell() {
                @Override public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                }});
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // Setting up the reservation table
        // Second line of each Table Column enables editing and converting the edited value to its correct type
        reservationTable.setEditable(true);
        reservationTable.setPlaceholder(new Label("No reservations to display"));
        
        roomIDCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        roomIDCol.setCellFactory(TextFieldTableCell.forTableColumn());
        roomIDCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Reservation,String>>() {

            @Override
            public void handle(CellEditEvent<Reservation, String> event) {
                Reservation reservation = event.getRowValue();
                String newRoomID = event.getNewValue();
                String oldRoomID = event.getOldValue();

                try {
                    reservation.removeReservation(reservation);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                
                reservation.setRoomID(newRoomID); // Settng the new roomID

                // Saving the new reservation
                try {
                    reservation.saveReservation();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }

                // Removing the dates of the new selected Room
                try {
                    Room[] roomList = readRoomFile(new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.json"));
                    Hotel tmpHotel = new Hotel(roomList);

                    LocalDate checkInDate = reservation.getCheckIn();
                    LocalDate checkOutDate = reservation.getCheckOut();
                    // Getting the list of dates that are booked
                    List<LocalDate> dates = checkInDate.datesUntil(checkOutDate).collect(Collectors.toList());
                    // Removing the dates from the data store (text file)
                    try {
                        // Removing the dates between the check in date and check out date (exclusive)
                        for (LocalDate date: dates) {
                            String formattedDate = dateFormatter.format(date);
                            // LocalDate newDate = LocalDate.parse(formattedDate, dateFormatter);
                            tmpHotel.removeDate(tmpHotel.getRoom(tmpHotel.searchRoom(newRoomID)).getRoomID(), formattedDate);
                        }
                        // Remove the check out date
                        tmpHotel.removeDate(tmpHotel.getRoom(tmpHotel.searchRoom(newRoomID)).getRoomID(), dateFormatter.format(checkOutDate));
                        // Saving the new available dates
                        tmpHotel.saveRoomData();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                }

                // Adding the dates back to the old Room
                try {
                    Room[] roomList = readRoomFile(new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.json"));
                    Hotel tmpHotel = new Hotel(roomList);

                    LocalDate checkInDate = reservation.getCheckIn();
                    LocalDate checkOutDate = reservation.getCheckOut();
                    // Getting the list of dates that are booked
                    List<LocalDate> dates = checkInDate.datesUntil(checkOutDate).collect(Collectors.toList());
                    // Removing the dates from the data store (text file)
                    try {
                        // Removing the dates between the check in date and check out date (exclusive)
                        for (LocalDate date: dates) {
                            String formattedDate = dateFormatter.format(date);
                            // LocalDate newDate = LocalDate.parse(formattedDate, dateFormatter);
                            tmpHotel.addDate(tmpHotel.getRoom(tmpHotel.searchRoom(oldRoomID)).getRoomID(), formattedDate);
                        }
                        // Remove the check out date
                        tmpHotel.addDate(tmpHotel.getRoom(tmpHotel.searchRoom(oldRoomID)).getRoomID(), dateFormatter.format(checkOutDate));
                        // Saving the new available dates
                        tmpHotel.saveRoomData();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                }
            }
            
        });

        custNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        custNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        custNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Reservation,String>>() {

            @Override
            public void handle(CellEditEvent<Reservation, String> event) {
                Reservation reservation = event.getRowValue();
                String newCustName = event.getNewValue();

                try {
                    reservation.removeReservation(reservation);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                reservation.setCustName(newCustName); // Settng the new roomID

                // Saving the new reservation
                try {
                    reservation.saveReservation();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
            
        });
        
        custICCol.setCellValueFactory(new PropertyValueFactory<>("custIC"));
        custICCol.setCellFactory(TextFieldTableCell.forTableColumn());
        custICCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Reservation,String>>() {

            @Override
            public void handle(CellEditEvent<Reservation, String> event) {
                Reservation reservation = event.getRowValue();
                String newCustIC = event.getNewValue();

                try {
                    reservation.removeReservation(reservation);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                reservation.setCustIC(newCustIC); // Settng the new roomID

                // Saving the new reservation
                try {
                    reservation.saveReservation();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
            
        });
        
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("custPhone"));
        custPhoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        custPhoneCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Reservation,String>>() {

            @Override
            public void handle(CellEditEvent<Reservation, String> event) {
                Reservation reservation = event.getRowValue();
                String newCustPhone = event.getNewValue();

                try {
                    reservation.removeReservation(reservation);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                reservation.setCustPhone(newCustPhone); // Settng the new roomID

                // Saving the new reservation
                try {
                    reservation.saveReservation();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
            
        });
        
        custEmailCol.setCellValueFactory(new PropertyValueFactory<>("custEmail"));
        custEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        custEmailCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Reservation,String>>() {

            @Override
            public void handle(CellEditEvent<Reservation, String> event) {
                Reservation reservation = event.getRowValue();
                String newCustEmail = event.getNewValue();

                try {
                    reservation.removeReservation(reservation);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                reservation.setCustEmail(newCustEmail); // Settng the new roomID

                // Saving the new reservation
                try {
                    reservation.saveReservation();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
            
        });
        
        noFamilyCol.setCellValueFactory(new PropertyValueFactory<>("custFamily"));
        noFamilyCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        noFamilyCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Reservation,Integer>>() {

            @Override
            public void handle(CellEditEvent<Reservation, Integer> event) {
                Reservation reservation = event.getRowValue();
                int newNoFamily = event.getNewValue();

                try {
                    reservation.removeReservation(reservation);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                reservation.setCustFamily(newNoFamily); // Settng the new roomID

                // Saving the new reservation
                try {
                    reservation.saveReservation();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
            
        });
        
        checkInCol.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOut"));

        try {
            Reservation[] reservationList = readReservationFile(reservationFile);
            if (reservationList != null) {
                for (Reservation reservation: reservationList) {
                    reservationTable.getItems().add(reservation);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Restricting the date to 13/3/2022 to 19/3/2022 only
        LocalDate minDate = LocalDate.of(2022, 3, 13);
        LocalDate maxDate = LocalDate.of(2022, 3, 19);
        checkInDatePicker.setDayCellFactory(d ->
            new DateCell() {
                @Override public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                }});
        
        checkOutDatePicker.setDayCellFactory(d ->
            new DateCell() {
                @Override public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                }});

        // Building the initial combo box
        checkInDatePicker.setValue(LocalDate.of(2022, 3, 13));
        
        try {
            Room[] roomList = readRoomFile(new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.json"));
            int index = 0;
            for (Room room: roomList) {
                if (room.checkAvailability(checkInDatePicker.getValue())) {
                    roomIDs[index] = room.getRoomID();
                }
                index++;
            }
            for (int i = 0; i < roomIDs.length; i++) {
                if (roomIDs[i] == null || roomIDs[i].isBlank()) {
                    roomIDs[i] = "Room not available";
                }
            }

            // Adding the list of roomID into the combo box for selection
            roomIDCombo.getItems().addAll(roomIDs);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
    }

    // Rebuild combo box
    public void buildComboBox() {
        try {
            Room[] roomList = readRoomFile(new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.json"));
            int index = 0;
            for (Room room: roomList) {
                if (room.checkAvailability(checkInDatePicker.getValue())) {
                    roomIDs[index] = room.getRoomID();
                } else {
                    roomIDs[index] = "Room not available";
                }
                index++;
            }

            // Adding the list of roomID into the combo box for selection
            roomIDCombo.getItems().setAll(roomIDs);
            restrictDates();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
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

        // Data validation section
        // Email matching regex
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(customerEmail);
        
        if (customerName == null || customerName.isBlank() || customerIC == null || customerIC.isBlank() || customerPhone == null || customerPhone.isBlank() ||customerEmail == null || customerEmail.isBlank() ||roomID == null || roomID.isBlank() || roomIDCombo.getSelectionModel().isEmpty() ||noFamilyMembers < 0 || noFamilyTextField.getText().trim().isEmpty() ||checkInDate == null || checkOutDate == null) {
            warningLabel.setTextFill(Color.RED);
            warningLabel.setText("Fields cannot be empty!");
        } else if (!matcher.matches()) {
            custEmailTextField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            warningLabel.setText("Email format is wrong!");
        } else {
            // Saving the reservation information to a text file
            warningLabel.setText("");
            custEmailTextField.setStyle(null);
            Reservation newReservation = new Reservation(roomID, customerName, customerIC, customerPhone, customerEmail, noFamilyMembers, checkInDate, checkOutDate);
            try {
                newReservation.saveReservation();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            
            // Creating a temporary Hotel class to hold the data
            try {
                Room[] roomList = readRoomFile(new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.json"));
    
                Hotel tmpHotel = new Hotel(roomList);
                // Getting the list of dates that are booked
    
                List<LocalDate> dates = checkInDate.datesUntil(checkOutDate).collect(Collectors.toList());
                // Removing the dates from the data store (text file)
                try {
                    // Removing the dates between the check in date and check out date (exclusive)
                    for (LocalDate date: dates) {
                        String formattedDate = dateFormatter.format(date);
                        // LocalDate newDate = LocalDate.parse(formattedDate, dateFormatter);
                        tmpHotel.removeDate(tmpHotel.getRoom(tmpHotel.searchRoom(roomID)).getRoomID(), formattedDate);
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
            }
    
            // Adding the reservation to the table in the other tab
            reservationTable.getItems().add(newReservation);
            
            // Clear all fields except the Check In Date
            clearBookingFields(e);
        }


    }

    // Clearing all booking fields
    public void clearBookingFields(ActionEvent e) {
        custNameTextField.clear();
        custICTextField.clear();
        noFamilyTextField.clear();
        custPhoneTextField.clear();
        custEmailTextField.clear();
        checkInDatePicker.setValue(LocalDate.of(2022, 3, 13));
        checkOutDatePicker.setValue(null);
        roomIDCombo.setValue(null);
        buildComboBox();
    }

    // Remove existing reservations
    /**
     * Function Name; removeReservation
     * @param e
     * 
     * Inside the function:
     *  1. Get the selected row from the TableView (x)
     *  2. Get the roomID and custName values from the selected row (x)
     *  3. Find the record of the combination of the roomID and customer name in Reservations.json (x)
     *  4. Get the Check In and Check Out dates
     *  5. Create a List of all the dates between the Check In and Check Out dates
     *  6. Add the dates of the List back into the correct Room (use searchRoom function)
     *  7. Remove the record from Reservations.json
     *  8. Remove the record from TableView object
     */
    public void removeReservation(ActionEvent e) {
        
        File reservationFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Reservations.json");
        File roomFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.json");
        
        try {
            // Getting an array of all current reservations
            Reservation[] reservations = readReservationFile(reservationFile);
            // Creating a new arraylist of reservations that will not contain the selected reservation to delete
            ArrayList<Reservation> updatedReservations = new ArrayList<Reservation>();
            // Getting the selected Reservation
            Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
            // Looping through the Array and check if the selected reservation
            if (selectedReservation != null) {
                for (Reservation reservation: reservations) {
                    if (reservation == selectedReservation) { // If the reservation in the Array matches the selected Reservation, skip it
                        continue;
                    }
                    updatedReservations.add(reservation); // Adds the other reservations to the new ArrayList
                }
            }

            // Creating a temporary Hotel object to save the new Available Dates for the selected Reservation room
            Room[] rooms = readRoomFile(roomFile);
            Hotel tmpHotel = new Hotel(rooms);

            LocalDate reservationCheckIn = selectedReservation.getCheckIn(); // Check in date for the removed reservation
            LocalDate reservationCheckOut = selectedReservation.getCheckOut(); // Check out date for the removed reservation
            List<LocalDate> dates = reservationCheckIn.datesUntil(reservationCheckOut).collect(Collectors.toList());
            try {
                // Adding the dates between the check in date and check out date (exclusive)
                for (LocalDate date: dates) {
                    String formattedDate = dateFormatter.format(date);
                    // LocalDate newDate = LocalDate.parse(formattedDate, dateFormatter);
                    tmpHotel.addDate(tmpHotel.getRoom(tmpHotel.searchRoom(selectedReservation.getRoomID())).getRoomID(), formattedDate);
                }
                // add the check out date
                tmpHotel.addDate(tmpHotel.getRoom(tmpHotel.searchRoom(selectedReservation.getRoomID())).getRoomID(), dateFormatter.format(reservationCheckOut));
                // Saving the new available dates
                tmpHotel.saveRoomData();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            
            updatedReservations.get(0).removeReservation(selectedReservation); // Updating the file with the newest reservations
            // System.out.println(updatedReservations);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        // Removing the selected Reservation from the table
        int row = reservationTable.getSelectionModel().getSelectedIndex();
        if (row >= 0) {
            reservationTable.getItems().remove(row);
            reservationTable.getSelectionModel().clearSelection();
        }

    }

    // Print receipt button
    /**
     * Function Name: printReceipt
     * @param e
     * 
     * Inside the function:
     *  1. Get the selected row
     *  2. Get the roomID and custName
     *  3. Loop through the Reservations.json (use GSON fromJSON method)
     *  4. If roomID and custName matches the reservation record, save it to a temporary Reservation object
     *  5. Pop up a new Scene (x)
     *  6. Change the Labels of the new Scene to the correct ones in the temporary Reservation object
     *  7. Create a back button to go back to the TableView scene. 
     * @throws IOException
     */
    public void printReceipt(ActionEvent e) throws IOException {

        Room[] rooms = readRoomFile(roomFile);
        Hotel tmpHotel = new Hotel(rooms);
        Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReceiptPage/Receipt.fxml"));
        root = loader.load();
        ReceiptController receiptController = loader.getController();
        receiptController.displayMessage(selectedReservation.getCustName(), selectedReservation.getCustIC(), selectedReservation.getCustPhone(), selectedReservation.getCustEmail(), selectedReservation.getRoomID(), tmpHotel.getRoom(tmpHotel.searchRoom(selectedReservation.getRoomID())).getRoomView(), selectedReservation.getCheckIn(), selectedReservation.getCheckOut(), selectedReservation.getDurationOfStay(), selectedReservation.getFinalPrice());

        stage =  (Stage)((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
