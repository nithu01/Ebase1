package ebase.hkgrox.com.ebase.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by root on 19/8/16.
 */
public class FileReaderWriter {



    static final String filenameUser="user";
    static final String filenameLaf="laf";
    static final String filenameBusiness="business";
    static final String filenameApp="app";
    private static final String JUST_CHILL ="1234567894561231" ;

//read file from application path

    public static synchronized String readFile(Context context, String filename) {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = null;
        try {
            File file = new File(context.getFilesDir(), filename);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                reader = new BufferedReader(inputStreamReader);
                String currentLine = "";
                while ((currentLine = reader.readLine()) != null) {
                    fileData.append(currentLine);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                return "ERROR";
            }

        }
        String data = fileData.toString();
        if (data != null && data.length() == 0) {
            return "ERROR";
        } else if (data == null) {
            return "ERROR";
        } else {
            return data;
        }
    }





// To read file from Assests
    public static synchronized String readFileFromAssets(Context context, String filename) {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = null;
        try {
            AssetManager am = context.getAssets();
            InputStream inputStream = am.open(filename + ".txt");
            File file = createFileFromInputStream(context, inputStream, filename);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            reader = new BufferedReader(inputStreamReader);
            String currentLine = "";
            while ((currentLine = reader.readLine()) != null) {
                fileData.append(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                return "ERROR";
            }
        }
        String data = fileData.toString();
        if (data != null && data.length() == 0) {
            return "ERROR";
        } else if (data == null) {
            return "ERROR";
        } else {
            return data;
        }
    }
//create file in Application package
    private static synchronized File createFileFromInputStream(Context context, InputStream inputStream, String fileName) {

        try {
            File f = new File(context.getFilesDir(), fileName);
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
            return f;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Writes the given data to a file. Returns false if an error occurs while
     * writing the file.
     *
     * @return boolean
     */
// wrtie file in application page
    public static synchronized boolean writeToFile(Context context, String data, String filename) {
        FileOutputStream outputStream = null;
        try {
            File file = new File(context.getFilesDir(), filename);
            outputStream = new FileOutputStream(file);
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] contentInBytes = data.getBytes();
            outputStream.write(contentInBytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }





















    public static synchronized boolean deleteAllObjectFromFile(Context context) {
        ArrayList<String> objectFileNameList = getAllObjectFileName();

        for (String s:objectFileNameList) {
            File file = new File(context.getFilesDir(), s);
            if(file.exists()){
                file.delete();
            }
        }
        return true;
    }

    private static ArrayList<String> getAllObjectFileName() {
        ArrayList<String> strings = new ArrayList<>();
        strings .add(filenameApp);
        strings .add(filenameBusiness);
        strings .add(filenameLaf);
        strings .add(filenameUser);
        return  strings;
    }


}
