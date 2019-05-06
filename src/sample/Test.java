package sample;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    private static  File allUsers = Controller.getDbUsers();
    public static void main(String[] args) {
//        File dbUsers = new File("C:\\Users\\galey\\IdeaProjects\\SecLab1\\dbusers.txt");

        try {

            if(!allUsers.exists())
                allUsers.createNewFile();

//            System.out.println(returnAllUsers(dbUsers));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User user){
        try
        {
            String password = user.getPassword();
            String username = user.getUsername();

            Writer write;
            write = new BufferedWriter(new FileWriter(allUsers,true));
            if(password.equals(""))
            {
                password = null;
            }
            String text = username + ":" + password + ";";
            if(checkIfTheUsernameIsUnique(user,allUsers))
            {
                Controller.alert(null,"User with such username already exists.");
            }
            else
            {
                write.append(text);
                write.close();
            }


        }
        catch(IOException  e)
        {
            System.err.println(e);
        }

    }

    public static String returnAllUsers(File file)
    {
        BufferedReader br = null;
        String text = "";
        try
        {

            br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null)
            {
                text += line;
               String[] newLine= line.replace(';',' ').trim().split(":");

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
        return text;
    }

    public static void changePassWithVeryfication(String user, String oldPass, String newPass, File file)
    {
        BufferedReader br=null;
        if(oldPass.equals(""))
            oldPass = "null";
        String oldContent = "";
        StringBuilder newContent = new StringBuilder("");
        try
        {

            br = new BufferedReader(new FileReader(file));
            String line = "";
            while((line = br.readLine()) != null)
            {

                oldContent+=line  + System.lineSeparator();


            }
//            System.out.println(oldContent);
            if(oldContent.contains(user + ":" + oldPass + ";"))
            {
                String[] tmp = oldContent.split(";");

//                newContent = oldContent.replace(oldPass,newPass);
//                System.out.println();
                for (int i = 0; i <tmp.length-1 ; i++) {
                    String trmmiedTmp = tmp[i].trim();
                    System.out.println(trmmiedTmp);
                    if(trmmiedTmp.equals(user + ":" + oldPass))
                    {
                        trmmiedTmp = user + ":" + newPass;

                    }
                    newContent.append(trmmiedTmp).append(";");
                }
                String newFile = newContent.toString();
//                System.out.println("New content: ");
//                System.out.println( newContent.toString());
                FileOutputStream fileOut = new FileOutputStream(file);
                fileOut.write(newFile.getBytes());
                fileOut.close();
            }
            else
            {
                Controller.alert(null,"Please make sure you have entered correct password.");
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
    }

    public static ArrayList<User> returnAllUsersInArray(File db)
    {
        ArrayList<User> users = new ArrayList<>();
        String[] textUsers = returnAllUsers(db).split(";");

        for (int i = 0; i < textUsers.length; i++) {
            String[] separatedUser = textUsers[i].split(":");
            users.add(new User(separatedUser[0],separatedUser[1]));
        }


        return users;
    }

    public static boolean checkIfTheUsernameIsUnique(User usr, File db)
    {
        String username = usr.getUsername();
        String alluser = returnAllUsers(db);
        return alluser.contains(username);
    }

    public static void addUserToLists(User user, File db)
    {
        try
        {

            String username = user.getUsername();

            Writer write;
            write = new BufferedWriter(new FileWriter(db,true));
            String text = username + ";";
            if(checkIfTheUsernameIsUnique(user,db))
            {
                Controller.alert(null,"User with such username already exists.");
            }
            else
            {
                write.append(text);
                write.close();
            }


        }
        catch(IOException  e)
        {
            System.err.println(e);
        }

    }
}
