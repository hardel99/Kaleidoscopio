package com.example.hardel.kaleidoscopio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class constr extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infor);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        estail();

        Button bk = (Button) findViewById(R.id.bak);
        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void estail(){
        Animation anni= AnimationUtils.loadAnimation(this,R.anim.magic2);
        anni.reset();
        LinearLayout lal= (LinearLayout) findViewById(R.id.lala);
        lal.clearAnimation();
        lal.setAnimation(anni);
    }
}
