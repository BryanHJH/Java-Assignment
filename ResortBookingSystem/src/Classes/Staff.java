package Classes;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Staff {
    // Add a new boolean variable called isAdmin
    private String name, email, password, dateOfBirth, staffIC;
    private int age;

    // Constructors
    // TODO: Add a new parameter: isAdmin (boolean)
    public Staff(String name, String email, String password, String dateOfBirth, String staffIC) throws Exception {
        if (name == null || name.isBlank() || email == null || email.isBlank() || password == null || password.isBlank() || staffIC.isBlank() || staffIC == null || staffIC.length() < 11 || dateOfBirth.isBlank() || dateOfBirth == null) {
            throw new IllegalArgumentException("Fields cannot be blank!");
        }
        
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.staffIC = staffIC;
        this.age = toAge(dateOfBirth);
    }

    public Staff(Staff source) throws Exception {
        this.name = source.name;
        this.email = source.email;
        this.password = source.password;
        this.dateOfBirth = source.dateOfBirth;
        this.staffIC = source.staffIC;
        this.age = toAge(source.dateOfBirth);
    }

    // Getters
    // TODO: add isAdmin getter
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getStaffIC() {
        return staffIC;
    }

    public int getAge() {
        return age;
    }

    // Setters
    // TODO: add setIsAdmin setter
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank or null!");
        }

        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be blank or null!");
        }

        this.email = email;
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be blank or null!");
        }
        this.password = password;
    }

    public void setDateOfBirth(String dateOfBirth) throws Exception {
        if (dateOfBirth == null || dateOfBirth.isBlank()) {
            throw new IllegalArgumentException("Date of birth cannot be blank or null!");
        }
        this.dateOfBirth = dateOfBirth;
        setAge(toAge(dateOfBirth));
    }

    public void setStaffIC(String staffIC) {
        if (staffIC == null || staffIC.isBlank()) {
            throw new IllegalArgumentException("Staff IC cannot be blank or null!");
        }
        this.staffIC = staffIC;
    }

    public void setAge(int age) {
        if (age < 21) {
            throw new IllegalArgumentException("Age cannot be blank or null!");
        }
        this.age = age;
    }

    // Methods
    /**
     * Function name: toAge <p>
     * Inside the function:  <p>
     *  1. Changes the parameter into date object <p>
     *  2. Get the year from the date object <p>
     *  3. Get the current year <p>
     *  4. Subtract the current year and birth year to get the age <p>
     * 
     * @param birthDate
     * @return
     * @throws ParseException
     * 
     */
    public int toAge(String birthDate) throws ParseException {
        SimpleDateFormat converter = new SimpleDateFormat("dd-MM-yyyy");
        Date dob = converter.parse(birthDate);
        SimpleDateFormat getYear = new SimpleDateFormat("yyyy");
        String birthYear = getYear.format(dob);
        
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        
        return (currentYear - Integer.parseInt(birthYear));
    }

    // toString()
    // TODO: Add Role and it displays Administrator if isAdmin == true else Staff
    public String toString() {
        return ("Name\t\t: " + this.name + 
                "\nAge\t\t: " + this.age + 
                "\nIC\t\t: " + this.staffIC +
                "\nDate of birth\t: " + this.dateOfBirth + 
                "\nEmail\t\t: " + this.email + "\n\n");
    }   
}
