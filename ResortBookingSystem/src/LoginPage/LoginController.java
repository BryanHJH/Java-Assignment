package LoginPage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;

import Classes.Staff;
import MainPage.MainPageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Node;

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

    // Reading contents of a file and saving it into an ArrayList

    public static Staff[] readStaffFile(File file) throws FileNotFoundException {
        // Scanner reader = new Scanner(file);
        // ArrayList<String> contents = new ArrayList<String>();

        // while (reader.hasNextLine()) {
        //     contents.add(reader.nextLine());
        // }
        // reader.close();

        // return contents;

        Gson gson = new Gson();
        Reader reader = new FileReader(file);
        Staff[] staffList = gson.fromJson(reader, Staff[].class);
        return staffList;
    }

    // Button events -- Login
    /**
     * Function name: login
     * 
     * @param e
     * @return boolean -- true if login successful otherwise false
     * 
     * Inside the function: 
     *  1. Get the text stored in the username and password fields
     *  2. Get the staff list from the Staff.txt file
     *  3. Loop through each staff and check if their username matches the username provided by the user
     *  4. If username matches, checks the password using the same method.
     *  5. If both username and password matches, returns true
     *  6. If username matches but not password and vice versa, sets one of the label to notify the user that one of the fields are wrong.
     *  7. If none of the username matches, sets one of the label to "Staff does not exists" message
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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage/MainPage.fxml"));
                    root = loader.load();
                    MainPageController mainPageController = loader.getController();
                    mainPageController.displayWelcomeMessage(staff.getName());

                    stage =  (Stage)((Node) e.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
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

    public void forgotPassword(ActionEvent e) throws IOException {
        // Switching to the Forgot Password page
        Parent root = FXMLLoader.load(getClass().getResource("ForgotPassword.fxml"));
        stage =  (Stage)((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void requestPassword(ActionEvent e) throws IOException {
        String email = forgotEmail.getText();
        // System.out.println(email);

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
                // break;
            }
        }
    }
}
