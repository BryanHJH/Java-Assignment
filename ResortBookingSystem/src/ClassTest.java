import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import Classes.Hotel;
import Classes.Room;
import Classes.Staff;

public class ClassTest {

    public static Room[] readRoomFile(File file) throws FileNotFoundException, ParseException {
        Gson gson = new Gson();
        Reader reader = new FileReader(file);
        Room[] roomList = gson.fromJson(reader, Room[].class);
        return roomList;
    }

    public static Staff[] readStaffFile(File file) throws FileNotFoundException, ParseException {
        Gson gson = new Gson();
        Reader reader = new FileReader(file);
        Staff[] staffList = gson.fromJson(reader, Staff[].class);
        return staffList;
    }

    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US);

    public static void main(String[] args) throws Exception {
        
        // TODO: If the booking format is changed, this part of the code can be removed, as each Room now only needs to add dates into the arraylist instead of removing them. 
        // ArrayList<LocalDate> dateList = new ArrayList<LocalDate>() {
        //     {
        //         add(LocalDate.parse(("13-03-2022"), dateFormatter));
        //         add(LocalDate.parse(("14-03-2022"), dateFormatter));
        //         add(LocalDate.parse(("15-03-2022"), dateFormatter));
        //         add(LocalDate.parse(("16-03-2022"), dateFormatter));
        //         add(LocalDate.parse(("17-03-2022"), dateFormatter));
        //         add(LocalDate.parse(("18-03-2022"), dateFormatter));
        //         add(LocalDate.parse(("19-03-2022"), dateFormatter));
        //     }
        // };

        Room[] allRoomList = {
            new Room("101", 3, "Jungle", 350.00),
            new Room("102", 3, "Jungle", 350.00),
            new Room("103", 1, "Jungle", 350.00),
            new Room("104", 3, "Jungle", 350.00),
            new Room("105", 1, "Jungle", 350.00),
            new Room("106", 3, "Jungle", 350.00),
            new Room("107", 2, "Jungle", 350.00),
            new Room("108", 2, "Jungle", 350.00),
            new Room("109", 3, "Jungle", 350.00),
            new Room("110", 2, "Jungle", 350.00),
            new Room("201", 3, "Sea", 350.00),
            new Room("202", 3, "Sea", 350.00),
            new Room("203", 1, "Sea", 350.00),
            new Room("204", 3, "Sea", 350.00),
            new Room("205", 1, "Sea", 350.00),
            new Room("206", 3, "Sea", 350.00),
            new Room("207", 2, "Sea", 350.00),
            new Room("208", 2, "Sea", 350.00),
            new Room("209", 3, "Sea", 350.00),
            new Room("210", 2, "Sea", 350.00)
        };
        

        Hotel testHotel = new Hotel(allRoomList);
        testHotel.addStaff(new Staff("John", "john@resort.com", "johnresort", "13-3-1990", "111111102938"));
        testHotel.addStaff(new Staff("Ron", "ron@resort.com", "ronresort", "13-3-1992", "111111102928"));
        testHotel.addStaff(new Staff("Ren", "ren@resort.com", "renresort", "14-4-1980", "111111101938"));
        testHotel.addStaff(new Staff("Bam", "bam@resort.com", "bamresort", "15-5-1950", "111111103938"));
        testHotel.addStaff(new Staff("Gavin", "gavin@resort.com", "gavinresort", "13-3-1995", "111111102838"));

        testHotel.removeStaff("Ron");
        System.out.println(testHotel);
        testHotel.saveStaffData();
        testHotel.saveRoomData();

        File staffFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Staff.txt");
        Staff[] staffList = readStaffFile(staffFile);
        for (Staff staff: staffList) {
            System.out.println(staff);
        }

        // String[] test = printContents(staffFile);

        // for (String staff: test) {
        //     String[] staffDetails = staff.split("; ");
        //     for (String details: staffDetails) {
        //         System.out.println(details);
        //     }

        //     System.out.println("\n");
        // }
        
        File roomFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Rooms.json");
        // String[] roomTest = printContents(roomFile);
        Room[] roomList = readRoomFile(roomFile);
        for (Room room: roomList) {
            if (room.getRoomID().equals("101")) {
                System.out.println(room.getRoomID());
            }
        }

        // testHotel.removeDate(testHotel.getRoom(testHotel.searchRoom("110")).getRoomID(), "14-03-2022");
        LocalDate startDate = LocalDate.parse("13-03-2022", dateFormatter);
        LocalDate endDate = LocalDate.parse("15-03-2022", dateFormatter);
        
        // ArrayList<LocalDate> newDates = new ArrayList<LocalDate>();
        List<LocalDate> dates = startDate.datesUntil(endDate).collect(Collectors.toList());
        // System.out.println(dates);
        for (LocalDate date: dates) {
            testHotel.removeDate(testHotel.getRoom(testHotel.searchRoom("101")).getRoomID(), dateFormatter.format(date));
        }
        testHotel.removeDate(testHotel.getRoom(testHotel.searchRoom("101")).getRoomID(), "15-03-2022");

        // System.out.println(newDates);
        System.out.println(testHotel.getRoom(testHotel.searchRoom("101")).getAvailableDates());
        System.out.println(testHotel.getRoom(1).getAvailableDates());
    }
}
