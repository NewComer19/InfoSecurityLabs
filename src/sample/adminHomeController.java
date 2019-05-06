package sample;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class adminHomeController {
    private ObservableList<User> usersData = FXCollections.observableArrayList(User.returnAllUsers(Controller.getDbUsers()));
//    private ObservableList<User> usersData = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addUserBtn;

    @FXML
    private Button changePassBtn;

    @FXML
    private TextField addUserTxt;

    @FXML
    private Button updateBtn;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, String> username;

    @FXML
    private TableColumn<User, String> password;

    @FXML
    private TableColumn<User, String> banCol;

    @FXML
    private TableColumn<User, String> restrCol;


    public void initialize() {

        usersData.remove(0);
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        banCol.setCellValueFactory(new PropertyValueFactory<>("isBanned"));
        restrCol.setCellValueFactory(new PropertyValueFactory<>("isRestricted"));
        table.setItems(usersData);
//        table.getColumns().addAll(userCol, passCol, actionCol);
        addUserBtn.setOnAction(event->addButtonClicked());
        changePassBtn.setOnAction(event -> changePassBtnClicked());
        updateBtn.setOnAction(event -> updateBtnClicked());
    }

    public void addButtonClicked()
    {
        User newUser = new User(addUserTxt.getText(),"");
//        System.out.println(User.returnAllUsers(Controller.getDbUsers()).toString());
        Test.addUser(newUser);
        boolean isUserUnique = Test.checkIfTheUsernameIsUnique(newUser, Controller.getDbUsers());
        if(!isUserUnique)
        {
            table.getItems().add(newUser);
        }


    }
    public void changePassBtnClicked()
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/userHomeScreen.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    public void updateBtnClicked()
    {
        table.getItems().forEach(this::iterateThroughTable);
        Controller.alert(null,"Selected data updated");

    }

    public void iterateThroughTable(User user)
    {
        if(user.getIsRestricted().isSelected())
        {
            if(!checkIfUserIsRestrictedOrBanned(user.getUsername(),Controller.getRestrictedUsers()))
            {
                Test.addUserToLists(user,Controller.getRestrictedUsers());
            }
        }
        if(user.getIsBanned().isSelected())
        {
            System.out.println(user.getIsBanned().isSelected());
            if(!checkIfUserIsRestrictedOrBanned(user.getUsername(),Controller.getBannedUsers()))
            {
                Test.addUserToLists(user,Controller.getBannedUsers());
            }
        }
        if(!user.getIsRestricted().isSelected() && checkIfUserIsRestrictedOrBanned(user.getUsername(),Controller.getRestrictedUsers()))
        {
            deleteUserFromList(user,Controller.getRestrictedUsers());
        }
        if(!user.getIsBanned().isSelected() && checkIfUserIsRestrictedOrBanned(user.getUsername(),Controller.getBannedUsers()))
        {
            deleteUserFromList(user,Controller.getBannedUsers());
        }
    }

    public static boolean checkIfUserIsRestrictedOrBanned(String username, File db)
    {
        String allRestrictedOrBannedUsers = Test.returnAllUsers(db);
        if(allRestrictedOrBannedUsers.contains(username))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void deleteUserFromList(User usr, File db)
    {
        BufferedReader br=null;
        String oldContent = "";
        StringBuilder newContent = new StringBuilder("");
        try
        {
            br = new BufferedReader(new FileReader(db));
            String line = "";
            while((line = br.readLine()) != null)
            {

                oldContent+=line  + System.lineSeparator();
            }
            newContent.append(oldContent.replace(usr.getUsername() + ";", ""));
            String newFile = newContent.toString();
            FileOutputStream fileOut = new FileOutputStream(db);
            fileOut.write(newFile.getBytes());
            fileOut.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }



}
