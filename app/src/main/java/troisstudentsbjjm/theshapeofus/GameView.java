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

import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Square;
import troisstudentsbjjm.theshapeofus.Input.InputController;

/**
 * Created by mrber on 2017-05-15.
 */

//this is where we update and draw

public class GameView extends SurfaceView implements Runnable {

    Context context;

    private volatile boolean running;
    private Thread gameThread = null;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    private long startFrameTime;
    private long timeThisFrame;
    private long fps = 60;

    int screenWidth;
    int screenHeight;

    private Viewport vp;
    private LevelManager lm;
    private Enemy_Square E_Square;
    InputController ic;

    private boolean debugging = true;


    GameView(Context context, int screenWidth, int screenHeight){

        super(context);
        this.context = context;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        paint = new Paint();
        ourHolder = getHolder();

        vp = new Viewport(screenWidth,screenHeight);
        lm = new LevelManager(context, vp.getPixelsPerMeterX(), screenWidth, ic, "1", 0, 0);
        E_Square = new Enemy_Square(vp.getPixelsPerMeterX(),(int)((screenHeight*0.5)), 40, vp.getPixelsPerMeterY());      //40 is the square's health for now

        running = true;

        ic = new InputController(vp.getScreenWidth(), vp.getScreenHeight());
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


    private void update(){

        E_Square.update(vp.getPixelsPerMeterX(),fps);
    }


    private void draw(){
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            paint.setColor(Color.argb(255, 0, 0, 0));
            canvas.drawColor(Color.argb(255, 0, 0, 0));
            paint.setColor(Color.argb(255,255,255,255));
            paint.setTextSize(30);
            //canvas.drawText("FPS:"+fps,screenWidth/5,screenHeight/5,paint);

            // Text for debugging
            if(debugging) {
                paint.setTextSize(24);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawText("fps: " + fps, 10, 60, paint);

                canvas.drawText("num objects: " + lm.gameObjects.size(), 10, 100, paint);

                canvas.drawText("num clipped: " + vp.getNumClipped(), 10, 140, paint);

                canvas.drawText("Gravity: " + lm.gravity, 10, 180, paint);

                // For reset the number of clipped objects per frame
                vp.resetNumClipped();
            }// End if(debugging)

            // Draw buttons
            paint.setColor(Color.argb(80, 255, 255, 255));
            //ArrayList<Rect> buttonsToDraw;
            //buttonsToDraw = ic.getButtons();

            // Draws the pause button
            Rect drawPause;
            drawPause = ic.PauseButton();

            RectF rp = new RectF(drawPause.left, drawPause.top, drawPause.right, drawPause.bottom);
            canvas.drawRoundRect(rp, 15f, 15f, paint);

            if(lm.isPlaying()) {
                paint.setColor(Color.argb(200, 255, 255, 255));
                paint.setTextSize(64);
                canvas.drawText("Pause", drawPause.left + 25, drawPause.bottom - 50, paint);
            } else if(!lm.isPlaying()) {
                paint.setColor(Color.argb(200, 255, 255, 255));
                paint.setTextSize(64);
                canvas.drawText("Play", drawPause.left + 50, drawPause.bottom - 50, paint);
            }

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


            //for(Rect rect : buttonsToDraw) {
            //    RectF rf = new RectF(rect.left, rect.top, rect.right, rect.bottom);

            //    canvas.drawRoundRect(rf, 15f, 15f, paint);
            //}

            // draw paused text
            if(!this.lm.isPlaying()) {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.argb(255, 255, 255, 255));

                paint.setTextSize(120);
                canvas.drawText("Paused", vp.getScreenWidth() / 2, vp.getScreenHeight() / 2, paint);
            }

            canvas.drawRect(0,screenHeight/2+vp.getPixelsPerMeterX(),screenWidth,screenHeight,paint);

            E_Square.draw(canvas,paint);

            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
}
