package troisstudentsbjjm.theshapeofus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Circle;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Square;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Triangle;
import troisstudentsbjjm.theshapeofus.Input.InputController;
import troisstudentsbjjm.theshapeofus.Primatives.GameObject;


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
    private boolean debugging = true;
    private volatile boolean running;
    private Thread gameThread = null;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    Context context;
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
    private LevelManager lm;

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

        terrain = new Rect(0,screenHeight/2+vp.getPixelsPerMeterX(),screenWidth,screenHeight);

        wave1 = new Wave(-50,(int)((screenHeight*0.5)), 1, vp.getPixelsPerMeterX(), (int)(screenWidth*0.5), (int)(screenHeight*0.5),7,3);

        T_Tower =  new Triangle_Tower(200, (int)(screenHeight*0.5), vp.getPixelsPerMeterX());
        C_Tower = new Circle_Tower(600,(float) (screenHeight*0.5),vp.getPixelsPerMeterX());
        S_Tower = new Square_Tower(700,(int) ((screenHeight*0.5)), vp.getPixelsPerMeterX());

        loadLevel("Level 1", 20, 22);
    }

    public void loadLevel(String level, float px, float py) {
        lm = null;
        lm = new LevelManager(context, vp.getPixelsPerMeterX(), vp.getScreenWidth(), ic, level, px, py);

        ic = new InputController(vp.getScreenWidth(), vp.getScreenHeight());

        vp.setWorldCenter(px, py);
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
        if(lm != null) {
            ic.handleInput(motionEvent, lm, vp);
        }
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
                wave1.squares.get(i).update(wave1.squares.get(0), vp.getPixelsPerMeterX(), fps);
            } else {
                wave1.squares.get(i).update(wave1.squares.get(i + 1), vp.getPixelsPerMeterX(), fps);
            }
        }
        for (int i = 0; i < wave1.circles.size(); i++) {
            if (i + 1 == wave1.circles.size()) {
                wave1.circles.get(i).update(wave1.circles.get(0), vp.getPixelsPerMeterX(), fps);
            } else {
                wave1.circles.get(i).update(wave1.circles.get(i + 1), vp.getPixelsPerMeterX(), fps);
            }
        }
        for (int i = 0; i < wave1.triangles.size(); i++) {

            if (i + 1 == wave1.triangles.size()) {
                wave1.triangles.get(i).update(vp.getPixelsPerMeterX(), fps);
            } else {
                wave1.triangles.get(i).update(vp.getPixelsPerMeterX(), fps);
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
          
          // Text for debugging
            if(debugging) {
                paint.setTextSize(24);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawText("fps: " + fps, 10, 60, paint);

                canvas.drawText("num objects: " + lm.gameObjects.size(), 10, 100, paint);

                canvas.drawText("num clipped: " + vp.getNumClipped(), 10, 140, paint);

                canvas.drawText("Gravity: " + lm.gravity, 10, 180, paint);

                canvas.drawText("Center X: " + vp.getViewportWorldCenterX(), 10, 220, paint);

                canvas.drawText("Center Y: " + vp.getViewportWorldCenterY(), 10, 260, paint);

                // For reset the number of clipped objects per frame
                vp.resetNumClipped();
            }// End if(debugging)

            DrawPauseButton();
            DrawUpgradeButton();

            // draw paused text
            if(!this.lm.isPlaying()) {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.argb(255, 255, 255, 255));

                paint.setTextSize(120);
                canvas.drawText("Paused", vp.getScreenWidth() / 2, vp.getScreenHeight() / 2, paint);
            }
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void DrawPauseButton() {
        // Draws the pause button
        paint.setColor(Color.argb(80, 255, 255, 255));
        Rect drawPause;
        drawPause = ic.PauseButton();

        RectF rp = new RectF(drawPause.left, drawPause.top, drawPause.right, drawPause.bottom);
        canvas.drawRoundRect(rp, 15f, 15f, paint);

        if(lm.isPlaying()) {
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(64);
            canvas.drawText("Pause", drawPause.left + 25, drawPause.bottom - 50, paint);
        } else if(!lm.isPlaying()) {
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(64);
            canvas.drawText("Play", drawPause.left + 50, drawPause.bottom - 50, paint);
        }
    }

    public void DrawUpgradeButton() {
        // Draws the upgrade button
        // determines whether the button is active or not
        if(ic.isUpgradeTapped() == true) {
            paint.setColor(Color.argb(180, 255, 255, 255));
        } else if(ic.isUpgradeTapped() == false) {
            paint.setColor(Color.argb(80, 255, 255, 255));
        }
        Rect drawUpgrade;
        drawUpgrade = ic.UpgradeButton();

        RectF ru = new RectF(drawUpgrade.left, drawUpgrade.top, drawUpgrade.right, drawUpgrade.bottom);
        canvas.drawRoundRect(ru, 15f, 15f, paint);

        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(52);
        canvas.drawText("Upgrade", drawUpgrade.left + 15, drawUpgrade.bottom - 55, paint);
    }
}
