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
            String oldPassForNullCheck = Controller.getPass();
//            if(oldPassForNullCheck.equals(""))
//            {
//                oldPass = "null";
//            }
            System.out.println(oldPassForNullCheck);
            File db = Controller.getDbUsers();

            if(newPass.isEmpty())
            {
                Controller.alert(null,"Please enter the information in new password field.");
            }
            if(adminHomeController.checkIfUserIsRestrictedOrBanned(user, Controller.getRestrictedUsers()))
            {
                if(checkIfNewPassHaveRestrictedSymbols(newPass))
                {
                    Controller.alert(null,"You have restrictions on your passwords.Please enter " +
                            "password without latin, cyrillic and special symbols");
                }
            }
            else if(oldPass.isEmpty() && !oldPassForNullCheck.equals(""))
            {
                Controller.alert(null,"Please enter the information in old password field.");
            }
            else if (!oldPassForNullCheck.equals(oldPass))
            {
                Controller.alert(null,"Please make sure you entered correct old password.");

            }
            else if(newPass.equals(oldPass))
            {
                Controller.alert(null,"Your old and new password are equal, please make sure you've entered correct new password");
            }
            else if(oldPassForNullCheck.equals(oldPass))
            {
                Controller.alert(null,"Your password has successfully been changed.");
                Test.changePassWithVeryfication(user,oldPass,newPass,db);
                changeBtn.getScene().getWindow().hide();
            }
        });

        exitBtn.setOnAction(even ->
        {
            Platform.exit();
        });
    }

    public boolean checkIfNewPassHaveRestrictedSymbols(String newpass)
    {
        boolean result = isCyrillic(newpass) || isLatin(newpass) || isSpecialSymbol(newpass);
        System.out.println("Cyrillic"+" " + isCyrillic(newpass));
        System.out.println("Latin"+" " + isLatin(newpass));
        System.out.println("Special"+" " + isSpecialSymbol(newpass));
        return  result;

    }

    public boolean isCyrillic(String str)
    {
        boolean result = false;
        for (char a : str.toCharArray()) {
            if (Character.UnicodeBlock.of(a) == Character.UnicodeBlock.CYRILLIC) {
                result = !result;
                break;
            }
        }
        return result;
    }

    public boolean isLatin(String str)
    {
        boolean result = str.matches("[A-Za-z]++");

        return result;
    }

    public boolean isSpecialSymbol(String str)
    {
        char[] specialSymbols = new char[]{'.',',',':',';','\'','\"','[',']','{','}','(',')','-','!','/'};
        for (Character c: str.toCharArray()) {
            for (Character b:specialSymbols) {
                if(c.equals(b))
                    return true;
            }
        }
        return false;

    }

}
