package Classes;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Room {
    
    private int numberOfBeds;
    private String roomView, roomID;
    private double price;
    private ArrayList<LocalDate> availableDates;
    
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US );

    // Constructors
    public Room(String roomID, int numberOfBeds, String roomView, double price, ArrayList<LocalDate> availableDates) throws ParseException {
        if (roomView == null || roomView.isBlank() || numberOfBeds <= 0 || roomID.length() < 3 || roomID.isBlank() || roomID == null|| price < 0) {
            throw new IllegalArgumentException("Fields you provided are invalid. Please try again.");
        }
        
        this.roomID = roomID;
        this.numberOfBeds = numberOfBeds;
        this.roomView = roomView;
        this.price = price;
        this.availableDates = new ArrayList<>(availableDates);
    }

    public Room(Room source) {
        this.roomID = source.roomID;
        this.numberOfBeds = source.numberOfBeds;
        this.roomView = source.roomView;
        this.price = source.price;
        this.availableDates = source.availableDates;
    }

    // Getters
    public String getRoomID() {
        return roomID;
    }

    public String getRoomView() {
        return roomView;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getPrice() {
        return price;
    }

    public String getAvailableDates() {
        String message = "";
        for (int i = 0; i < this.availableDates.size(); i++) {
            message += (dateFormatter.format(this.availableDates.get(i)));
            message += ", ";
        }

        return message;
    }

    // Setters
    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public void setRoomView(String roomView) {
        this.roomView = roomView;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }

    // Adding and removing dates
    public void addAvailableDate(String date) throws ParseException {

        LocalDate newDate = LocalDate.parse(date, dateFormatter);

        if (this.availableDates.size() == 0) {
            this.availableDates.add(newDate);
        } else if (this.availableDates.size() == 1) {
            if (newDate.isAfter(this.availableDates.get(0))) {
                this.availableDates.add(newDate);
            }
        } else {
            int lastIndex = this.availableDates.size() - 1;
            LocalDate lastDate = this.availableDates.get(lastIndex);
            int comparisonResults = newDate.compareTo(lastDate);
            if (comparisonResults > 0) { // Meaning newDate is after the last item in this.availableDate
                this.availableDates.add(newDate);
            } else if (comparisonResults == 0) {
                this.availableDates.remove(lastIndex);
                this.availableDates.add(newDate);
            } else {
                this.availableDates.add(lastIndex - 2, newDate);
            }

            this.availableDates.sort(Comparator.naturalOrder());
        }
        
        // for (int i = 0; i < this.availableDates.size(); i++) {
        //     if (newDate.isBefore(this.availableDates.get(i))) {
        //         this.availableDates.add(i, newDate);
        //         break;
        //     }
        // }
    }

    public void removeAvailableDate(String date) throws ParseException {
        if (this.availableDates.size() == 0) {
            throw new IllegalStateException("You cannot remove anything anymore!");
        }

        for (int i = 0; i < this.availableDates.size(); i++) {
            if (LocalDate.parse(date, dateFormatter).isEqual(this.availableDates.get(i))) {
                this.availableDates.remove(i);
            }
        }
    }

    // Check availability
    public boolean checkAvailability(LocalDate checkIn) {
        for (int i = 0; i < this.availableDates.size(); i++) {
            if (this.availableDates.get(i).isEqual(checkIn)) {
                return true;
            }
        }
        return false;
    }

    // toString
    public String toString() {

        String[] tmpDates = new String[this.availableDates.size()];
        for (int i = 0; i < this.availableDates.size(); i++) {
            tmpDates[i] = dateFormatter.format(this.availableDates.get(i));
        }

        return ("Room Number\t: " + this.roomID + 
                "\nRoom View\t: " + this.roomView + 
                "\nNumber of beds\t: " + this.numberOfBeds +
                "\nPrice\t\t: " + this.price + 
                "\nAvailability\t: " + Arrays.toString(tmpDates));
    }  
}
