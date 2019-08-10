package com.example.hardel.kaleidoscopio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashScreen extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        startAnimation();
    }

    private void startAnimation() {
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.info_anim);
        anim.reset();
        LinearLayout linear = findViewById(R.id.lin_lay);
        linear.clearAnimation();
        linear.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this,R.anim.splas_screen_anim);
        anim.reset();
        anim.setFillAfter(true);
        ImageView splashImg= findViewById(R.id.iV2);
        splashImg.clearAnimation();
        splashImg.setAnimation(anim);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    int wait = 0;

                    while (wait < 3000){
                        sleep(100);
                        wait += 100;
                    }

                    Intent nt=new Intent().setClass(SplashScreen.this,MainActivity.class);
                    nt.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(nt);

                    SplashScreen.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SplashScreen.this.finish();
                }
            }
        };

        thread.start();
    }
}
