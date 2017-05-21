package troisstudentsbjjm.theshapeofus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Circle;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Square;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Triangle;
import troisstudentsbjjm.theshapeofus.Input.InputController;


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



    // Towers
    private Square_Tower T_Square;

    // Enemies
    private Enemy_Square E_Square;
    private Enemy_Square E_Square2;
    private Enemy_Circle E_Circle;
    private Enemy_Circle E_Circle_1;
    private Enemy_Circle E_Circle_2;

    private Rect terrain;


    private Enemy_Triangle E_Triangle;
    private Triangle_Tower T_Tower;
    private Circle_Tower C_Tower;



    GameView(Context context, int screenWidth, int screenHeight){

        super(context);
        this.context = context;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        paint = new Paint();
        ourHolder = getHolder();

        vp = new Viewport(screenWidth,screenHeight);
        terrain = new Rect(0,screenHeight/2+vp.getPixelsPerMeter(),screenWidth,screenHeight);
        // Towers


        T_Tower =  new Triangle_Tower((int)(screenWidth*0.5), (int)(screenHeight*0.5), vp.pixelsPerMeter);
        C_Tower = new Circle_Tower(1000,(float) (screenHeight*0.5),vp.pixelsPerMeter);
        T_Square = new Square_Tower(800,(int) ((screenHeight*0.5)), vp.pixelsPerMeter);



        // Enemies

        E_Square = new Enemy_Square(-50,(int)((screenHeight*0.5)), 80, vp.pixelsPerMeter, (int)(screenWidth*0.5), (int)(screenHeight*0.5));      //40 is the square's health for now
        E_Square2 = new Enemy_Square(vp.pixelsPerMeter,(int)((screenHeight*0.5)), 40, vp.pixelsPerMeter, (int)(screenWidth*0.5), (int)(screenHeight*0.5));
        E_Circle = new Enemy_Circle(400, (int)((screenHeight * 0.5)), 40, vp.pixelsPerMeter);
        E_Circle_1 = new Enemy_Circle(200, (int)((screenHeight * 0.5)), 40, vp.pixelsPerMeter);
        E_Circle_2 = new Enemy_Circle(100, (int)((screenHeight * 0.5)), 40, vp.pixelsPerMeter);


        E_Triangle = new Enemy_Triangle(0, (int)((screenHeight * 0.5)), 10, vp.pixelsPerMeter);

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


    private void update(){
        counter++;
        if (counter == 100){
            Avgfps = sumfps/counter;
            counter = 0;
            sumfps = 0;
        }

        E_Square.update(E_Square2,vp.pixelsPerMeter,fps);
        E_Square2.update(E_Square,vp.pixelsPerMeter,fps);
        E_Triangle.update(vp.pixelsPerMeter,fps,gravity);



        T_Square.update(E_Square, fps);
        T_Tower.update(E_Square, fps);
        T_Tower.update(E_Square2, fps);
//        C_Tower.update(E_Square2,fps);
//        C_Tower.update(E_Square,fps);
        E_Circle.update(vp.pixelsPerMeter, fps);
        E_Circle_1.update(vp.pixelsPerMeter, fps);
        E_Circle_2.update(vp.pixelsPerMeter, fps);
        E_Circle.isCollided(E_Circle_1);
        E_Circle.isCollided(E_Circle_2);
        T_Square.update(E_Circle, fps);

    }


    private void draw(){
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            paint.setColor(Color.argb(255, 0, 0, 0));
            canvas.drawColor(Color.argb(255, 0, 0, 0));
            paint.setColor(Color.argb(255,255,255,255));
            paint.setTextSize(30);
            canvas.drawText("FPS:"+Avgfps,screenWidth/5,screenHeight/5,paint);

            canvas.drawRect(terrain,paint);

            // Towers
            T_Square.draw(canvas,paint);
            T_Tower.draw(canvas, paint);
            C_Tower.draw(canvas, paint);


            // Enemies

//            E_Square.draw(canvas,paint);
//            E_Square2.draw(canvas,paint);
            E_Circle.draw(canvas,paint);
//            if(!E_Circle_1.isDead) {    E_Circle_1.draw(canvas,paint); }
//            if(!E_Circle_2.isDead) {    E_Circle_2.draw(canvas,paint);  }
            E_Triangle.draw(canvas, paint);


            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
}
