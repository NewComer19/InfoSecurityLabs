package sample;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        File dbUsers = new File("dbusers.txt");

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
//                addUser(username,password,dbUsers);
                checkIfUserIsInDb(username,dbUsers);


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
    public static void checkIfUserIsInDb(String username, File file)
    {
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null)
            {
               String[] newLine= line.replace(';',' ').trim().split(":");
                System.out.println(Arrays.toString(newLine));
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

}
