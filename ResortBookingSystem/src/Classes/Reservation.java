package Classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Scanner;

public class Reservation {
    private String roomID, custIC, custName, custPhone, custEmail;
    private int custFamily, durationOfStay;
    private LocalDate checkIn, checkOut;


    // Constructors
    public Reservation(String roomID, String custIC, String custName, String custPhone, String custEmail, int custFamily, LocalDate checkIn, LocalDate checkOut) {
        if (roomID == null || roomID.isBlank() ||(custIC == null || custIC.isBlank() || custName == null || custName.isBlank() || custPhone == null || custPhone.isBlank() || custEmail == null || custEmail.isBlank() || custFamily < 0)) {
            throw new IllegalArgumentException("Fields contain invalid values");
        }

        this.roomID = roomID;
        this.custIC = custIC;
        this.custName = custName;
        this.custPhone = custPhone;
        this.custEmail = custEmail;
        this.custFamily = custFamily;
        this.checkIn = checkIn;
        this.checkOut = checkOut;

        Period duration = Period.between(this.checkIn, this.checkOut);
        this.durationOfStay = duration.getDays();
    }

    public Reservation(Reservation source) {
        this.custIC = source.custIC;
        this.custName = source.custName;
        this.custPhone = source.custPhone;
        this.custEmail = source.custEmail;
        this.custFamily = source.custFamily;
        this.durationOfStay = source.durationOfStay;
    }

    // Getters
    public String getRoomID() {
        return roomID;
    }

    public String getCustName() {
        return custName;
    }

    public String getCustIC() {
        return custIC;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public int getCustFamily() {
        return custFamily;
    }

    public int getDurationOfStay() {
        return durationOfStay;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    // Setters
    public void setRoomID(String roomID) {
        if (roomID == null || roomID.isBlank()) {
            throw new IllegalArgumentException("Room ID cannot be blank!");
        }
        this.roomID = roomID;
    }

    public void setCustName(String custName) {
        if (custName == null || custName.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be blank or null!");
        }

        this.custName = custName;
    }

    public void setCustPhone(String custPhone) {
        if (custPhone == null || custPhone.isBlank()) {
            throw new IllegalArgumentException("Customer address cannot be blank or null!");
        }

        this.custPhone = custPhone;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }
    
    public void setCustIC(String custIC) {
        if (custIC == null || custIC.isBlank()) {
            throw new IllegalArgumentException("Customer IC cannot be blank or null!");
        }

        this.custIC = custIC;
    }

    public void setCustFamily(int custFamily) {
        if (custFamily < 0) {
            throw new IllegalArgumentException("Customer's number of family members cannot be less than zero!");
        }

        this.custFamily = custFamily;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public void setDurationOfStay(int durationOfStay) {
        if (durationOfStay <= 0) {
            throw new IllegalArgumentException("Customer's duration of stay cannot be blank or null!");
        }

        this.durationOfStay = durationOfStay;
    }


    // Methods
    public void writeFile(File file, String message) throws Exception {
        FileWriter fwriter = new FileWriter(file);

        try (BufferedWriter writer = new BufferedWriter(fwriter)) {
            writer.write(message);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<String> readFile(File file) throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        ArrayList<String> contents = new ArrayList<String>();

        while (reader.hasNextLine()) {
            contents.add(reader.nextLine());
        }
        reader.close();

        return contents;
    }

    public void saveReservation() throws Exception {
        double price;
        File reservationFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Reservations.txt");
        File roomFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.txt");

        ArrayList<String> rooms = readFile(roomFile);
        double finalPrice = 0;
        
        for (String room: rooms) {
            String[] roomDetails = room.split("; ");
            if (roomDetails[0].equals(this.roomID)) {
                double roomPrice = Double.parseDouble(roomDetails[3])*durationOfStay;
                double roomCharges = roomPrice * 0.10;
                double tourismTax = 10*this.durationOfStay;
                finalPrice += Math.round((roomPrice + roomCharges + tourismTax)*100)/100;
            }
        }

        String message = (this.roomID + "; " + 
                          this.custName + "; " + 
                          this.custIC + "; " + 
                          this.custPhone + "; " + 
                          this.custEmail + "; " + 
                          this.custFamily + "; " + 
                          this.checkIn + "; " + 
                          this.checkOut + "; " +
                          this.durationOfStay + "; " + 
                          finalPrice);

        writeFile(reservationFile, message);
    }
}
