package com.example.hardel.kaleidoscopio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaintView extends View{

    private Path line;
    private Paint fingerCircle, bmp, paint;
    private Bitmap bt;
    private Canvas canvas;
    private final static float TOLERANCE =4;
    private int width, height;
    private Point d;
    private Matrix matrix = new Matrix();
    private Rect screen;
    private int reflections = 30;
    private Path[] bit = new Path[reflections];
    private Point[] dXY = new Point[reflections];
    private float angle, counter;
    private Color lineColor = new Color();
    private Color backgroundColor = new Color();

    //Line RGB
    int r = 255;
    int g = 0;
    int b = 0;

    //Background RGB
    int bR = 0;
    int bG = 0;
    int bB = 0;

    public PaintView(Context context) {
        super(context);
        prepareActivity();
    }

    public PaintView(Context context, AttributeSet attS){
        super(context, attS);
        prepareActivity();
    }

    public PaintView(Context context, AttributeSet attS, int style){
        super(context, attS, style);
        prepareActivity();
    }

    public void prepareActivity(){
        setDrawingCacheEnabled(true);

        for (int i = 0; i < reflections; i++){
            bit[i] = new Path();
        }

        line = new Path();
        bmp = new Paint(Paint.DITHER_FLAG);
        fingerCircle = new Paint();
        paint = new Paint();

        fingerCircle.setAntiAlias(true);
        fingerCircle.setColor(Color.rgb(r, g, b));
        fingerCircle.setStyle(Paint.Style.STROKE);
        fingerCircle.setStrokeJoin(Paint.Join.MITER);
        fingerCircle.setStrokeWidth(4f);

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.rgb(r, g, b));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3.5f);

        angle = 360f / reflections;
    }

    @Override
    protected void onSizeChanged(int w, int h, int aw, int ah){
        super.onSizeChanged(w, h, aw, ah);

        width = w;
        height = h;

        screen = new Rect(0,0, width, height);

        bt = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bt);
        canvas.drawColor(Color.rgb(bR, bG, bB));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bt,0,0, bmp);

        for (int i = 0; i < reflections; i++){
            canvas.drawPath(bit[i], paint);
        }

        canvas.drawPath(line, fingerCircle);
    }

    private void track(float x, float y) {
        counter = 0;

        for (int i = 0; i < reflections; i++){
            d = coordinate(x, y, counter);
            bit[i].reset();
            bit[i].moveTo(d.x, d.y);

            dXY[i] = d;
            counter += angle;
        }
    }

    private void moveTo(float x, float y){
        //Differentials of the paths
        float dx = Math.abs(d.x - dXY[0].x);
        float dy = Math.abs(d.y - dXY[0].y);

        counter = 0;

        if(dx >= TOLERANCE || dy >= TOLERANCE){
            for (int i = 0; i< reflections; i++){
                d = coordinate(x,y, counter);
                bit[i].quadTo(dXY[i].x, dXY[i].y,(dXY[i].x+d.x)/2,(dXY[i].y+d.y)/2);
                dXY[i] = d;
                counter += angle;
            }

            line.reset();
            line.addCircle(dXY[0].x, dXY[0].y,20, Path.Direction.CW);
        }
    }

    private void draw(){
        counter =0;

        for (int i = 0; i < reflections; i++){
            bit[i].lineTo(dXY[i].x, dXY[i].y);
            canvas.drawPath(bit[i], paint);
            bit[i].reset();
        }

        line.reset();
    }

    /*private Point coordinate(float change_point_icon, float y, double angl){
        //ra = (float) Math.sqrt( ((float) Math.pow((change_point_icon-cx),2))+( (float) Math.pow((y-cy),2)));   //obtenemos el radio
        float a= (float) (Math.sin(Math.toDegrees(angl)) * ra) + y;   //obtenemos y,eje de las abscisas
        float o = (float) (Math.cos(Math.toDegrees(angl)) * ra) + change_point_icon;  //obtenemos change_point_icon, eje de las ordenadas
        Point afg=new Point((int)o,(int)a);

        return afg;
    }*/

    private Point coordinate(float x, float y, float angle){
        matrix.setRotate(angle, screen.exactCenterX(), screen.exactCenterY());

        float[] previous = new float[2];
        previous[0] = x;
        previous[1] = y;

        matrix.mapPoints(previous);

        return new Point((int)previous[0],(int)previous[1]);
    }

    public void setPoints(int n){
        reflections = n;
        angle = 360f/ reflections;
    }

    public int getPoints(){
        return reflections;
    }

    public void setBackgroundColor(String color){
        switch (color){
            case "ROJO":
                r = 230;
                g = 5;
                b = 5;
                break;
            case "NEGRO":
                r = 0;
                g = 0;
                b = 0;
                break;
            case "AZUL":
                r = 1;
                g = 22;
                b = 255;
                break;
            case "VERDE":
                r = 4;
                g = 100;
                b = 14;
                break;
            case "BLANCO":
                r = 255;
                g = 255;
                b = 255;
                break;
            case "PASTEL":
                r = 255;
                g = 190;
                b = 240;
                break;
            case "CAFE":
                r = 113;
                g = 56;
                b = 3;
                break;
            case "GRIS":
                r = 128;
                g = 128;
                b = 128;
                break;
            case "INTERFAZ":
                r = 50;
                g = 170;
                b = 255;
                break;
        }

        if(paint != null){
            paint.setColor(Color.rgb(r , g, b));
            fingerCircle.setColor(Color.rgb(r, g, b));
        }
    }

    public void setForegroundColor(String color, Context c){
        switch (color){
            case "ROJO":
                bR = 171;
                bG = 5;
                bB = 5;
                break;
            case "NEGRO":
                bR = 0;
                bG = 0;
                bB = 0;
                break;
            case "AZUL":
                bR = 1;
                bG = 22;
                bB = 255;
                break;
            case "VERDE":
                bR = 4;
                bG = 100;
                bB = 14;
                break;
            case "BLANCO":
                bR = 255;
                bG = 255;
                bB = 255;
                break;
            case "PASTEL":
                bR = 255;
                bG = 190;
                bB = 240;
                break;
            case "CAFE":
                bR = 113;
                bG = 56;
                bB = 3;
                break;
            case "GRIS":
                bR = 128;
                bG = 128;
                bB = 128;
                break;
            case "INTERFAZ":
                bR = 50;
                bG = 170;
                bB = 255;
                break;
        }

        if(canvas !=null){
            clean();
            canvas.drawColor(Color.rgb(bR, bG, bB));
        }
    }

    public void saveFile(Context context){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Kaleidoscopio/Imagenes";
        File file = new File(path);
        Toast t = null;

        if(!file.exists()) {
            file.mkdirs();
        }

        try {
            File fullPath = new File(path + "/" + new SimpleDateFormat("ddMMyyyy__HHmm").format(new Date()) + ".png");
            FileOutputStream fos=new FileOutputStream(fullPath);
            bt.compress(Bitmap.CompressFormat.PNG,100, fos);
            fos.flush();
            fos.close();
            t = Toast.makeText(context,"Guardado Exitosamente :)",Toast.LENGTH_SHORT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            t = Toast.makeText(context,"No se ha logrado guardar :'(",Toast.LENGTH_SHORT);
        } finally {
            assert t != null;
            t.show();
        }
    }

    public void clean(){
        setDrawingCacheEnabled(false);
        onSizeChanged(width,height,width,height);
        invalidate();
        setDrawingCacheEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                track(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTo(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                draw();
                invalidate();
                break;
        }

        return true;
    }
}
