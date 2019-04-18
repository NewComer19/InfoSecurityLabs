package sample;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        File dbUsers = new File("C:\\Users\\galey\\IdeaProjects\\SecLab1\\dbusers.txt");

        try {
            String username = "user3";
            String password = "user";
            if(!dbUsers.exists())
                dbUsers.createNewFile();
            else{
                if(password == "")
                {
                    password = "null";
                }
//                    System.out.println(loginUser(username,password,dbUsers));
            }
//            System.out.println(returnAllUsers(dbUsers));
            User.returnAllUsers(dbUsers);
//            changePassWithVeryfication(username,password,"user3",dbUsers);
//                addUser(username,password,dbUsers);
//            String users = returnAllUsers(dbUsers);
//            System.out.println(users.contains(username));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String username, String password, File file){
        try
        {
            Writer write;
            write = new BufferedWriter(new FileWriter(file,true));
            if(password.equals(""))
            {
                password = null;
            }
            String text = username + ":" + password + ";";
            write.append(text);
            write.close();
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

    public static void changePassWithVeryfication(String user, String oldPass, String newPass, File file) {
        BufferedReader br = null;
        String oldContent = "";
        StringBuilder newContent = new StringBuilder("");
        try {

            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {

                oldContent += line + System.lineSeparator();


            }
//            System.out.println(oldContent);
            if (oldContent.contains(user + ":" + oldPass + ";")) {
                String[] tmp = oldContent.split(";");

//                newContent = oldContent.replace(oldPass,newPass);
//                System.out.println();
                for (int i = 0; i < tmp.length - 1; i++) {
                    String trmmiedTmp = tmp[i].trim();
                    System.out.println(trmmiedTmp);
                    if (trmmiedTmp.equals(user + ":" + oldPass)) {
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
            } else {
                Controller.alert(null, "Please make sure you have entered correct password.");
            }

        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
