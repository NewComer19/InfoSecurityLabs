package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.omg.CORBA.Any;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    private int  numOfTriesWithIncorrectPassword = 0;
    private File dbUsers = new File("dbusers.txt");
    private BufferedReader br = null;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private static String adminPass = "null";

    @FXML
    void initialize() {
        alert.setTitle("Error");
        loginBtn.setOnAction(event ->
        {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            int authKey = login(username, password, dbUsers, br);
            if(authKey == 0)
            {
                numOfTriesWithIncorrectPassword++;
                if(numOfTriesWithIncorrectPassword == 3)
                {
                    Platform.exit();
                }

                alert.setHeaderText(null);
                alert.setContentText("Incorrect Password. Please try again.");
                alert.showAndWait();

            }
            else if(authKey == 1)
            {
                alert.setHeaderText(null);
                alert.setContentText("No such user. Please concat admin.");
                alert.showAndWait();
            }
            else if (authKey == 2)
            {
                System.out.println(username + " " + password);
                loginBtn.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/adminHomeScreen.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                load(loader);
            }
            else if(authKey == 3)
            {
                loginBtn.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/userHomeScreen.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                load(loader);
            }


        });

    }


    static int login(String username, String password, File file, BufferedReader br) {
        int authKey = 4;
        try
        {
            br = new BufferedReader(new FileReader(file));
            ArrayList<String[]> al = new ArrayList<>();
            String line;
            String users="";
            while((line = br.readLine()) != null) {
                String[] newLine = line.replace(';', ' ').trim().split(":");
                users+=newLine + ",";
                System.out.println(!newLine[1].equals(password));
                if(newLine[0].equals(username) && !newLine[1].equals(password))
                {
                    System.out.println("Found it");
                    authKey= 0;
                }
                else if(newLine[0].equals(username) && newLine[1].equals(password) && newLine[0].equals("ADMIN") && newLine[1].equals(adminPass))
                {
                    authKey= 2;

                }
                else if(newLine[0].equals(username) && newLine[1].equals(password))
                {
                    authKey= 3;
                }


                else
                    System.out.println("Wtf");
                System.out.println(Arrays.toString(newLine));
            }
            String[] usersInArray = users.split(",");
            for(String user: usersInArray)
            {
                if(!user.equals(username))
                {
                    authKey= 1;
                }
                else
                    authKey= 0;
            }
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
        finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("AuthKey " +authKey );
        return authKey;

    }

    public static void load(FXMLLoader loader)
    {
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
