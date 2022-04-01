package Classes;


import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class Room {
    
    private int numberOfBeds;
    private String roomView, roomID;
    private double price;
    private ArrayList<LocalDate> availableDates;
    
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US );

    // Constructors
    public Room(String roomID, int numberOfBeds, String roomView, double price, ArrayList<LocalDate> dateList) throws ParseException {
        if (roomView == null || roomView.isBlank() || numberOfBeds <= 0 || roomID.length() < 3 || roomID.isBlank() || roomID == null|| price < 0) {
            throw new IllegalArgumentException("Fields you provided are invalid. Please try again.");
        }
        
        this.roomID = roomID;
        this.numberOfBeds = numberOfBeds;
        this.roomView = roomView;
        this.price = price;
        this.availableDates = dateList;
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
    /**
     * Function Name: addAvailableDate
     * <p>
     * Inside the function: <p>
     *  1. Convert the parameter into o LocalDate object <p><p>
     *  2. Loop through the availableDates ArrayList of this Room object<p>
     *  3. If the size of the ArrayList is 0, just add the date into the ArrayList<p>
     *  4. If the size of the ArrayList is 1, add it to the end of the ArrayList<p>
     *  5. Else, get the last date in the ArrayList, if the new LocalDate object from step 1 is before the last date, add it to one index before the last date, else, add it to the end of the ArrayList<p>
     *  6. Sort the ArrayList to ascending order
     * 
     * @param date
     * @throws ParseException
     * 
     */
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
            } else if (comparisonResults == 0) { // Meaning newDate is the same as the current date and replaces the one in the ArrayList
                this.availableDates.remove(lastIndex);
                this.availableDates.add(newDate);
            } else { // Meaning newDate is before the last index and is now being placed as the second last item in the ArrayList
                this.availableDates.add(lastIndex - 2, newDate);
            }

            this.availableDates.sort(Comparator.naturalOrder()); // Sorts the entire ArrayList in ascending order
        }
    }

    /**
     * Function Name: removeAvailableDate
     * Inside the function:<p>
     *  1. If the availableDates ArrayList is 0, throw an error<p>
     *  2. Else, loop through the availableDates ArrayList, if the current LocalDate matches the one in the parameter, remove the element<p>
     * 
     * @param date
     * @throws ParseException
     * 
     */
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
    /**
     * Function Name: checkAvailability<p>
     * Inside the function:<p>
     *  1. Loop through the current Room object's availableDate ArrayList<p>
     *  2. If the current LocalDate object matches the parameter, return true, else return false<p>
     * @param checkIn
     * @return boolean value
     * 
     */
    public boolean checkAvailability(LocalDate checkIn) {
        for (int i = 0; i < this.availableDates.size(); i++) {
            if (this.availableDates.get(i).isEqual(checkIn)) {
                return true; // Means the room is already booked
            }
        }
        return false; // Means the room is not booked yet
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
