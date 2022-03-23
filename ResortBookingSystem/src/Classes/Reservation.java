package Classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

public class Reservation {
    private String roomID, custIC, custName, custPhone, custEmail;
    private int custFamily, durationOfStay;
    private LocalDate checkIn, checkOut;
    private double finalPrice, roomPrice, serviceTax, tourismTax;
    static File reservationFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Reservations.json");
    static File roomFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.json");

    // Constructors
    public Reservation(String roomID, String custName, String custIC, String custPhone, String custEmail, int custFamily, LocalDate checkIn, LocalDate checkOut) {
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

        try {
            double totalPrice = 0;
            Room[] roomList = readRoomFile(roomFile);
            for (Room room: roomList) {
                if (room.getRoomID().equals(this.roomID)) {
                    this.roomPrice = Math.round(room.getPrice()*durationOfStay);
                    this.serviceTax = Math.round((this.roomPrice * 0.10));
                    this.tourismTax = Math.round((10*this.durationOfStay));
                    totalPrice += Math.round((this.roomPrice + this.serviceTax + this.tourismTax)*100)/100;
                    this.finalPrice = totalPrice;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


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

    public double getRoomPrice() {
        return roomPrice;
    }

    public double getServiceTax() {
        return serviceTax;
    }

    public double getTourismTax() {
        return tourismTax;
    }

    public double getFinalPrice() {
        return finalPrice;
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

    public void setTourismTax(double tourismTax) {
        this.tourismTax = tourismTax * this.durationOfStay;
    }

    public void setFinalPrice(double finalPrice) {
        if (finalPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }
        this.finalPrice = finalPrice;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Reservation r1 = (Reservation) obj;

        return (this.roomID.equals(r1.roomID) &&
                this.custIC.equals(r1.custIC) &&
                this.custName.equals(r1.custName) &&
                this.custPhone.equals(r1.custPhone) &&
                this.custEmail.equals(r1.custEmail) &&
                this.custFamily ==  r1.custFamily &&
                this.checkIn.isEqual(r1.checkIn) &&
                this.checkOut.isEqual(r1.checkOut));

    }


    // Methods
    /**
     * Function name: writeFile<p>
     * Inside the function:<p>
     *  1. Creates a file writer with the File parameter<p>
     *  2. Creates a gson object<p>
     *  3. Creates a BufferedWriter and writes into the File object passed in as argument<p>
     *  4. Contents of the file is stored in json format<p>
     * 
     * @param file
     * @param reservations
     * @throws Exception
     * 
     */
    public void writeFile(File file, ArrayList<Reservation> reservations) throws Exception {
        FileWriter fwriter = new FileWriter(file);
        Gson gson = new Gson();

        try (BufferedWriter writer = new BufferedWriter(fwriter)) {
            gson.toJson(reservations, writer);
            // writer.write(message);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Function Name: readRoomFile<p>
     * Inside teh function:<p>
     *  1. Creates a Gson object<p>
     *  2. Creates a Reader object<p>
     *  3. Reads the json contents from the file passed in as argument<p>
     *  4. Stores the json contents as an Array with matching type<p>
     *  5. returns the Array<p>
     * 
     * @param file
     * @return Room[]
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
     * Function Name: readReservationFile<p>
     * Inside the function:<p>
     *  1. Steps are similar to readRoomFile, with the return type being different<p>
     * 
     * @param file
     * @return Reservation[]
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
     * Function Name: saveReservation<p>
     * Inside the function:<p>
     *  1. Create a Reservation Array and initiate it<p>
     *  2. Create a new empty Reservation ArrayList<p>
     *  3. if the Reservation Array is not empty, add all the reservation objects in the array to the ArrayList<p>
     *  4. After adding all the existing reservation (from the Array), add the newest Reservation record into the ArrayList<p>
     *  5. Use the writeFile function to write the newest ArrayList into the correct file<p>
     * 
     * @throws Exception
     * 
     */
    public void saveReservation() throws Exception {
        Reservation[] reservationList = readReservationFile(reservationFile);
        ArrayList<Reservation> newReservations = new ArrayList<>();

        if (reservationList != null) {
            for (Reservation reservation: reservationList) {
                newReservations.add(reservation);
            }
            newReservations.add(new Reservation(this.roomID, this.custName, this.custIC, this.custPhone, this.custEmail, this.custFamily, this.checkIn, this.checkOut));
        } else {
            newReservations.add(new Reservation(this.roomID, this.custName, this.custIC, this.custPhone, this.custEmail, this.custFamily, this.checkIn, this.checkOut));
        }
        writeFile(reservationFile, newReservations);
    }

    /**
     * Function name: removeReservation<p>
     * Inside the function<p>
     *  1. Create a new Reservation Array with all existing records<p>
     *  2. Create an ArrayList from the Reservation Array<p>
     *  3. If any of the reservation objects in the ArrayList matches the one passed in as an argument, remove it<p>
     *  4. Save the newly edited ArrayList into the Reservations.json file<p>
     * 
     * @param reservation
     * @throws Exception
     * 
     */
    public void removeReservation(Reservation reservation) throws Exception {
        Reservation[] reservationList = readReservationFile(reservationFile);
        ArrayList<Reservation> updatedReservations = new ArrayList<Reservation>(Arrays.asList(reservationList));
        for (int i = 0; i < updatedReservations.size(); i++) {
            if (updatedReservations.get(i).equals(reservation)) {
                updatedReservations.remove(i);
            }
        }
        writeFile(reservationFile, updatedReservations);
    }

    public String toString() {
        return ("Room ID: " + this.roomID + 
                "\nCustomer Name: " + this.custName + 
                "\nCustomer IC: " + this.custIC + 
                "\nCustomer Phone Number: " + this.custPhone + 
                "\nCustomer Email: " + this.custEmail + 
                "\nNumber of family members: " + this.custFamily + 
                "\nCheck In: " + this.checkIn + 
                "\nCheck Out: " + this.checkOut + 
                "\nDuration of stay: " + this.durationOfStay);
    }
}
