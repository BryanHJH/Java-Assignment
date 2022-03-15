package Classes;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Room {
    
    private int numberOfBeds;
    private String roomView, roomID;
    private double price;
    private ArrayList<Date> availableDates;
    
    static SimpleDateFormat setDates = new SimpleDateFormat("dd-MM-yyyy");

    // Constructors
    public Room(String roomID, int numberOfBeds, String roomView, double price) throws ParseException {
        if (roomView == null || roomView.isBlank() || numberOfBeds <= 0 || roomID.length() < 3 || roomID.isBlank() || roomID == null|| price < 0) {
            throw new IllegalArgumentException("Fields you provided are invalid. Please try again.");
        }
        
        this.roomID = roomID;
        this.numberOfBeds = numberOfBeds;
        this.roomView = roomView;
        this.price = price;

        this.availableDates = new ArrayList<Date>() {
            {
                add(setDates.parse("13-3-2022"));
                add(setDates.parse("14-3-2022"));
                add(setDates.parse("15-3-2022"));
                add(setDates.parse("16-3-2022"));
                add(setDates.parse("17-3-2022"));
                add(setDates.parse("18-3-2022"));
                add(setDates.parse("19-3-2022"));
            }
        };
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
            message += (setDates.format(this.availableDates.get(i)));
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
        for (int i = 0; i < this.availableDates.size(); i++) {
            if (setDates.parse(date).before(this.availableDates.get(i))) {
                this.availableDates.add(i, setDates.parse(date));
                break;
            }
        }
    }

    public void removeAvailableDate(String date) throws ParseException {
        if (this.availableDates.size() == 0) {
            throw new IllegalStateException("You cannot remove anything anymore!");
        }

        for (int i = 0; i < this.availableDates.size(); i++) {
            if (setDates.parse(date) == this.availableDates.get(i)) {
                this.availableDates.remove(i);
            }
        }
    }

    // toString
    public String toString() {

        String[] tmpDates = new String[this.availableDates.size()];
        for (int i = 0; i < this.availableDates.size(); i++) {
            tmpDates[i] = setDates.format(this.availableDates.get(i));
        }

        return ("Room Number\t: " + this.roomID + 
                "\nRoom View\t: " + this.roomView + 
                "\nNumber of beds\t: " + this.numberOfBeds +
                "\nPrice\t\t: " + this.price + 
                "\nAvailability\t: " + Arrays.toString(tmpDates));
    }  
}
