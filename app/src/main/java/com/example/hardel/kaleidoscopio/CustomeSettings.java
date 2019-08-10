package com.example.hardel.kaleidoscopio;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class CustomeSettings {

    private Properties cipemen = new Properties();
    private FileOutputStream foster;
    String ruta;

    private void vah_ruta(){
        ruta= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Kaleidoscopio/conf";
        File f=new File(ruta);
        if(!f.exists()){
            f.mkdirs();
            cipemen.setProperty("Puntos","3");
            cipemen.setProperty("ColorPat","ROJO");
            cipemen.setProperty("ColorEsp","NEGRO");
            try {
                foster=new FileOutputStream(ruta+"/customises.interfaz");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void savePreferences(String id, String cambio){
        vah_ruta();
        try {
            cipemen.setProperty(id,cambio);
            cipemen.store(new FileOutputStream(ruta+"/customises.interfaz"),null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCustomes(String id){
        vah_ruta();
        try {
            FileInputStream fister=new FileInputStream(ruta+"/customises.interfaz");
            cipemen.load(fister);
            fister.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cipemen.getProperty(id);
    }
}
