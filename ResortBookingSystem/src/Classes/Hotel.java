package Classes;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Hotel {
    private ArrayList<Staff> staffList;
    private Room[] jungleRoomList;
    private Room[] seaRoomList;

    // Constructors
    public Hotel(Room[] jungleRoomList, Room[] seaRoomList) {
        this.staffList = new ArrayList<Staff>();
        this.jungleRoomList = Arrays.copyOf(jungleRoomList, jungleRoomList.length);
        this.seaRoomList = Arrays.copyOf(seaRoomList, seaRoomList.length);
    }

    // Getters
    public Room getJungleRoom(int index) {
        return this.jungleRoomList[index];
    }

    public Room getSeaRoom(int index) {
        return this.seaRoomList[index];
    }

    public Staff getStaff(int index) {
        return this.staffList.get(index);
    }

    // Setters
    public void setJungleRoom(int index, Room newJungleRoom) {
        this.jungleRoomList[index] = new Room(newJungleRoom);
    }

    public void setSeaRoom(int index, Room newSeaRoom) {
        this.seaRoomList[index] = new Room(newSeaRoom);
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

    // Writing to a file
    public void writeFile(File file, String message) throws Exception {
        FileWriter fwriter = new FileWriter(file);

        try (BufferedWriter writer = new BufferedWriter(fwriter)) {
            writer.write(message);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Writing the arrays to a text file
    public void saveData() throws Exception {
        String staff = "";
        String jungleRooms = "";
        String seaRooms = "";

        File staffFile = new File("Text Files/Staff.txt");
        File jungleFile = new File("Text Files/Jungle Rooms.txt");
        File seaFile = new File("Text Files/Sea Rooms.txt");

        for (Staff item: this.staffList) {
            String message = item.getName() + "; " + item.getEmail() + "; " + item.getPassword() + "; " + item.getDateOfBirth() + "; " + item.getStaffIC();
            staff += message;
            staff += "\n";
        }

        for (Room room: this.jungleRoomList) {
            String message = room.getRoomID() + "; " + room.getNumberOfBeds() + "; " + room.getRoomView() + "; " + room.getPrice();
            jungleRooms += message;
            jungleRooms += "\n";
        }
        
        for (Room room: this.seaRoomList) {
            String message = room.getRoomID() + "; " + room.getNumberOfBeds() + "; " + room.getRoomView() + "; " + room.getPrice();
            seaRooms += message;
            seaRooms += "\n";
        }

        writeFile(staffFile, staff);
        writeFile(jungleFile, jungleRooms);
        writeFile(seaFile, seaRooms);
    }

    // toString
    public String toString() {
        String message = "Staff List: \n";

        for (int i = 0; i < staffList.size(); i++) {
            message += staffList.get(i).getName();
            message += ", ";
        }

        message += "\n\nRoom List (Jungle View): ";

        for (int i = 0; i < jungleRoomList.length; i++) {
            message += jungleRoomList[i].getRoomID();
            message += ", ";
        }

        message += "\n\nRoom List (Sea View): ";
        for (int i = 0; i < seaRoomList.length; i++) {
            message += seaRoomList[i].getRoomID();
            message += ", ";
        }

        return message;
    }
}
