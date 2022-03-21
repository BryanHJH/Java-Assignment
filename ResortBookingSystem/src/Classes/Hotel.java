package Classes;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
    public void addStaff(Staff newStaff) {
        this.staffList.add(newStaff);
    }

    public void removeStaff(String staffName) {
        for (int i = 0; i < this.staffList.size(); i++) {
            if (this.staffList.get(i).getName().equals(staffName)) {
                this.staffList.remove(i);
            }
        }
    }

    // Adding and removing available dates for rooms
    public void addDate(String roomID, String date) throws Exception {
        for (int i = 0; i < this.roomList.length; i++) {
            if (this.roomList[i].getRoomID().equals(roomID)) {
                this.roomList[i].addAvailableDate(date);
            }
        }
    }
    
    public void removeDate(String roomID, String date) throws Exception {
        for (int i = 0; i < this.roomList.length; i++) {
            if (this.roomList[i].getRoomID().equals(roomID)) {
                this.roomList[i].removeAvailableDate(date);
            }
        }
    }

    // Reading from a file
    // public void getData(ArrayList<File> files) throws FileNotFoundException {
        
    //     for (File file: files) {
    //         Scanner fileScan = new Scanner(file);
    //         while (fileScan.hasNextLine()) {
    //             String[] entities = fileScan.nextLine().split(", ");
    //             for (String entity: entities) {
    //                 String[] details = entity.split("; ");
    //             }
    //         }

    //         fileScan.close();
    //     }
    // }

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
    public void saveStaffData() throws Exception {
        // String staff = "";
        File staffFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Staff.json");
        writeStaffFile(staffFile, this.staffList);
    }

    public void saveRoomData() throws Exception {
        // String allRooms = "";
        File roomFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.json");
        writeRoomFile(roomFile, this.roomList);
    }

    // Searching rooms
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
