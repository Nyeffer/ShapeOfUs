package troisstudentsbjjm.theshapeofus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import android.os.Bundle;
import android.graphics.RectF;

import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Enemies.WaveSpawner;
import troisstudentsbjjm.theshapeofus.Input.InputController;
import troisstudentsbjjm.theshapeofus.Level.LevelManager;
import troisstudentsbjjm.theshapeofus.Towers.Circle_Tower;
import troisstudentsbjjm.theshapeofus.Towers.Square_Tower;
import troisstudentsbjjm.theshapeofus.Towers.Triangle_Tower;

public class GameView extends SurfaceView implements Runnable {
    private boolean debugging = true;
    private volatile boolean running;
    private Thread gameThread = null;
    public boolean playing;

    private boolean gameEnded;

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
    public int pixelsPerMeter;
    int counter = 0;                    //for calculating fps
    public int money = 0;

    private LevelManager lm;
    private InputController ic;
    private Rect terrain;

    GameView(Context context, int screenWidth, int screenHeight){
        super(context);
        this.context = context;

        pixelsPerMeter = (int)(screenWidth * 0.03);

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        paint = new Paint();
        ourHolder = getHolder();

        ic = new InputController(screenWidth,screenHeight, pixelsPerMeter);
        lm = new LevelManager(-50,(int)((screenHeight*0.5)), pixelsPerMeter, (int)(screenWidth*0.5), (int)(screenHeight*0.5));

        terrain = new Rect(0,screenHeight/2+pixelsPerMeter,screenWidth,screenHeight);

        playing = true;
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

        ic.handleInput(motionEvent, lm, this);
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
        if (playing) {
            counter++;
            if (counter == 100) {
                Avgfps = sumfps / counter;
                counter = 0;
                sumfps = 0;
            }
            lm.update(ic, fps);
        }
    }


    private void draw(){
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            paint.setColor(Color.argb(255, 0, 0, 0));
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            lm.draw(canvas,paint);

            paint.setColor(Color.argb(255,99,74,51));
            canvas.drawRect(terrain,paint);

            DrawPauseButton();
            DrawUpgradeButton();

            ic.drawButtons(canvas,paint);

            if(!playing) {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.argb(255, 255, 255, 255));

                paint.setTextSize(120);
                canvas.drawText("Paused", (int) (screenWidth*0.5), (int)(screenHeight*0.5), paint);
            }
            if(debugging) {
                paint.setTextSize(30);
                canvas.drawText("FPS:"+Avgfps,screenWidth/5,screenHeight/5,paint);
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

        if(playing) {
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(64);
            canvas.drawText("Pause", drawPause.left + 25, drawPause.bottom - 50, paint);
        } else if(!playing) {
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(64);
            canvas.drawText("Play", drawPause.left + 50, drawPause.bottom - 50, paint);
        }
    }


    public void DrawUpgradeButton() {
        // Draws the upgrade button
        // determines whether the button is active or not
        if(ic.isUpgradeTapped()) {
            paint.setColor(Color.argb(180, 255, 255, 255));
        } else if(!ic.isUpgradeTapped()) {
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
