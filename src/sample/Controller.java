package sample;



import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.InformationGatherer.GetInfo;
import sample.WinRegistry.WinRegistry;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;


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

    private String pubKeyFile = "C:\\Users\\galey\\Desktop\\pubKeys\\pubKey.pub";
    private int  numOfTriesWithIncorrectPassword = 0;
    private static File dbUsers = new File("dbusers.txt");
    private static File bannedUsers = new File("banUser.txt");
    private static File restrictedUsers = new File("restrUsеr.txt");
    private static String user = "";
    private static String pass = "";

    public static String getUser() {
        return user;
    }

    public static File getDbUsers() {
        return dbUsers;
    }

    public static String getPass() {
        return pass;
    }

    public static File getBannedUsers() {
        return bannedUsers;
    }

    public static File getRestrictedUsers() {
        return restrictedUsers;
    }

    @FXML
    void initialize() {

        GetInfo.getSystemInfo();
        String computerName = GetInfo.getComputerName();
        String currentUser = GetInfo.getCurrentUser();
        String winDir = GetInfo.getWindowsDirectory();
        String system32Dir = GetInfo.getSystem32Directory();
        int screnHeight = GetInfo.getScrenHeight();
        String keyboardInfo = GetInfo.searchXMLByAttribute("Sys.xml");
        String keyboardType = GetInfo.returnKeyboardType(keyboardInfo);

        String systemInfo = GetInfo.appendAllInfo(computerName,
                currentUser,
                winDir,
                system32Dir,
                keyboardType,
                screnHeight,
                System.getProperty("user.dir"));
        String hash = hashing(systemInfo);
        try {
            String signature = WinRegistry.readString(
                    WinRegistry.HKEY_CURRENT_USER,
                    "SOFTWARE\\Kosse",
                    "Signature");
            PublicKey pubkey = loadPublicKeyFromFile(pubKeyFile);
            if(!verify(hash,signature,pubkey))
            {
                alert(null,"Pirated verion");
                Platform.exit();
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        loginBtn.setOnAction(event ->
        {

            String username = usernameField.getText().trim();
//            String username ="user";
            System.out.println(username);
            String password = passwordField.getText().trim();
            user(username);
            oldPass(password);
            int authKey = login(username, password, dbUsers);
            System.out.println(authKey);

            if(authKey == 0)
            {
                numOfTriesWithIncorrectPassword++;
                if(numOfTriesWithIncorrectPassword == 3)
                {
                    Platform.exit();
                }
                alert(null, "Incorrect Password. Please try again.");

            }
            else if(authKey == 1)
            {
                alert(null, "No such user. Please concat admin.");
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
                if(adminHomeController.checkIfUserIsRestrictedOrBanned(username,Controller.getBannedUsers()))
                {
                    alert(null,"You are banned, please contact admin for more details");
                }
                else
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
                if(user[1].equals("null"))
                    user[1] = "";
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

        return authKey;

    }

    public static Stage load(FXMLLoader loader) {
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
        return stage;
    }

    public static void alert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void user(String username) {
        user = username;

    }

    public static void oldPass(String password)
    {
        pass = password;

    }

    public String hashing(String text) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(text.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
            System.out.println(hashtext);
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PublicKey loadPublicKeyFromFile(String filePath)
    {
        Path path = Paths.get(filePath);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(path);
            X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(ks);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes("UTF-8"));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }
}
