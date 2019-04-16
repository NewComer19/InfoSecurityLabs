package sample;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class userHomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField oldPassTxt;

    @FXML
    private PasswordField newPassTxt;

    @FXML
    private Button changeBtn;

    @FXML
    private Button exitBtn;

    @FXML
    void initialize() {

        changeBtn.setOnAction(event ->
        {
            String oldPass = oldPassTxt.getText().trim();
            String newPass = newPassTxt.getText().trim();
            String user = Controller.getUser();
            File db = Controller.getDbUsers();
            if(oldPass.isEmpty() || newPass.isEmpty())
            {
                Controller.alert(null,"Please enter the information in proper fields.");
            }
            else
            {
                Controller.alert(null,"Your password has successfully been changed.");
                Test.changePassWithVeryfication(user,oldPass,newPass,db);
            }
        });

        exitBtn.setOnAction(even ->
        {
            Platform.exit();
        });
    }
}
