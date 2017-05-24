package troisstudentsbjjm.theshapeofus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Circle;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Square;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Triangle;
import troisstudentsbjjm.theshapeofus.Input.InputController;


import static android.R.attr.focusable;
import static android.R.attr.gravity;

import troisstudentsbjjm.theshapeofus.Towers.Circle_Tower;
import troisstudentsbjjm.theshapeofus.Towers.Square_Tower;

import troisstudentsbjjm.theshapeofus.Towers.Triangle_Tower;



/**
 * Created by mrber on 2017-05-15.
 */

//this is where we update and draw

public class GameView extends SurfaceView implements Runnable {

    Context context;

    private volatile boolean running;
    private Thread gameThread = null;

    private boolean gameEnded;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    private long startFrameTime;
    private long timeThisFrame;
    private long fps = 60;
    private long sumfps;
    private long Avgfps;

    int screenWidth;
    int screenHeight;
    int counter = 0;

    private Viewport vp;
    private InputController ic;

    private Wave wave1;

    private Square_Tower S_Tower;
    private Triangle_Tower T_Tower;
    private Circle_Tower C_Tower;

    private Rect terrain;

    GameView(Context context, int screenWidth, int screenHeight){

        super(context);
        this.context = context;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        paint = new Paint();
        ourHolder = getHolder();

        vp = new Viewport(screenWidth,screenHeight);
        terrain = new Rect(0,screenHeight/2+vp.getPixelsPerMeter(),screenWidth,screenHeight);

        wave1 = new Wave(-50,(int)((screenHeight*0.5)), 1, vp.pixelsPerMeter, (int)(screenWidth*0.5), (int)(screenHeight*0.5),7,3);

        T_Tower =  new Triangle_Tower(200, (int)(screenHeight*0.5), vp.pixelsPerMeter);
        C_Tower = new Circle_Tower(600,(float) (screenHeight*0.5),vp.pixelsPerMeter);
        S_Tower = new Square_Tower(700,(int) ((screenHeight*0.5)), vp.pixelsPerMeter);


        running = true;

    }


    @Override
    public void run(){
        while (running){
            startFrameTime = System.currentTimeMillis();

            update();
            draw();

            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1){
                fps = 1000/timeThisFrame;
                sumfps += fps;
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        return true;
    }


    public void resume(){
        gameThread = new Thread(this);
        gameThread.start();
        running = true;
    }


    public void pause(){
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e){
            Log.e("Error", "Failed to load thread");
        }
    }


    private void update() {
        counter++;
        if (counter == 100) {
            Avgfps = sumfps / counter;
            counter = 0;
            sumfps = 0;
        }

        wave1.spawnEnemies();

        C_Tower.update(wave1.circles, wave1.squares, wave1.triangles, fps);
        S_Tower.update(wave1.circles, wave1.squares, wave1.triangles, fps);
        T_Tower.update(wave1.circles, wave1.squares, wave1.triangles, fps);

        for (int i = 0; i < wave1.squares.size(); i++) {
            if (i + 1 == wave1.squares.size()) {
                wave1.squares.get(i).update(wave1.squares.get(0), vp.pixelsPerMeter, fps);
            } else {
                wave1.squares.get(i).update(wave1.squares.get(i + 1), vp.pixelsPerMeter, fps);
            }
        }
        for (int i = 0; i < wave1.circles.size(); i++) {
            if (i + 1 == wave1.circles.size()) {
                wave1.circles.get(i).update(wave1.circles.get(0), vp.pixelsPerMeter, fps);
            } else {
                wave1.circles.get(i).update(wave1.circles.get(i + 1), vp.pixelsPerMeter, fps);
            }
        }
        for (int i = 0; i < wave1.triangles.size(); i++) {

            if (i + 1 == wave1.triangles.size()) {
                wave1.triangles.get(i).update(vp.pixelsPerMeter, fps);
            } else {
                wave1.triangles.get(i).update(vp.pixelsPerMeter, fps);
            }
        }
    }


    private void draw(){
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            paint.setColor(Color.argb(255, 0, 0, 0));
            canvas.drawColor(Color.argb(255, 0, 0, 0));
            paint.setColor(Color.argb(255,255,255,255));

            paint.setTextSize(30);
            canvas.drawText("FPS:"+Avgfps,screenWidth/5,screenHeight/5,paint);

            C_Tower.draw(canvas, paint);
            S_Tower.draw(canvas,paint);

            if (wave1.squares.size() != 0){
                for (int i = 0; i < wave1.squares.size(); i++){
                    wave1.squares.get(i).draw(canvas,paint);
                }
            }
            if (wave1.triangles.size() != 0) {
                for (int i = 0; i < wave1.triangles.size(); i++) {
                    wave1.triangles.get(i).draw(canvas, paint);
                }
            }
            if (wave1.circles.size() != 0) {
                for (int i = 0; i < wave1.circles.size(); i++) {
                    wave1.circles.get(i).draw(canvas, paint);
                }
            }

            T_Tower.draw(canvas, paint);

            paint.setColor(Color.argb(255,99,74,51));
            canvas.drawRect(terrain,paint);
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
}
