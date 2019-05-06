package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.io.File;
import java.util.ArrayList;

public class User {
    private String username;
    private String password;

    @FXML
    private CheckBox isBanned;

    @FXML
    private CheckBox isRestricted;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public CheckBox getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(CheckBox isBanned) {
        this.isBanned = isBanned;
    }

    public CheckBox getIsRestricted() {
        return isRestricted;
    }

    public void setIsRestricted(CheckBox isRestricted) {
        this.isRestricted = isRestricted;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isBanned = new CheckBox();
        checkIfUserIsBanned();
        this.isRestricted = new CheckBox();
        checkIfUserIsRestricted();
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

    public void checkIfUserIsBanned()
    {
        String allBannedUsers = Test.returnAllUsers(Controller.getBannedUsers());
        if(allBannedUsers.contains(this.username))
        {
            this.isBanned.setSelected(true);
        }

    }

    public void checkIfUserIsRestricted()
    {
        String allRestrictedUsers = Test.returnAllUsers(Controller.getRestrictedUsers());
        System.out.println(allRestrictedUsers);
        if(allRestrictedUsers.contains(this.username))
        {
            this.isRestricted.setSelected(true);
        }
    }
}
