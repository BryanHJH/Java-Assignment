package MainPage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import Classes.Hotel;
import Classes.Reservation;
import Classes.Room;
import Classes.Staff;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class AdminPageController implements Initializable{

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML 
    TableColumn<Reservation, String> roomIDCol, custNameCol, custICCol, custPhoneCol, custEmailCol;
    
    @FXML
    private TableColumn<Reservation, LocalDate> checkInCol, checkOutCol;

    @FXML
    private TableColumn<Reservation, Integer> noFamilyCol;
    
    @FXML
    private TableView<Staff> staffTable;

    @FXML
    private TableColumn<Staff, String> staffNameCol, staffICCol, staffEmailCol, staffPasswordCol, staffDOBCol, staffRoleCol;

    @FXML
    private DatePicker checkInDatePicker, checkOutDatePicker;

    @FXML
    private Button addBooking, clearBooking, logoutButton, deleteReservation, printReceiptButton, searchButton, clearSearchButton, staffSearchButton, staffSearchClearButton, staffAddButton, staffClearButton, staffLogoutButton, fireStaffButton;

    @FXML
    private ComboBox<String> roomIDCombo;

    @FXML
    private Label checkInLabel, checkOutLabel, custICLabel, custNameLabel, noFamilyLabel, roomIDLabel, welcomeMessage, custPhonLabel, custEmailLabel, warningLabel, receiptWarningLabel;

    @FXML
    private Tab newBooking, viewBookings;

    @FXML
    private TextField custICTextField, custNameTextField, noFamilyTextField, custPhoneTextField, custEmailTextField, searchTextField, staffNameTextField, staffICTextField, staffEmailTextField, passwordTextField, dobTextField, staffSearchTextField;

    @FXML
    private RadioButton staffAdminRadio, staffRadio;

    @FXML
    private ToggleGroup staffRole;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private File staffFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Staff.json");
    private File roomFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.json");
    private File reservationFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Reservations.json");

    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    String[] roomIDs = new String[20];

    /**
     * Function Name: readStaffFile<p>
     * Inside the function: <p>
     * 1. Create a GSON object <P>
     * 2. Create a FileReader object <p>
     * 3. Read the contents of a file and store it as a Staff Array <p>
     * 4. Return the Staff Array
     * @param file
     * @return
     * @throws FileNotFoundException
     * @throws ParseException
     */
    public static Staff[] readStaffFile(File file) throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader(file);
        Staff[] staffList = gson.fromJson(reader, Staff[].class);
        return staffList;
    }
    
    /**
     * Function Name: readRoomFile<p>
     * Inside the function:<p>
     *  1. Creates a GSON object<p>
     *  2. Creates a FileReader object<p>
     *  3. Reads the contents from file and store it as a Room array<p>
     *  4. return the room array<p>
     * 
     * @param file
     * @return Room[] roomList
     * @throws FileNotFoundException
     * 
     */
    public static Room[] readRoomFile(File file) throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader(file);
        Room[] roomList = gson.fromJson(reader, Room[].class);
        return roomList;
    }

    /**
     * Function Name: readReservationFile <p>
     * Inside the function: <p>
     *  1. Similar to previous function <p>
     *  2. Difference is that it returns a Reservation[] array instead <p>
     * 
     * @param file
     * @return Reservation[] reservationList
     * @throws FileNotFoundException
     * 
     */
    public static Reservation[] readReservationFile(File file) throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader(file);
        Reservation[] reservationList = gson.fromJson(reader, Reservation[].class);
        return reservationList;
    }

    /**
     * Function name: Restrict the dates that can be chosen in the DatePicker <p>
     * Inside the function: <p>
     *  1. Read the room file <p>
     *  2. Get the room id text field text value <p>
     *  3. Find the room and get its available dates <p>
     *  4. set the min date as the first item in the AvailableDates ArrayList <p>
     *  5. set the max date as the last item in the AvailableDates ArrayList <p>
     * 
     * @throws FileNotFoundException
     * 
     */
    public void restrictDates() throws FileNotFoundException {
        
        Room[] roomList = readRoomFile(roomFile);
        String roomID = roomIDCombo.getValue();

        for (Room room: roomList) {
            if (room.getRoomID().equals(roomID)) {
            }
        }
        
        
        LocalDate minDate = checkInDatePicker.getValue().plusDays(1);
        LocalDate maxDate = checkInDatePicker.getValue().plusDays(6);
        checkOutDatePicker.setDayCellFactory(d ->
            new DateCell() {
                @Override public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                }});
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        staffTable.setEditable(true);
        staffTable.setPlaceholder(new Label("No staff records to display"));

        staffNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        staffNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        staffNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Staff,String>>() {

            @Override
            public void handle(CellEditEvent<Staff, String> event) {
                String newStaffName = event.getNewValue();
                String oldStaffName = event.getOldValue();
                
                try {
                    Staff[] staffList = readStaffFile(staffFile);
                    Room[] roomList = readRoomFile(roomFile);
                    Hotel tmpHotel = new Hotel(roomList);

                    for (int i = 0; i < staffList.length; i++) {
                        if (staffList[i].getName().equals(oldStaffName)) {
                            staffList[i].setName(newStaffName);
                            tmpHotel.addStaff(staffList[i]);
                            continue;
                        }
                        tmpHotel.addStaff(staffList[i]);
                    }

                    // tmpHotel.addStaff(editedStaff);

                    tmpHotel.saveStaffData();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
            
        });

        staffEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        staffEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        staffEmailCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Staff,String>>() {

            @Override
            public void handle(CellEditEvent<Staff, String> event) {
                String newStaffEmail = event.getNewValue();
                String oldStaffEmail = event.getOldValue();
                
                try {
                    Staff[] staffList = readStaffFile(staffFile);
                    Room[] roomList = readRoomFile(roomFile);
                    Hotel tmpHotel = new Hotel(roomList);

                    for (int i = 0; i < staffList.length; i++) {
                        if (staffList[i].getEmail().equals(oldStaffEmail)) {
                            staffList[i].setEmail(newStaffEmail);
                            tmpHotel.addStaff(staffList[i]);
                            continue;
                        }
                        tmpHotel.addStaff(staffList[i]);
                    } 
                    tmpHotel.saveStaffData();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        staffPasswordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        staffPasswordCol.setCellFactory(TextFieldTableCell.forTableColumn());
        staffPasswordCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Staff,String>>() {

            @Override
            public void handle(CellEditEvent<Staff, String> event) {
                String newStaffPassword = event.getNewValue();
                String oldStaffPassword = event.getOldValue();
                
                try {
                    Staff[] staffList = readStaffFile(staffFile);
                    Room[] roomList = readRoomFile(roomFile);
                    Hotel tmpHotel = new Hotel(roomList);

                    for (int i = 0; i < staffList.length; i++) {
                        if (staffList[i].getPassword().equals(oldStaffPassword)) {
                            staffList[i].setPassword(newStaffPassword);
                            tmpHotel.addStaff(staffList[i]);
                            continue;
                        }
                        tmpHotel.addStaff(staffList[i]);
                    } 
                    tmpHotel.saveStaffData();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                
            }
            
        });

        staffDOBCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        staffDOBCol.setCellFactory(TextFieldTableCell.forTableColumn());
        staffDOBCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Staff,String>>() {

            @Override
            public void handle(CellEditEvent<Staff, String> event) {
                String newStaffDateOfBirth = event.getNewValue();
                String oldStaffDateOfBirth = event.getOldValue();
                
                try {
                    Staff[] staffList = readStaffFile(staffFile);
                    Room[] roomList = readRoomFile(roomFile);
                    Hotel tmpHotel = new Hotel(roomList);

                    for (int i = 0; i < staffList.length; i++) {
                        if (staffList[i].getDateOfBirth().equals(oldStaffDateOfBirth)) {
                            staffList[i].setDateOfBirth(newStaffDateOfBirth);
                            tmpHotel.addStaff(staffList[i]);
                            continue;
                        }
                        tmpHotel.addStaff(staffList[i]);
                    } 
                    tmpHotel.saveStaffData();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            
        });

        staffICCol.setCellValueFactory(new PropertyValueFactory<>("staffIC"));
        staffICCol.setCellFactory(TextFieldTableCell.forTableColumn());
        staffICCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Staff,String>>() {

            @Override
            public void handle(CellEditEvent<Staff, String> event) {
                String newStaffIC = event.getNewValue();
                String oldStaffIC = event.getOldValue();
                
                try {
                    Staff[] staffList = readStaffFile(staffFile);
                    Room[] roomList = readRoomFile(roomFile);
                    Hotel tmpHotel = new Hotel(roomList);

                    for (int i = 0; i < staffList.length; i++) {
                        if (staffList[i].getStaffIC().equals(oldStaffIC)) {
                            staffList[i].setStaffIC(newStaffIC);
                            tmpHotel.addStaff(staffList[i]);
                            continue;
                        }
                        tmpHotel.addStaff(staffList[i]);
                    } 
                    tmpHotel.saveStaffData();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                
            }
            
        });

        staffRoleCol.setCellValueFactory(new PropertyValueFactory<>("admin"));
        staffRoleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        staffRoleCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Staff,String>>() {

            @Override
            public void handle(CellEditEvent<Staff, String> event) {
                String newStaffRole = event.getNewValue();
                String oldStaffRole = event.getOldValue();
                
                try {
                    Staff[] staffList = readStaffFile(staffFile);
                    Room[] roomList = readRoomFile(roomFile);
                    Hotel tmpHotel = new Hotel(roomList);

                    for (int i = 0; i < staffList.length; i++) {
                        if (staffList[i].getAdmin().equals(oldStaffRole)) {
                            staffList[i].setIsAdmin((newStaffRole.equals("Administrator") ? true : false));
                            tmpHotel.addStaff(staffList[i]);
                            continue;
                        }
                        tmpHotel.addStaff(staffList[i]);
                    } 
                    tmpHotel.saveStaffData();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                
            }
            
        });

        // Setting up the reservation table
        // Second line of each Table Column enables editing and converting the edited value to its correct type
        reservationTable.setEditable(true);
        reservationTable.setPlaceholder(new Label("No reservations to display"));
        
        /**
         * First line setups where the column should look to get its value
         * Second line setups for it to be editable
         * Third line allows changes made to the cell to be saved into the correct destination
         */
        roomIDCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        roomIDCol.setCellFactory(TextFieldTableCell.forTableColumn());
        roomIDCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Reservation,String>>() {

            @Override
            public void handle(CellEditEvent<Reservation, String> event) {
                Reservation reservation = event.getRowValue();
                String newRoomID = event.getNewValue();
                String oldRoomID = event.getOldValue();

                // Removes the old reservation from the Reservations.json
                try {
                    reservation.removeReservation(reservation);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                
                reservation.setRoomID(newRoomID); // Setting the new roomID

                // Saving the new reservation
                try {
                    reservation.saveReservation();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }

                // Removing the dates of the new selected Room
                try {
                    Room[] roomList = readRoomFile(roomFile);
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
                    Room[] roomList = readRoomFile(roomFile);
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

                // Removing the old reservation from Reservations.json
                try {
                    reservation.removeReservation(reservation);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                
                reservation.setCustName(newCustName); // Setting the new roomID

                // Saving the new reservation
                try {
                    reservation.saveReservation();
                } catch (Exception e) {
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

                // Removing the old reservation from Reservations.json
                try {
                    reservation.removeReservation(reservation);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                
                reservation.setCustIC(newCustIC); // Setting the new roomID

                // Saving the new reservation
                try {
                    reservation.saveReservation();
                } catch (Exception e) {
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

                // Removing the old reservation from Reservations.json
                try {
                    reservation.removeReservation(reservation);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                
                reservation.setCustPhone(newCustPhone); // Setting the new roomID

                // Saving the new reservation
                try {
                    reservation.saveReservation();
                } catch (Exception e) {
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

                // Removing the old reservation from Reservations.json
                try {
                    reservation.removeReservation(reservation);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                
                reservation.setCustEmail(newCustEmail); // Setting the new roomID

                // Saving the new reservation
                try {
                    reservation.saveReservation();
                } catch (Exception e) {
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

                // Removing the old reservation from Reservations.json
                try {
                    reservation.removeReservation(reservation);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                
                reservation.setCustFamily(newNoFamily); // Setting the new roomID

                // Saving the new reservation
                try {
                    reservation.saveReservation();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
            
        });
        
        // Check In and Check Out dates cannot be edited, if changes needs to be made, delete the old record and create a new one
        checkInCol.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOut"));

        // Adding all records in Reservations.json to the TableView
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

        try {
            Staff[] staffList = readStaffFile(staffFile);
            if (staffList != null) {
                for (Staff staff: staffList) {
                    staffTable.getItems().add(staff);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Restricting the date to 13/3/2022 to 19/3/2022 only
        // LocalDate minDate = LocalDate.of(2022, 3, 13);
        // LocalDate maxDate = LocalDate.of(2022, 3, 19);
        // checkInDatePicker.setDayCellFactory(d ->
        //     new DateCell() {
        //         @Override public void updateItem(LocalDate item, boolean empty) {
        //             super.updateItem(item, empty);
        //             setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
        //         }});
        
        // checkOutDatePicker.setDayCellFactory(d ->
        //     new DateCell() {
        //         @Override public void updateItem(LocalDate item, boolean empty) {
        //             super.updateItem(item, empty);
        //             setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
        //         }});

        // Building the initial combo box by setting the default date to 13/3/2022, can be changed to today()
        checkInDatePicker.setValue(LocalDate.now());
        
        try {
            Room[] roomList = readRoomFile(roomFile);
            int index = 0;
            // Ensures only rooms that can be booked are shown
            for (Room room: roomList) {
                if (!room.checkAvailability(checkInDatePicker.getValue())) {
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
    /**
     * Function Name: buildComboBox <p>
     * 
     * Inside the function: <p>
     *  1. Create a Room Array <p>
     *  2. Loop through the room array <p>
     *  3. Run the checkAvailability function for every Room object looped through <p>
     *  4. If the checkAvailability function returns true, add it to the ComboBox <p>
     */
    public void buildComboBox() {
        try {
            Room[] roomList = readRoomFile(roomFile);
            int index = 0;
            // Making sure only rooms that can be booked are shown     
            for (Room room: roomList) {
                if (!room.checkAvailability(checkInDatePicker.getValue())) {
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

    /**
     * Function Name: displayWelcomeMessage
     * <p>
     * Inside the function: <p>
     *  1. Sets the header of the Window to a welcome message with the user's name <p>
     * 
     * @param username
     * 
     */
    public void displayWelcomeMessage(String username) {
        welcomeMessage.setText("Welcome, " + username + "!");
    }

    /**
     * Function Name: addNewReservation <p>
     * Inside teh function: <p>
     *  1. Get all the values from the text fields <p>
     *  2. Check whether the fields are provided the correct input, if not display warning text <p>
     *  3. If all fields are correct, start by storing all existing reservations in to an ArrayList <p>
     *  4. Then store the newest reservation into the ArrayList <p>
     *  5. After that, remove the dates between Check In and Check Out from the specific Room object <p>
     *  6. Save the edited Room to the Rooms.json file <p>
     *  7. Update the TableView <p>
     *  8. Clear all fields <p>
     * 
     * @param e
     * 
     */
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
        
        // Displays warning message if any of the fields are wrong or blank
        if (customerName == null || customerName.isBlank() || customerIC == null || customerIC.isBlank() || customerPhone == null || customerPhone.isBlank() ||customerEmail == null || customerEmail.isBlank() ||roomID == null || roomID.isBlank() || roomIDCombo.getSelectionModel().isEmpty() || noFamilyTextField.getText().trim().isBlank() || noFamilyMembers < 0 ||checkInDate == null || checkOutDate == null) {
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
                Room[] roomList = readRoomFile(roomFile);
    
                Hotel tmpHotel = new Hotel(roomList);
                // Getting the list of dates that are booked
    
                List<LocalDate> dates = checkInDate.datesUntil(checkOutDate).collect(Collectors.toList());
                // Removing the dates from the Room object
                try {
                    // Removing the dates between the check in date and check out date (exclusive)
                    for (LocalDate date: dates) {
                        String formattedDate = dateFormatter.format(date);
                        // LocalDate newDate = LocalDate.parse(formattedDate, dateFormatter);
                        tmpHotel.addDate(tmpHotel.getRoom(tmpHotel.searchRoom(roomID)).getRoomID(), formattedDate);
                    }
                    // Remove the check out date
                    tmpHotel.addDate(tmpHotel.getRoom(tmpHotel.searchRoom(roomID)).getRoomID(), dateFormatter.format(checkOutDate));
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

    /**
     * Function Name: clearBookingFields <p>
     * Inside the Function: <p>
     *  1. Clear all fields <p>
     *  2. Set the Check In Datepicker to 13/3/2022 <p>
     *  3. Rebuild the Room ID Combo Box to match the edited Rooms.json <p>
     * 
     * @param e
     * 
     */
    public void clearBookingFields(ActionEvent e) {
        custNameTextField.clear();
        custICTextField.clear();
        noFamilyTextField.clear();
        custPhoneTextField.clear();
        custEmailTextField.clear();
        checkInDatePicker.setValue(LocalDate.now());
        checkOutDatePicker.setValue(null);
        roomIDCombo.setValue(null);
        buildComboBox();
    }

    /**
     * Function Name: addStaff <p>
     * Inside the function: <p>
     *  1. Get all the values from the text fields <p>
     *  2. If all fields are correct, start by storing all existing staff in to an ArrayList <p>
     *  3. Then store the newest staff into the ArrayList <p>
     *  5. Save the edited Staff List to the Staff.json file <p>
     *  6. Update the TableView <p>
     *  7. Clear all fields <p>
     * @param event
     */
    public void addStaff(ActionEvent event) {
        String staffName = staffNameTextField.getText();
        String staffIC = staffICTextField.getText();
        String staffEmail = staffEmailTextField.getText();
        String staffPassword = passwordTextField.getText();
        String staffDOB = dobTextField.getText();

        RadioButton selectedRadioButton = (RadioButton) staffRole.getSelectedToggle();
        String staffRole = selectedRadioButton.getText();

        try {
            Room[] roomList = readRoomFile(roomFile);
            Staff[] staffList = readStaffFile(staffFile);
            Hotel tmpHotel = new Hotel(roomList);
            Staff newStaff = new Staff(staffName, staffEmail, staffPassword, staffDOB, staffIC, ((staffRole.equals("Administrator") ? true : false))); 

            for (Staff staff: staffList) {
                tmpHotel.addStaff(staff);
            }
            tmpHotel.addStaff(newStaff);
            tmpHotel.saveStaffData();

            staffTable.getItems().add(newStaff);
            clearStaffFields(event);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }

    /**
     * Function Name: clearStaffFields <p>
     * Inside the function: <p>
     * 1. Clear all the fields in the Register new staff page <p>
     * @param event
     */
    public void clearStaffFields(ActionEvent event) {
        staffNameTextField.clear();
        staffEmailTextField.clear();
        passwordTextField.clear();
        staffICTextField.clear();
        dobTextField.clear();
    }

    /**
     * Function Name; removeReservation <p>
     * Inside the function: <p>
     *  1. Get the selected row from the TableView (x) <p>
     *  2. Get the roomID and custName values from the selected row (x) <p>
     *  3. Find the record of the combination of the roomID and customer name in Reservations.json (x) <p>
     *  4. Get the Check In and Check Out dates <p>
     *  5. Create a List of all the dates between the Check In and Check Out dates <p>
     *  6. Add the dates of the List back into the correct Room (use searchRoom function) <p>
     *  7. Remove the record from Reservations.json <p>
     *  8. Remove the record from TableView object <p>
     * 
     * @param e
     * 
     */
    public void removeReservation(ActionEvent e) {
        try {
            // Getting an array of all current reservations
            Reservation[] reservations = readReservationFile(reservationFile);
            // Creating a new ArrayList of reservations that will not contain the selected reservation to delete
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
                // If booking format changes, this will need to use removeDate function instead
                for (LocalDate date: dates) {
                    String formattedDate = dateFormatter.format(date);
                    // LocalDate newDate = LocalDate.parse(formattedDate, dateFormatter);
                    tmpHotel.removeDate(tmpHotel.getRoom(tmpHotel.searchRoom(selectedReservation.getRoomID())).getRoomID(), formattedDate);
                }
                // add the check out date
                tmpHotel.removeDate(tmpHotel.getRoom(tmpHotel.searchRoom(selectedReservation.getRoomID())).getRoomID(), dateFormatter.format(reservationCheckOut));
                // Saving the new available dates
                tmpHotel.saveRoomData();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            
            updatedReservations.get(0).removeReservation(selectedReservation); // Updating the file with the newest reservations
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

    /**
     * Function Name: fireStaff <p>
     * Inside the function: <p>
     *  1. Get the selected row from the TableView  <p>
     *  2. Get the Staff object from the selected item in the Table <p>
     *  3. Prepare a new empty ArrayList of Staff <p>
     *  4. Loop through all the existing Staff, and if it matches the one selected, then don't add it to the new ArrayList
     *  5. Add all the other that do not match into the ArrayList
     *  6. Save the list to the Staff.json file
     * 
     * @param e
     * 
     */
    public void fireStaff(ActionEvent event) {
        Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        int selectedRow = staffTable.getSelectionModel().getSelectedIndex();
        try {
            // Getting the necessary variables
            Room[] roomList = readRoomFile(roomFile); // Used to initialize a new Hotel object
            Staff[] staffList = readStaffFile(staffFile); // List of existing staffs
            ArrayList<Staff> newStaffList = new ArrayList<>(); // ArrayList of new staff (does not include fired staff)
            Hotel tmpHotel = new Hotel(roomList); // Hotel object to handle the staff information

            for (Staff staff: staffList) {
                if (staff.equals(selectedStaff)) {
                    continue; // Do not add the fired staff into the newStaffList
                }
                newStaffList.add(staff); // Add the non-fired staff into the newStaffList
            }

            for (Staff staff: newStaffList) {
                tmpHotel.addStaff(staff); // Adding the non-fired staff to the Hotel object
            }

            staffTable.getItems().remove(selectedRow);
            staffTable.getSelectionModel().clearSelection();
            tmpHotel.saveStaffData(); // Saving the non-fired staff list into the JSON file

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function Name: search <p>
     * Inside the function: <p>
     *  1. First get an array of reservations. <p>
     *  2. Get the value in the searchTextField <p>
     *  3. Loop through the reservations array. <p>
     *  4. If the value is the same as the reservation's customer name or email <p>
     *  5. Remove all other reservations in the TableView, leaving only the one that matches <p>
     *  6. If none of them matches, just leave the TableView as it is.  <p>
     * 
     * @param event
     * @throws FileNotFoundException
     * 
     */
    public void search(ActionEvent event) throws FileNotFoundException {

        try {

            Reservation[] tmpReservations = readReservationFile(reservationFile);
            String searchedReservation = searchTextField.getText().toLowerCase().trim();

            for (Reservation reservation: tmpReservations) {
                if (reservation.getCustName().toLowerCase().trim().equals(searchedReservation)) {
                    reservationTable.getItems().removeAll(tmpReservations);
                    reservationTable.getItems().add(reservation);
                } else if (reservation.getCustEmail().toLowerCase().trim().equals(searchedReservation)) {
                    reservationTable.getItems().removeAll(tmpReservations);
                    reservationTable.getItems().add(reservation);
                } else {
                    continue;
                }
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function Name: clearSearch <p>
     * Inside the function: <p>
     *  1. Clear the text field <p>
     *  2. Get an array of the reservation records <p>
     *  3. If the reservation array is not null, then remove the current filtered list <p>
     *  4. Add back all the records in the array to the TableView <p>
     * 
     * @param event
     * @throws FileNotFoundException
     * 
     */
    public void clearSearch(ActionEvent event) throws FileNotFoundException {
        searchTextField.clear();

        if (searchTextField.getText().trim() == null || searchTextField.getText().trim().isEmpty()) {
            // Adding all records in Reservations.json to the TableView
            try {
                Reservation[] reservationList = readReservationFile(reservationFile);
                if (reservationList != null) {
                    reservationTable.getItems().removeAll(reservationList);
                    for (Reservation reservation: reservationList) {
                        reservationTable.getItems().add(reservation);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Function Name: searchStaff <p>
     * Inside the function: <p>
     * 1. Get the existing list of Staff <p>
     * 2. Get the searched text <p>
     * 3. Loop through the list and if it matches, update the table to only contain the ones that match
     * 
     * @param event
     */
    public void searchStaff(ActionEvent event) {
        try {
            Staff[] staffList = readStaffFile(staffFile);
            String searchedStaff = staffSearchTextField.getText().toLowerCase().trim();

            for (Staff staff: staffList) {
                if (staff.getName().toLowerCase().trim().equals(searchedStaff)) {
                    staffTable.getItems().removeAll(staffList);
                    staffTable.getItems().add(staff);
                } else {
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Function Name: staffSearchClear <p>
     * Inside the function: <p>
     * 1. Clear the searched text textfield <p>
     * 2. Loop through the Staff List and add in each item into the table
     * 
     * @param event
     */
    public void staffSearchClear(ActionEvent event) {
        staffSearchTextField.clear();

        try {
            Staff[] staffList = readStaffFile(staffFile);
            if (staffList != null) {
                staffTable.getItems().removeAll(staffList);
                for (Staff staff: staffList) {
                    staffTable.getItems().add(staff);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function Name: printReceipt <p>
     * Inside the function: <p>
     *  1. Get the selected row <p>
     *  2. Get the roomID and custName <p>
     *  3. Loop through the Reservations.json (use GSON fromJSON method) <p>
     *  4. If roomID and custName matches the reservation record, save it to a temporary Reservation object <p>
     *  5. Pop up a new Scene (x) <p>
     *  6. Change the Labels of the new Scene to the correct ones in the temporary Reservation object <p>
     *  7. Create a back button to go back to the TableView scene.  <p>
     * 
     * @param e
     * @throws IOException
     */
    public void printReceipt(ActionEvent e) throws IOException {

        Room[] rooms = readRoomFile(roomFile);
        Hotel tmpHotel = new Hotel(rooms);
        Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            receiptWarningLabel.setText("No reservation selected!");
            receiptWarningLabel.setTextFill(Color.RED);
        } else {
            receiptWarningLabel.setText("");
            receiptWarningLabel.setTextFill(null);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReceiptPage/Receipt.fxml"));
        root = loader.load();
        ReceiptController receiptController = loader.getController();
        receiptController.displayMessage(selectedReservation.getCustName(), selectedReservation.getCustIC(), selectedReservation.getCustPhone(), selectedReservation.getCustEmail(), selectedReservation.getRoomID(), tmpHotel.getRoom(tmpHotel.searchRoom(selectedReservation.getRoomID())).getRoomView(), selectedReservation.getCheckIn(), selectedReservation.getCheckOut(), selectedReservation.getDurationOfStay(), selectedReservation.getRoomPrice(), selectedReservation.getServiceTax(), selectedReservation.getTourismTax(),selectedReservation.getFinalPrice());

        stage =  (Stage)((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Function Name: Logout <p>
     * Inside the function: <p>
     *  1. Brings the user back to the login screen <p>
     * 
     * @param e
     * @throws IOException
     * 
     */
    public void logout(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/LoginPage/Login.fxml"));
        stage =  (Stage)((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
        