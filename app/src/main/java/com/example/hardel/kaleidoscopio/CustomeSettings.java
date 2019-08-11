package com.example.hardel.kaleidoscopio;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

class CustomeSettings {

    private Properties prop = new Properties();
    private String path;

    private void commitChanges(){
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Kaleidoscopio/conf";
        File f = new File(path);

        if(!f.exists()){
            f.mkdirs();

            prop.setProperty("Puntos", "3");
            prop.setProperty("ColorPat", "ROJO");
            prop.setProperty("ColorEsp", "NEGRO");

            try {
                FileOutputStream fos = new FileOutputStream(path + "/customises.interfaz");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    void savePreferences(String id, String change){
        commitChanges();

        try {
            prop.setProperty(id, change);
            prop.store(new FileOutputStream(path + "/customises.interfaz"),null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getCustomes(String id){
        commitChanges();

        try {
            FileInputStream fis = new FileInputStream(path +"/customises.interfaz");
            prop.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop.getProperty(id);
    }
}
