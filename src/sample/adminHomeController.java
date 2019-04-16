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
    private TableView<User> table = new TableView<User>();

    @FXML
    private TableColumn<User,String> userCol = new TableColumn<>("user");

    @FXML
    private TableColumn<User,String> passCol  = new TableColumn<>("password");


    public static void  initialize(Stage stage) {

//
//        StackPane root = new StackPane();
//        root.getChildren().add(table);

        stage.setTitle("TableView (o7planning.org)");

//        Scene scene = new Scene(root, 450, 300);
//        stage.setScene(scene);
//        stage.show();
//        Stage stage = new Stage();
//        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
//        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));
//        ObservableList<User> list1 = FXCollections.observableArrayList(User.returnAllUsers(Controller.getDbUsers()));
//        table.setUserData(list1);
//        table.getColumns().addAll(userCol, passCol);
//        StackPane root = new StackPane();
//        root.getChildren().add(table);
//
//        stage.setTitle("TableView (o7planning.org)");
//
//        Scene scene = new Scene(root, 450, 300);
//        stage.setScene(scene);
//        stage.show();
    }
}

