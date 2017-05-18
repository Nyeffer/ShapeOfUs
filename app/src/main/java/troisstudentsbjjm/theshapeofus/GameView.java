package troisstudentsbjjm.theshapeofus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Circle;
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
    private InputController ic;
    private Enemy_Square E_Square;
    private Enemy_Circle E_Circle;


    GameView(Context context, int screenWidth, int screenHeight){

        super(context);
        this.context = context;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        paint = new Paint();
        ourHolder = getHolder();

        vp = new Viewport(screenWidth,screenHeight);
        E_Square = new Enemy_Square(0,(int)((screenHeight*0.5)), 40, vp.pixelsPerMeter);      //40 is the square's health for now
        E_Circle = new Enemy_Circle(0,(int)((screenHeight*0.5)), 40, vp.pixelsPerMeter);
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


    private void update(){

        E_Square.update(vp.pixelsPerMeter,fps);
        E_Circle.update(vp.pixelsPerMeter,fps);
    }


    private void draw(){
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            paint.setColor(Color.argb(255, 0, 0, 0));
            canvas.drawColor(Color.argb(255, 0, 0, 0));
            paint.setColor(Color.argb(255,255,255,255));
            paint.setTextSize(30);
            canvas.drawText("FPS:"+fps,screenWidth/5,screenHeight/5,paint);

            canvas.drawRect(0,screenHeight/2+vp.getPixelsPerMeter(),screenWidth,screenHeight,paint);

            E_Square.draw(canvas,paint);
            E_Circle.draw(canvas,paint);


            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
}
