package Classes;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

public class Hotel {
    private ArrayList<Staff> staffList;
    private Room[] roomList;

    // Constructors
    public Hotel(Room[] roomList) {
        this.staffList = new ArrayList<Staff>();
        this.roomList = Arrays.copyOf(roomList, roomList.length);
    }

    // Getters
    public Room getRoom(int index) {
        return this.roomList[index];
    }

    public Staff getStaff(int index) {
        return this.staffList.get(index);
    }

    // Setters
    public void setRoom(int index, Room newRoom) {
        this.roomList[index] = new Room(newRoom);
    }

    public void setStaff(int index, Staff newStaff) {
        this.staffList.set(index, newStaff);
    }

    // Methods
    // Adding and removing Staff
    /**
     * Function Name: addStaff <p>
     * Inside the function: <p>
     *  1. Adds a new Staff object into the Staff ArrayList <p>
     * 
     * @param newStaff
     * 
     */
    public void addStaff(Staff newStaff) {
        this.staffList.add(newStaff);
    }

    /**
     * Function Name: removeStaff <p>
     * Inside the Function <p>
     *  1. Loop through the Staff object's ArrayList <p>
     *  2. if the staff name is the same as the name passed in as argument, remove it <p>
     * 
     * @param staffName
     * 
     */
    public void removeStaff(String staffName) {
        for (int i = 0; i < this.staffList.size(); i++) {
            if (this.staffList.get(i).getName().equals(staffName)) {
                this.staffList.remove(i);
            }
        }
    }

    // Adding and removing available dates for rooms
    /**
     * Function Name: addDate <p>
     * Inside the function <p>
     *  1. Loop through the roomList Room Array <p>
     *  2. If the room ID of the current Room object is the same as the room ID passed in as argument <p>
     *  3. Run the addAvailableDate function of that Room object <p>
     * 
     * @param roomID
     * @param date
     * @throws Exception
     * 
     */
    public void addDate(String roomID, String date) throws Exception {
        for (int i = 0; i < this.roomList.length; i++) {
            if (this.roomList[i].getRoomID().equals(roomID)) {
                this.roomList[i].addAvailableDate(date);
            }
        }
    }
    
    /**
     * Function Name: removeDate <p>
     * Inside the function <p>
     *  1. Loop through the Room Array <p>
     *  2. If the room ID is the same as the one passed in as argument <p>
     *  3. Run the removeAvailableDate function of that Room object <p>
     * 
     * @param roomID
     * @param date
     * @throws Exception
     * 
     */
    public void removeDate(String roomID, String date) throws Exception {
        for (int i = 0; i < this.roomList.length; i++) {
            if (this.roomList[i].getRoomID().equals(roomID)) {
                this.roomList[i].removeAvailableDate(date);
            }
        }
    }

    // Writing to a file
    public void writeRoomFile(File file, Room[] arr) throws Exception {
        FileWriter fwriter = new FileWriter(file);
        // List<Room> roomArr = Arrays.asList(arr);
        ArrayList<Room> roomArr = new ArrayList<Room>(Arrays.asList(arr));

        try (BufferedWriter writer = new BufferedWriter(fwriter)) {
            Gson gson = new Gson();
            gson.toJson(roomArr, writer);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Function Name: writeStaffFile <p>
     * Inside the function <p>
     *  1. Create a FileWriter object <p>
     *  2. Create a BufferedWriter object <p>
     *  3. Create a Gson object <p>
     *  4. WRite the ArrayList passed in as argument into the File specified in json format
     * 
     * @param file
     * @param arr
     * @throws Exception
     * 
     */
    public void writeStaffFile(File file, ArrayList<Staff> arr) throws Exception {
        FileWriter fwriter = new FileWriter(file);

        try (BufferedWriter writer = new BufferedWriter(fwriter)) {
            Gson gson = new Gson();
            gson.toJson(arr, writer);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Writing the arrays to a text file
    /**
     * Function Name: saveStaffData <p>
     * Inside the function <p>
     *  1. Set the filepath to the Staff file <p>
     *  2. Run writeStaffFile to save Staff ArrayList into the specified file <p>
     * 
     * @throws Exception
     * 
     */
    public void saveStaffData() throws Exception {
        // String staff = "";
        File staffFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Staff.json");
        writeStaffFile(staffFile, this.staffList);
    }

    /**
     * Function Name: saveRoomData <p>
     * Inside the function <p>
     *  1. Set the filepath to the Room file <p>
     *  2. Run writeRoomFile to save Room ArrayList into the specified file <p>
     * 
     * @throws Exception
     * 
     */
    public void saveRoomData() throws Exception {
        // String allRooms = "";
        File roomFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.json");
        writeRoomFile(roomFile, this.roomList);
    }

    // Searching rooms
    /**
     * Function Name: searchRoom <p>
     * Inside the function <p>
     *  1. Loop through the Room ArrayList
     *  2. If the roomID of the current Room object matches the room ID passed in as argument, return the current index
     * 
     * @param roomID
     * @return index of the matching room
     * 
     */
    public int searchRoom(String roomID) {
        for (int i = 0; i < this.roomList.length; i++) {
            if (this.roomList[i].getRoomID().equals(roomID)) {
                return i;
            }
        }
        return -1;
    }

    // toString
    public String toString() {
        String message = "Staff List: \n";

        for (int i = 0; i < staffList.size(); i++) {
            message += staffList.get(i).getName();
            message += ", ";
        }

        message += "\n\nRoom List: ";

        for (int i = 0; i < roomList.length; i++) {
            message += roomList[i].getRoomID();
            message += ", ";
        }

        return message;
    }
}
