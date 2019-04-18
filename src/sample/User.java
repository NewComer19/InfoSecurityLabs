package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.File;
import java.util.ArrayList;

public class User {
    private String username;
    private String password;

    @FXML
    private Button action;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Button getAction() {
        return action;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.action = new Button("Perform Action");
    }

    public static ArrayList<User> returnAllUsers(File db) {
        ArrayList<User> users = new ArrayList<>();
        String[] textUsers = Test.returnAllUsers(db).split(";");

        for (int i = 0; i < textUsers.length; i++) {
            String[] separatedUser = textUsers[i].split(":");
            users.add(new User(separatedUser[0], separatedUser[1]));
        }

        return users;
    }
}
