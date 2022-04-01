package LoginPage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;

import Classes.Staff;
import MainPage.AdminPageController;
import MainPage.MainPageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {
    
    @FXML
    private Label welcomeMessage, outcomeMessage, forgotEmailLabel;

    @FXML
    private Button login, forgotPassword;

    @FXML
    private TextField username, forgotEmail;

    @FXML
    private PasswordField password;


    private Stage stage;
    private Scene scene;
    private Parent root;
    File staffFile = new File("C:\\Users\\2702b\\OneDrive - Asia Pacific University\\Diploma\\Semester 5\\Java Programming\\Assignment\\ResortBookingSystem\\src\\Text Files\\Staff.json");

    /**
     * Function Name: readStaffFile <p>
     * Inside the function: <p>
     *  1. Create a Gson object <p>
     *  2. Create a FileReader object <p>
     *  3. Read the contents of the Staff file and save it as a Staff array <p>
     *  4. Return the Staff Array <p>
     * 
     * @param file
     * @return Staff[]
     * @throws FileNotFoundException
     * 
     */
    public static Staff[] readStaffFile(File file) throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader(file);
        Staff[] staffList = gson.fromJson(reader, Staff[].class);
        return staffList;
    }

    /**
     * Function name: login <p>
     * Inside the function:  <p>
     *  1. Get the text stored in the username and password fields <p>
     *  2. Get the staff list from the Staff.txt file <p>
     *  3. Loop through each staff and check if their username matches the username provided by the user <p>
     *  4. If username matches, checks the password using the same method. <p>
     *  5. If both username and password matches, returns true <p>
     *  6. If username matches but not password and vice versa, sets one of the label to notify the user that one of the fields are wrong. <p>
     *  7. If none of the username matches, sets one of the label to "Staff does not exists" message <p>
     * 
     * @param e
     * @return boolean -- true if login successful otherwise false
     * 
     */
    public void login(ActionEvent e) throws Exception {
        String usernameInput = username.getText();
        String passwordInput = password.getText();

        Staff[] staffList = readStaffFile(staffFile);
        for (Staff staff: staffList) {
            if (staff == null) {
                continue;
            }
            
            if (staff.getEmail().equals(usernameInput)) {
                if (staff.getPassword().equals(passwordInput)) {
                    // Switch to new scene
                    if (staff.isAdminBool()) { // Is an Admin
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage/AdminPage.fxml"));
                        root = loader.load();
                        AdminPageController mainPageController = loader.getController();
                        mainPageController.displayWelcomeMessage(staff.getName());

                        stage =  (Stage)((Node) e.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } else { // Not an Admin
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage/MainPage.fxml"));
                        root = loader.load();
                        MainPageController mainPageController = loader.getController();
                        mainPageController.displayWelcomeMessage(staff.getName());
    
                        stage =  (Stage)((Node) e.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                } else {
                    outcomeMessage.setTextFill(Color.RED);
                    outcomeMessage.setText("Email or password is wrong!");
                    break;
                }
            } else {
                outcomeMessage.setTextFill(Color.RED);
                outcomeMessage.setText("Staff does not exist!");
            }
        }
    }

    /**
     * Function Name: forgotPassword <p>
     * Inside the function: <p>
     *  1. Switches window to the Forgot Password window stored in the FXML file <p>
     * 
     * @param e
     * @throws IOException
     * 
     */
    public void forgotPassword(ActionEvent e) throws IOException {
        // Switching to the Forgot Password page
        Parent root = FXMLLoader.load(getClass().getResource("ForgotPassword.fxml"));
        stage =  (Stage)((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Function Name: requestPassword <p>
     * Inside the function: <p>
     *  1. Read the contents of the Staff file and store it as a Staff Array <p>
     *  2. If the staff email that is provided in the TextField exists inside the Array <p>
     *  3. Set the label to green stating that the request has been sent <p>
     *  4. If the staff email does not exist, notify the user that a request has been sent to the admin <p>
     * 
     * @param e
     * @throws IOException
     * 
     */
    public void requestPassword(ActionEvent e) throws IOException {
        String email = forgotEmail.getText();

        Staff[] staffList = readStaffFile(staffFile);
        for (Staff staff: staffList) {
            if (staff == null) {
                continue;
            }

            if (staff.getEmail().equals(email)) {
                forgotEmailLabel.setTextFill(Color.GREEN);
                forgotEmailLabel.setText("Request Sent!");
                break;
            } else {
                forgotEmailLabel.setTextFill(Color.RED);
                forgotEmailLabel.setText("This email does not exist in our staff list. Please wait for an email from the management team before further attempts.");
            }
        }
    }
}
