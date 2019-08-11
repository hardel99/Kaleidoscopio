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
    Button saveBtn, cleanBtn, pointCounter, lineColorBtn, backgroundColorBtn, infoBtn;
    PaintView paintView;
    int pointNumber, actualPointNumber;
    private CustomeSettings customise = new CustomeSettings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        style();

        saveBtn = findViewById(R.id.save_btn);
        cleanBtn = findViewById(R.id.clean_btn);
        pointCounter = findViewById(R.id.point_dialog_btn);
        lineColorBtn = findViewById(R.id.change_line_btn);
        backgroundColorBtn = findViewById(R.id.change_background_btn);
        infoBtn = findViewById(R.id.info_btn);

        paintView = findViewById(R.id.pw);
        paintView.setPoints(Integer.parseInt(customise.getCustomes("Puntos")));
        paintView.setBackgroundColor(customise.getCustomes("ColorPat"));
        paintView.setForegroundColor(customise.getCustomes("ColorEsp"),this);

        actualPointNumber = paintView.getPoints();

        pointCounter.setText(String.valueOf(actualPointNumber));

        saveBtn.setOnClickListener(this);
        cleanBtn.setOnClickListener(this);
        pointCounter.setOnClickListener(this);
        lineColorBtn.setOnClickListener(this);
        backgroundColorBtn.setOnClickListener(this);
        infoBtn.setOnClickListener(this);


        /*final float toleran = 4;
        paintView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float change_point_icon=event.getX();
                float y=event.getY();

                if(event.getAction()==event.ACTION_MOVE){
                    boolean tx=change_point_icon+toleran>event.getX() & change_point_icon-toleran<event.getX();
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

    private void style(){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.info_anim);
        anim.reset();

        RelativeLayout rel = findViewById(R.id.rel_lay);
        rel.clearAnimation();
        rel.startAnimation(anim);
    }

    private void displayPointCounter(){
        final Dialog dialog = new Dialog(this);
        LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        assert li != null;
        View view = li.inflate(R.layout.point_config, (ViewGroup) findViewById(R.id.relative_main_layout));

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();

        final Button cancelBtn = view.findViewById(R.id.cancel_btn);
        Button okBtn = view.findViewById(R.id.ok_btn);
        SeekBar sb = view.findViewById(R.id.seek_bar);
        final TextView pointCount = view.findViewById(R.id.point_number);

        sb.setProgress(actualPointNumber);
        pointCount.setText(String.valueOf(actualPointNumber));
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pointNumber = progress;
                if(pointNumber < 2){
                    pointNumber = 2;
                }

                pointCount.setText(String.valueOf(pointNumber));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        View.OnClickListener ock=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == cancelBtn){
                    dialog.cancel();
                }else{
                    actualPointNumber = pointNumber;
                    customise.savePreferences("Puntos","" + actualPointNumber);
                    paintView.setPoints(actualPointNumber);
                    pointCounter.setText(String.valueOf(actualPointNumber));

                    dialog.cancel();
                }
            }
        };

        cancelBtn.setOnClickListener(ock);
        okBtn.setOnClickListener(ock);
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
                        if (btn == lineColorBtn) {
                            paintView.setBackgroundColor("ROJO");
                            customise.savePreferences("ColorPat","ROJO");
                        } else{
                            alert("ROJO");
                        }
                    } else if (v == blueBtn) {
                        if(btn == lineColorBtn){
                            paintView.setBackgroundColor("AZUL");
                            customise.savePreferences("ColorPat","AZUL");
                        } else{
                            alert("AZUL");
                        }
                    } else if(v == greenBtn) {
                        if(btn == lineColorBtn) {
                            paintView.setBackgroundColor("VERDE");
                            customise.savePreferences("ColorPat","VERDE");
                        } else{
                            alert("VERDE");
                        }
                    } else if(v == yellowBtn) {
                        if(btn == lineColorBtn) {
                            paintView.setBackgroundColor("PASTEL");
                            customise.savePreferences("ColorPat","PASTEL");
                        } else{
                            alert("PASTEL");
                        }
                    }else if(v == interfazBtn) {
                        if(btn == lineColorBtn) {
                            paintView.setBackgroundColor("INTERFAZ");
                            customise.savePreferences("ColorPat","INTERFAZ");
                        } else{
                            alert("INTERFAZ");
                        }
                    }else if(v == brownBtn) {
                        if(btn == lineColorBtn) {
                            paintView.setBackgroundColor("CAFE");
                            customise.savePreferences("ColorPat","CAFE");
                        } else{
                            alert("CAFE");
                        }
                    } else if(v == whiteBtn) {
                        if(btn == lineColorBtn) {
                            paintView.setBackgroundColor("BLANCO");
                            customise.savePreferences("ColorPat","BLANCO");
                        } else{
                            alert("BLANCO");
                        }
                    } else if(v == grayBtn) {
                        if(btn == lineColorBtn) {
                            paintView.setBackgroundColor("GRIS");
                            customise.savePreferences("ColorPat","GRIS");
                        } else{
                            alert("GRIS");
                        }
                    } else if(v == blackBtn) {
                        if(btn == lineColorBtn) {
                            paintView.setBackgroundColor("NEGRO");
                            customise.savePreferences("ColorPat","NEGRO");
                        } else{
                            alert("NEGRO");
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

    private void alert(final String color){
        final AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setTitle(null);
        ad.setMessage("Si cambias de color de fondo todo tu dibujo se perdera, ¿quieres continuar?");

        final Context c = this;
        ad.setButton(AlertDialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                paintView.setForegroundColor(color, c);
                customise.savePreferences("ColorEsp", color);
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
        if(v == saveBtn){
            paintView.saveFile(getApplicationContext());                    //Save the file
        }else if(v == cleanBtn){
            paintView.clean();                                              //Clean the screen
            paintView.setForegroundColor(customise.getCustomes("ColorEsp"),this);
            paintView.setBackgroundColor(customise.getCustomes("ColorPat"));
        }else if(v == pointCounter){
            displayPointCounter();                                          //Change actualPointNumber of points
        }else if(v == lineColorBtn){
            colorPicker(lineColorBtn);                                      //Foreground color
        }else if(v == backgroundColorBtn){
            colorPicker(backgroundColorBtn);                                //Background color
        }else if(v == infoBtn){
            Intent it=new Intent(this, InfoActivity.class);    //Info View
            startActivity(it);
        }
    }
}
