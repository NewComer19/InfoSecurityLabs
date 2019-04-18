package sample;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private Button changePswBtn;

    @FXML
    private Button addUserToBanLst;

    @FXML
    private Button addUserToRestrList;

    @FXML
    private Button addUser;


    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, String> username;

    @FXML
    private TableColumn<User, String> password;

    @FXML
    private TableColumn<User, String> action;


    public void initialize() {
        System.out.println(usersData.toString());

        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        action.setCellValueFactory(new PropertyValueFactory<>("action"));
        table.setItems(usersData);
//        table.getColumns().addAll(userCol, passCol, actionCol);

    }
}

