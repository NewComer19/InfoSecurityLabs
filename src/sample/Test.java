package sample;

import java.io.*;
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
            changePassWithVeryfication("ADMIN","null","notnull",dbUsers);
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

    public static void changePassWithVeryfication(String user, String oldPass, String newPass, File file)
    {
        BufferedReader br=null;
        String oldContent = "";
        String newContent = "";
        try
        {

            br = new BufferedReader(new FileReader(file));
            String line = "";
            while((line = br.readLine()) != null)
            {

                oldContent+=line  + System.lineSeparator();


            }
            System.out.println(oldContent);
            if(oldContent.contains(user + ":" + oldPass + ";"))
            {
                newContent = oldContent.replace(oldPass,newPass);
            }
            FileOutputStream fileOut = new FileOutputStream(file);
            fileOut.write(newContent.getBytes());
            fileOut.close();

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
}
