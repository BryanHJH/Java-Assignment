package Classes;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Staff {
    // Add a new boolean variable called isAdmin
    private String name, email, password, dateOfBirth, staffIC;
    private int age;
    private boolean isAdmin;

    // Constructors
    public Staff(String name, String email, String password, String dateOfBirth, String staffIC, boolean isAdmin) throws Exception {
        if (name == null || name.isBlank() || email == null || email.isBlank() || password == null || password.isBlank() || staffIC.isBlank() || staffIC == null || staffIC.length() < 11 || dateOfBirth.isBlank() || dateOfBirth == null) {
            throw new IllegalArgumentException("Fields cannot be blank!");
        }
        
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.staffIC = staffIC;
        this.age = toAge(dateOfBirth);
        this.isAdmin = isAdmin;
    }

    public Staff(Staff source) throws Exception {
        this.name = source.name;
        this.email = source.email;
        this.password = source.password;
        this.dateOfBirth = source.dateOfBirth;
        this.staffIC = source.staffIC;
        this.age = toAge(source.dateOfBirth);
        this.isAdmin = source.isAdmin;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }

        if ( !(obj instanceof Staff) ) {
            return false;
        }

        Staff staff = (Staff) obj;

        return (staff.getName().equals(this.name) &&
                staff.getEmail().equals(this.email) &&
                staff.getPassword().equals(this.password) &&
                staff.getDateOfBirth().equals(this.dateOfBirth) &&
                staff.getStaffIC().equals(this.staffIC) &&
                staff.getAge() == this.age &&
                staff.isAdminBool() == this.isAdmin);
    }

    // Getters
    public String getName() {
        return this.name;
    }
    
    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
    
    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getStaffIC() {
        return this.staffIC;
    }

    public int getAge() {
        return this.age;
    }

    public boolean isAdminBool() {
        return this.isAdmin;
    }

    public String getAdmin() {
        return (this.isAdmin ? "Administrator" : "Staff");
    }

    // Setters
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

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
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
    public String toString() {
        return ("Name\t\t: " + this.name + 
                "\nAge\t\t: " + this.age + 
                "\nIC\t\t: " + this.staffIC +
                "\nDate of birth\t: " + this.dateOfBirth + 
                "\nEmail\t\t: " + this.email + "\n\n" + 
                "\nRole\t\t: " + (this.isAdmin ? "Administrator" : "Staff"));
    }   
}
