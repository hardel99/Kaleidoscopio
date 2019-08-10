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
    Button saveBtn, lim, pointCounter, cl, cf, info;
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

        saveBtn = findViewById(R.id.saveBtn);
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

        saveBtn.setOnClickListener(this);
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
        Animation anni= AnimationUtils.loadAnimation(this,R.anim.info_anim);
        anni.reset();
        RelativeLayout rel= (RelativeLayout) findViewById(R.id.rele);
        rel.clearAnimation();
        rel.startAnimation(anni);
    }

    private void dialogo(){
        final Dialog dia=new Dialog(this);
        LayoutInflater lin= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View sik=lin.inflate(R.layout.point_config, (ViewGroup) findViewById(R.id.relative_main_layout));
        dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dia.setContentView(sik);
        dia.show();

        final Button canc = (Button) sik.findViewById(R.id.cancel_btn);
        Button ok = (Button) sik.findViewById(R.id.ok_btn);
        SeekBar sb= (SeekBar) sik.findViewById(R.id.seek_bar);
        final TextView h= (TextView) sik.findViewById(R.id.point_number);

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

    private void colorPicker(final Button btn){
        final Dialog colorPickerDialog = new Dialog(this);
        LayoutInflater li= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        assert li != null;
        View customView = li.inflate(R.layout.color_picker, (ViewGroup) findViewById(R.id.main_layout));

        colorPickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        colorPickerDialog.setContentView(customView);
        colorPickerDialog.show();

        final Button redBtn = customView.findViewById(R.id.red);
        final Button blueBtn = customView.findViewById(R.id.blue);
        final Button greenBtn = customView.findViewById(R.id.green);
        final Button yellowBtn = customView.findViewById(R.id.yellow);
        final Button interfazBtn = customView.findViewById(R.id.interfaz);
        final Button brownBtn = customView.findViewById(R.id.brown);
        final Button whiteBtn = customView.findViewById(R.id.white);
        final Button grayBtn = customView.findViewById(R.id.gray);
        final Button blackBtn = customView.findViewById(R.id.black);
        final Button cancelBtn = customView.findViewById(R.id.cancel_btn);

        View.OnClickListener ocl=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == cancelBtn){
                    colorPickerDialog.cancel();
                }else{
                    if (v == redBtn) {
                        if (btn == cl) {
                            pw.setBackgroundColor("ROJO");
                            kus.savePreferences("ColorPat","ROJO");
                        } else{
                            als("ROJO");
                        }
                    } else if (v == blueBtn) {
                        if(btn == cl){
                            pw.setBackgroundColor("AZUL");
                            kus.savePreferences("ColorPat","AZUL");
                        } else{
                            als("AZUL");
                        }
                    } else if(v == greenBtn) {
                        if(btn == cl) {
                            pw.setBackgroundColor("VERDE");
                            kus.savePreferences("ColorPat","VERDE");
                        } else{
                            als("VERDE");
                        }
                    } else if(v == yellowBtn) {
                        if(btn == cl) {
                            pw.setBackgroundColor("PASTEL");
                            kus.savePreferences("ColorPat","PASTEL");
                        } else{
                            als("PASTEL");
                        }
                    }else if(v == interfazBtn) {
                        if(btn == cl) {
                            pw.setBackgroundColor("INTERFAZ");
                            kus.savePreferences("ColorPat","INTERFAZ");
                        } else{
                            als("INTERFAZ");
                        }
                    }else if(v == brownBtn) {
                        if(btn == cl) {
                            pw.setBackgroundColor("CAFE");
                            kus.savePreferences("ColorPat","CAFE");
                        } else{
                            als("CAFE");
                        }
                    } else if(v == whiteBtn) {
                        if(btn == cl) {
                            pw.setBackgroundColor("BLANCO");
                            kus.savePreferences("ColorPat","BLANCO");
                        } else{
                            als("BLANCO");
                        }
                    } else if(v == grayBtn) {
                        if(btn == cl) {
                            pw.setBackgroundColor("GRIS");
                            kus.savePreferences("ColorPat","GRIS");
                        } else{
                            als("GRIS");
                        }
                    } else if(v == blackBtn) {
                        if(btn == cl) {
                            pw.setBackgroundColor("NEGRO");
                            kus.savePreferences("ColorPat","NEGRO");
                        } else{
                            als("NEGRO");
                        }
                    }

                    colorPickerDialog.cancel();
                }
            }
        };

        redBtn.setOnClickListener(ocl);
        blueBtn.setOnClickListener(ocl);
        greenBtn.setOnClickListener(ocl);
        yellowBtn.setOnClickListener(ocl);
        interfazBtn.setOnClickListener(ocl);
        brownBtn.setOnClickListener(ocl);
        whiteBtn.setOnClickListener(ocl);
        grayBtn.setOnClickListener(ocl);
        blackBtn.setOnClickListener(ocl);
        cancelBtn.setOnClickListener(ocl);

        colorPickerDialog.show();
    }

    private void als(final String color){
        final AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setTitle(null);
        ad.setMessage("Si cambias de color de fondo todo tu dibujo se perdera, ¿quieres continuar?");

        final Context c = this;
        ad.setButton(AlertDialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                pw.setForegroundColor(color, c);
                kus.savePreferences("ColorEsp", color);
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
        //cant use switch cause my buttons aren't final

        if(v == saveBtn){
            pw.saveFile(getApplicationContext());    //Save the file
        }else if(v == lim){
            pw.clean();                             //Clean the screen
            pw.setForegroundColor(kus.getCustomes("ColorEsp"),this);
            pw.setBackgroundColor(kus.getCustomes("ColorPat"));
        }else if(v == pointCounter){
            dialogo();                              //Change number of points
        }else if(v == cl){
            colorPicker(cl);                        //Foreground color
        }else if(v == cf){
            colorPicker(cf);                        //Background color
        }else if(v == info){
            Intent it=new Intent(this, InfoActivity.class);    //Info View
            startActivity(it);
        }
    }
}
