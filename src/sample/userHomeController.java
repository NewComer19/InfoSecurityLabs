package sample;

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
            String username = oldPassTxt.getText().trim();
            String password = newPassTxt.getText().trim();


        });
    }
}
