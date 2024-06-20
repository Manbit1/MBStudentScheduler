package com.mbsstudentscheduler;

import android.preference.PreferenceDataStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLData;

public class fileManager {
    private static File file;
    private static FileWriter writer;
    private static FileReader reader;
    
    //deletes the given file, need I say more?
    public static void deleteFile(String fileName) {
        file = new File(fileName);
        file.delete();
    }
    
    //makes a new .txt file with the given name by the user then adds the message to it
    public static String writeFile(String fileName, String Message) {
        try {
            writer = new FileWriter(fileName + ".txt", true);
            writer.write(Message);
            writer.close();
            return ("message " + Message + " has been added to file " + fileName + " succesfully");
        } catch (IOException e) {
            return ("Something went wrong, please check the IO input");
        } catch (Exception e) {
            return ("something went wrong");
        }
    }
    
    //reads a given file, translates the reading from base64 to readable alphabet and numbers
    public static String readFile(String fileName) {
        String finalString = "";
        try {
            reader = new FileReader(fileName);
            int conversion = reader.read();
            while (conversion != -1) {
                finalString = finalString + (char) conversion;
                conversion = reader.read();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalString;
        
    }
    
    //checks if the file exists and returns a True if it is
    public static boolean fileChecker(String filename) {
        file = new File(filename);
        return (file.exists() && !file.isDirectory());
    }
    
    //input a path for a specific directory, it searches it file by file then delets whatever file you specify
    public static void fileCondDelete(String path, String keyword) {
        file = new File(path);
        File[] direc = file.listFiles();
        for (int i = 0; i < direc.length; i++) {
            if (direc[i].getName().contains(keyword)) {
                deleteFile(direc[i].getAbsolutePath());
            }
        }
        
    }
}
