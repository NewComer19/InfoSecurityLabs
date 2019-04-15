package sample;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;


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
            int authKey = login(username, password, dbUsers);
            System.out.println(authKey);
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


    static int login(String username, String password, File file) {
        int authKey = 5;
        ArrayList<String[]> al = new ArrayList<>();
        String text = Test.returnAllUsers(file);
        if (text.contains(username)) {
            String[] users = text.split(";");
            for (int i = 0; i < users.length; i++) {
                String[] user = users[i].split(":");
                System.out.println(user[0]);
                System.out.println(user[1]);
                if (user[0].equals(username) && user[1].equals(password) && !user[0].equals("ADMIN"))
                {
                    return 3;
                } else if (user[0].equals("ADMIN") && user[1].equals(password))
                {
                    return 2;
                } else if (user[0].equals(username) && !Objects.equals(user[1], password))
                {
                    return 0;
                }
            }
        } else {
            return 4;
        }

//        try
//        {
//            br = new BufferedReader(new FileReader(file));
//
//            String line;
//
//            while((line = br.readLine()) != null) {
//                System.out.println(line);
//
//                String[] newLine = line.replace(';', ' ').trim().split(":");
//                al.add(newLine);
//                System.out.println(Arrays.toString(newLine));
//                System.out.println(!newLine[1].equals(password));
//                if(newLine[0].equals(username) && !newLine[1].equals(password))
//                {
//                    System.out.println("Found it");
//                    authKey= 0;
//                }
//                else if(newLine[0].equals(username) && newLine[1].equals(password) && newLine[0].equals("ADMIN") && newLine[1].equals(adminPass))
//                {
//                    authKey= 2;
//
//                }
//                else if(newLine[0].equals(username) && newLine[1].equals(password))
//                {
//                    authKey= 3;
//                }
//
//
//                else
//                    System.out.println("Wtf");
//                System.out.println(Arrays.toString(newLine));
//            }
//            String[] usersInArray = users.split(",");
//            for(String user: usersInArray)
//            {
//                if(!user.equals(username))
//                {
//                    authKey= 1;
//                }
//                else
//                    authKey= 0;
//            }
//        }
//        catch (IOException e)
//        {
//            System.err.println(e);
//        }
//        finally {
//            try {
//                br.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
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
