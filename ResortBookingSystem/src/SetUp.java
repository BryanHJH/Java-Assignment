import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import Classes.Hotel;
import Classes.Room;
import Classes.Staff;

public class SetUp {

    /**
     * Function Name: readRoomFile <p>
     * Inside the function: <p>
     * 1. Create a Gson and a FileReader object <p>
     * 2. Read the contents of the selected file and store it as a Room Array <p>
     * 3. Return the Room array <p>
     *  
     * @param file
     * @return Room[]
     * @throws FileNotFoundException
     * @throws ParseException
     * 
     */
    public static Room[] readRoomFile(File file) throws FileNotFoundException, ParseException {
        Gson gson = new Gson();
        Reader reader = new FileReader(file);
        Room[] roomList = gson.fromJson(reader, Room[].class);
        return roomList;
    }

    /**
     * Function Name: readStaffFile <p>
     * Inside the function: <p>
     * 1. Create a Gson and a FileReader object <p>
     * 2. Read the contents of the selected file and store it as a Staff Array <p>
     * 3. Return the Staff array <p>
     * 
     * @param file
     * @return Staff[]
     * @throws FileNotFoundException
     * @throws ParseException
     * 
     */
    public static Staff[] readStaffFile(File file) throws FileNotFoundException, ParseException {
        Gson gson = new Gson();
        Reader reader = new FileReader(file);
        Staff[] staffList = gson.fromJson(reader, Staff[].class);
        return staffList;
    }

    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US);

    /**
     * main method <p>
     * Used to add the necessary information into the text files before the system can run <p>
     * E.g. <p>
     * 1. adds the room information into the system before the system is first launched <p>
     * 2. adds the hotel staff into the system before the system is first launched <p>
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        
        ArrayList<LocalDate> dateList = new ArrayList<LocalDate>() {
            {
                add(LocalDate.parse(("13-03-2022"), dateFormatter));
                add(LocalDate.parse(("14-03-2022"), dateFormatter));
                add(LocalDate.parse(("15-03-2022"), dateFormatter));
                add(LocalDate.parse(("16-03-2022"), dateFormatter));
                add(LocalDate.parse(("17-03-2022"), dateFormatter));
                add(LocalDate.parse(("18-03-2022"), dateFormatter));
                add(LocalDate.parse(("19-03-2022"), dateFormatter));
            }
        };

        Room[] allRoomList = {
            new Room("101", 3, "Jungle", 350.00, dateList),
            new Room("102", 3, "Jungle", 350.00, dateList),
            new Room("103", 1, "Jungle", 350.00, dateList),
            new Room("104", 3, "Jungle", 350.00, dateList),
            new Room("105", 1, "Jungle", 350.00, dateList),
            new Room("106", 3, "Jungle", 350.00, dateList),
            new Room("107", 2, "Jungle", 350.00, dateList),
            new Room("108", 2, "Jungle", 350.00, dateList),
            new Room("109", 3, "Jungle", 350.00, dateList),
            new Room("110", 2, "Jungle", 350.00, dateList),
            new Room("201", 3, "Sea", 350.00, dateList),
            new Room("202", 3, "Sea", 350.00, dateList),
            new Room("203", 1, "Sea", 350.00, dateList),
            new Room("204", 3, "Sea", 350.00, dateList),
            new Room("205", 1, "Sea", 350.00, dateList),
            new Room("206", 3, "Sea", 350.00, dateList),
            new Room("207", 2, "Sea", 350.00, dateList),
            new Room("208", 2, "Sea", 350.00, dateList),
            new Room("209", 3, "Sea", 350.00, dateList),
            new Room("210", 2, "Sea", 350.00, dateList)
        };
        

        Hotel testHotel = new Hotel(allRoomList);
        testHotel.addStaff(new Staff("John", "john@resort.com", "johnresort", "13-3-1990", "111111102938", true));
        testHotel.addStaff(new Staff("Ron", "ron@resort.com", "ronresort", "13-3-1992", "111111102928", false));
        testHotel.addStaff(new Staff("Ren", "ren@resort.com", "renresort", "14-4-1980", "111111101938", false));
        testHotel.addStaff(new Staff("Bam", "bam@resort.com", "bamresort", "15-5-1950", "111111103938", false));
        testHotel.addStaff(new Staff("Gavin", "gavin@resort.com", "gavinresort", "13-3-1995", "111111102838", false));

        testHotel.saveStaffData();
        testHotel.saveRoomData();
    }
}
