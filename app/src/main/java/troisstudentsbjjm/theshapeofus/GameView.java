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

import troisstudentsbjjm.theshapeofus.Enemies.WaveSpawner;
import troisstudentsbjjm.theshapeofus.Input.InputController;
import troisstudentsbjjm.theshapeofus.Towers.Circle_Tower;
import troisstudentsbjjm.theshapeofus.Towers.Square_Tower;
import troisstudentsbjjm.theshapeofus.Towers.Triangle_Tower;

public class GameView extends SurfaceView implements Runnable {
    private boolean debugging = true;
    private volatile boolean running;
    private Thread gameThread = null;
    public boolean playing;

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
    int counter = 0;

    private Viewport vp;
    private InputController ic;
    private LevelManager lm;

    private WaveSpawner spawner;

    public ArrayList<Square_Tower> square_towers;
    public ArrayList<Triangle_Tower> triangle_towers;
    public ArrayList<Circle_Tower> circle_towers;

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

        spawner = new WaveSpawner(-50,(int)((screenHeight*0.5)), pixelsPerMeter, (int)(screenWidth*0.5), (int)(screenHeight*0.5));

        terrain = new Rect(0,screenHeight/2+pixelsPerMeter,screenWidth,screenHeight);

        playing = true;
        running = true;

        square_towers = new ArrayList<>();
        circle_towers = new ArrayList<>();
        triangle_towers = new ArrayList<>();
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

        ic.handleInput(motionEvent, this);
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
            spawner.update();
            for (Square_Tower blockade : square_towers) {
                blockade.update(spawner.currentWave.circles, spawner.currentWave.squares, spawner.currentWave.triangles, fps);
                if (!blockade.isActive && spawner.currentWave.waveComplete){
                    ic.buildBlocks.hitBlocks.get(blockade.placementIndex).isActive = true;
                }
            }
            for (Circle_Tower shooter : circle_towers){
                shooter.update(spawner.currentWave.circles, spawner.currentWave.squares, spawner.currentWave.triangles, fps);
            }
            for (Triangle_Tower spikes : triangle_towers){
                spikes.update(spawner.currentWave.circles, spawner.currentWave.squares, spawner.currentWave.triangles, fps);
            }
            for (int i = 0; i < spawner.currentWave.squares.size(); i++) {
                if (i + 1 == spawner.currentWave.squares.size()) {
                    spawner.currentWave.squares.get(i).update(spawner.currentWave.squares.get(0), pixelsPerMeter, fps);
                } else {
                    spawner.currentWave.squares.get(i).update(spawner.currentWave.squares.get(i + 1), pixelsPerMeter, fps);
                }
            }
            for (int i = 0; i < spawner.currentWave.circles.size(); i++) {
                if (i + 1 == spawner.currentWave.circles.size()) {
                    spawner.currentWave.circles.get(i).update(spawner.currentWave.circles.get(0), pixelsPerMeter, fps);
                } else {
                    spawner.currentWave.circles.get(i).update(spawner.currentWave.circles.get(i + 1), pixelsPerMeter, fps);
                }
            }
            for (int i = 0; i < spawner.currentWave.triangles.size(); i++) {
                spawner.currentWave.triangles.get(i).update(pixelsPerMeter, fps);
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
            spawner.draw(canvas,paint);


            for (Square_Tower tower : square_towers) {
                tower.draw(canvas, paint);
            }
            for (Circle_Tower shooter : circle_towers){
                shooter.draw(canvas,paint);
            }
            if (spawner.currentWave.squares.size() != 0){
                for (int i = 0; i < spawner.currentWave.squares.size(); i++){
                    spawner.currentWave.squares.get(i).draw(canvas,paint);
                }
            }
            if (spawner.currentWave.triangles.size() != 0) {
                for (int i = 0; i < spawner.currentWave.triangles.size(); i++) {
                    spawner.currentWave.triangles.get(i).draw(canvas, paint);
                }
            }
            if (spawner.currentWave.circles.size() != 0) {
                for (int i = 0; i < spawner.currentWave.circles.size(); i++) {
                    spawner.currentWave.circles.get(i).draw(canvas, paint);
                }
            }
            for (Triangle_Tower spikes : triangle_towers){
                spikes.draw(canvas,paint);
            }
            paint.setColor(Color.argb(255,99,74,51));
            canvas.drawRect(terrain,paint);

            if(debugging) {
                paint.setTextSize(24);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawText("fps: " + fps, 10, 60, paint);

                canvas.drawText("Center X: " + screenWidth/2, 10, 220, paint);

                canvas.drawText("Center Y: " + screenHeight/2, 10, 260, paint);

            }

            DrawPauseButton();
            DrawUpgradeButton();

            if(!playing) {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.argb(255, 255, 255, 255));

                paint.setTextSize(120);
                canvas.drawText("Paused", (int) (screenWidth*0.5), (int)(screenHeight*0.5), paint);
            }
            ic.drawButtons(canvas,paint);

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
