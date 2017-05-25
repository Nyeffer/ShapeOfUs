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

import troisstudentsbjjm.theshapeofus.Input.InputController;
import troisstudentsbjjm.theshapeofus.Level.LevelManager;
import troisstudentsbjjm.theshapeofus.Towers.OmniGon;

public class GameView extends SurfaceView implements Runnable {
    private boolean debugging = true;
    private volatile boolean running;
    private Thread gameThread = null;
    public boolean playing;
    public boolean notEnoughResources;

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

    OmniGon omniGon;

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

        omniGon = new OmniGon(context,(float) (screenWidth*0.75), (float) (screenHeight*0.5),pixelsPerMeter);
        ic = new InputController(screenWidth,screenHeight, pixelsPerMeter);
        lm = new LevelManager(-50,(int)((screenHeight*0.5)), pixelsPerMeter, (int)(omniGon.omniGonCenter.x), (int)(omniGon.omniGonCenter.y));

        terrain = new Rect(0,screenHeight/2+pixelsPerMeter,screenWidth,screenHeight);

        playing = true;
        notEnoughResources = false;
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
            if (omniGon.isActive) {
                omniGon.update(lm.spawner.currentWave.circles, lm.spawner.currentWave.squares, lm.spawner.currentWave.triangles, fps);
            } else {
                playing = false;
            }
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

            omniGon.draw(canvas,paint);

            ic.drawButtons(canvas,paint,this);

            if(notEnoughResources) {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.argb(255, 255, 0, 0));

                paint.setTextSize(120);
                canvas.drawText("Insufficient Resources", (int) (screenWidth*0.5), (int)(screenHeight*0.5), paint);

                notEnoughResources = false;
            }

            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(120);
            if(!playing && omniGon.isActive) {
                canvas.drawText("Paused", (int) (screenWidth*0.5), (int)(screenHeight*0.5), paint);
            } else if (!playing){
                canvas.drawText("Game Over", (int) (screenWidth*0.5), (int)(screenHeight*0.5), paint);
                canvas.drawText("Highest wave reached " + lm.spawner.waveNumber, (int) (screenWidth*0.5), (int)(screenHeight*0.7), paint);
            }
            if(debugging) {
                paint.setTextSize(30);
                canvas.drawText("FPS:"+Avgfps,screenWidth/5,screenHeight/5,paint);
            }
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
}
