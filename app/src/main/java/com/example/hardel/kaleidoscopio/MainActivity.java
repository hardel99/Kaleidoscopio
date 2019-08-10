package com.example.hardel.kaleidoscopio;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button wa, lim, pointCounter, cl, cf, info;
    PaintView pw;
    int numbef,number;
    private CustomeSettings kus=new CustomeSettings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        estail();

        wa = findViewById(R.id.saveBtn);
        lim = findViewById(R.id.clin);
        pointCounter = findViewById(R.id.puntos);
        cl = findViewById(R.id.camlin);
        cf = findViewById(R.id.camfon);
        info = findViewById(R.id.inf);

        pw = (PaintView) findViewById(R.id.pw);
        pw.setPoints(Integer.parseInt(kus.getCustomes("Puntos")));
        pw.setBackgroundColor(kus.getCustomes("ColorPat"));
        pw.setForegroundColor(kus.getCustomes("ColorEsp"),this);

        number = pw.getPoints();

        pointCounter.setText(String.valueOf(number));

        wa.setOnClickListener(this);
        lim.setOnClickListener(this);
        pointCounter.setOnClickListener(this);
        cl.setOnClickListener(this);
        cf.setOnClickListener(this);
        info.setOnClickListener(this);


        /*final float toleran = 4;
        pw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x=event.getX();
                float y=event.getY();

                if(event.getAction()==event.ACTION_MOVE){
                    boolean tx=x+toleran>event.getX() & x-toleran<event.getX();
                    boolean ty=y+toleran>event.getY() & y-toleran<event.getY();
                    if(tx & ty){
                        if(!hai){
                            ll.setVisibility(View.VISIBLE);
                            hai=true;
                        }else{
                            ll.setVisibility(View.GONE);
                            hai=false;
                        }
                    }
                }

                return false;
            }
        });*/
    }

    private void estail(){
        Animation anni= AnimationUtils.loadAnimation(this,R.anim.magic2);
        anni.reset();
        RelativeLayout rel= (RelativeLayout) findViewById(R.id.rele);
        rel.clearAnimation();
        rel.startAnimation(anni);
    }

    private void dialogo(){
        final Dialog dia=new Dialog(this);
        LayoutInflater lin= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View sik=lin.inflate(R.layout.nump, (ViewGroup) findViewById(R.id.skb));
        dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dia.setContentView(sik);
        dia.show();

        final Button canc = (Button) sik.findViewById(R.id.cancel);
        Button ok = (Button) sik.findViewById(R.id.ocai);
        SeekBar sb= (SeekBar) sik.findViewById(R.id.sick);
        final TextView h= (TextView) sik.findViewById(R.id.cuanto);

        sb.setProgress(number);
        h.setText(String.valueOf(number));
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numbef =progress;
                if(numbef<2){
                    numbef=2;
                }
                h.setText(String.valueOf(numbef));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        View.OnClickListener ock=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==canc){
                    dia.cancel();
                }else{
                    number=numbef;
                    kus.savePreferences("Puntos",""+number);
                    pw.setPoints(number);
                    pointCounter.setText(String.valueOf(number));
                    dia.cancel();
                }
            }
        };

        canc.setOnClickListener(ock);
        ok.setOnClickListener(ock);
    }

    private void cP(final Button but){
        final Dialog cp=new Dialog(this);
        LayoutInflater li= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View rue=li.inflate(R.layout.colorpi, (ViewGroup) findViewById(R.id.princpl));
        cp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cp.setContentView(rue);
        cp.show();

        final Button rojo = (Button) rue.findViewById(R.id.red);
        final Button azz = (Button) rue.findViewById(R.id.blu);
        final Button gd = (Button) rue.findViewById(R.id.green);
        final Button mellow = (Button) rue.findViewById(R.id.yellow);
        final Button interfaz = (Button) rue.findViewById(R.id.interfaz);
        final Button kfe = (Button) rue.findViewById(R.id.kafka);
        final Button wait = (Button) rue.findViewById(R.id.whit);
        final Button grey = (Button) rue.findViewById(R.id.gray);
        final Button blk = (Button) rue.findViewById(R.id.bla);
        final Button can = (Button) rue.findViewById(R.id.cancer);

        View.OnClickListener ocl=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==can){
                    cp.cancel();
                }else{
                    if(v==rojo){
                        if(but==cl){
                            pw.setBackgroundColor("ROJO");
                            kus.savePreferences("ColorPat","ROJO");
                        }else{
                            als("ROJO");
                        }
                    }else if(v==azz){
                        if(but==cl){
                            pw.setBackgroundColor("AZUL");
                            kus.savePreferences("ColorPat","AZUL");
                        }else{
                            als("AZUL");
                        }
                    }else if(v==gd){
                        if(but==cl){
                            pw.setBackgroundColor("VERDE");
                            kus.savePreferences("ColorPat","VERDE");
                        }else{
                            als("VERDE");
                        }
                    }else if(v==mellow){
                        if(but==cl){
                            pw.setBackgroundColor("PASTEL");
                            kus.savePreferences("ColorPat","PASTEL");
                        }else{
                            als("PASTEL");
                        }
                    }else if(v==interfaz){
                        if(but==cl){
                            pw.setBackgroundColor("INTERFAZ");
                            kus.savePreferences("ColorPat","INTERFAZ");
                        }else{
                            als("INTERFAZ");
                        }
                    }else if(v==kfe){
                        if(but==cl){
                            pw.setBackgroundColor("CAFE");
                            kus.savePreferences("ColorPat","CAFE");
                        }else{
                            als("CAFE");
                        }
                    }else if(v==wait){
                        if(but==cl){
                            pw.setBackgroundColor("BLANCO");
                            kus.savePreferences("ColorPat","BLANCO");
                        }else{
                            als("BLANCO");
                        }
                    }else if(v==grey){
                        if(but==cl){
                            pw.setBackgroundColor("GRIS");
                            kus.savePreferences("ColorPat","GRIS");
                        }else{
                            als("GRIS");
                        }
                    }else if(v==blk){
                        if(but==cl){
                            pw.setBackgroundColor("NEGRO");
                            kus.savePreferences("ColorPat","NEGRO");
                        }else{
                            als("NEGRO");
                        }
                    }
                    cp.cancel();
                }
            }
        };

        rojo.setOnClickListener(ocl);
        azz.setOnClickListener(ocl);
        gd.setOnClickListener(ocl);
        mellow.setOnClickListener(ocl);
        interfaz.setOnClickListener(ocl);
        kfe.setOnClickListener(ocl);
        wait.setOnClickListener(ocl);
        grey.setOnClickListener(ocl);
        blk.setOnClickListener(ocl);
        can.setOnClickListener(ocl);

        cp.show();
    }

    private void als(final String color){
        final AlertDialog ad=new AlertDialog.Builder(this).create();
        ad.setTitle(null);
        ad.setMessage("Si cambias de color de fondo todo tu dibujo se perdera, ¿quieres continuar?");
        final Context c=this;
        ad.setButton(AlertDialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                pw.setForegroundColor(color,c);
                kus.savePreferences("ColorEsp",color);
                ad.dismiss();
            }

        });

        ad.setButton(AlertDialog.BUTTON_NEGATIVE,"Cancelar",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ad.dismiss();
            }

        });
        ad.show();
    }

    @Override
    public void onClick(View v) {
        if(v==wa){
            pw.saveFile(getApplicationContext());    //Guardar la imagen
        }else if(v==lim){        //Limpiar la pantalla
            pw.clean();
            pw.setForegroundColor(kus.getCustomes("ColorEsp"),this);
            pw.setBackgroundColor(kus.getCustomes("ColorPat"));
        }else if(v== pointCounter){
            dialogo();  //Cambiar numero de puntos
        }else if(v==cl){
            cP(cl);     //Color de linea
        }else if(v==cf){
            cP(cf);     //Color de fondo
        }else if(v==info){
            Intent it=new Intent(this,constr.class);    //Pantalla de Información
            startActivity(it);
        }
    }
}
