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

import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Square;
import troisstudentsbjjm.theshapeofus.Input.InputController;
import troisstudentsbjjm.theshapeofus.Primatives.GameObject;

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

    int screenWidth;
    int screenHeight;

    private Viewport vp;
    private LevelManager lm;
    private Enemy_Square E_Square;
    InputController ic;


    GameView(Context context, int screenWidth, int screenHeight){

        super(context);
        this.context = context;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        paint = new Paint();
        ourHolder = getHolder();

        vp = new Viewport(screenWidth,screenHeight);
        E_Square = new Enemy_Square(vp.getPixelsPerMeterX(),(int)((screenHeight*0.5)), 40, vp.getPixelsPerMeterY());      //40 is the square's health for now

        running = true;

        loadLevel("Level 1", 0, 0);
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

        for(GameObject go : lm.gameObjects) {
            if(go.isActive()) {
                // Clip anything off-screen
                if(!vp.clipObjects(go.getWorldLocation().x, go.getWorldLocation().y, go.getWidth(), go.getHeight())) {
                    // Set visible flag to true
                    go.setVisible(true);

                    // check collisions with towers (if enemy touches them, do damage)
                    // TODO: make code

                    // Check bullet collision (for towers
                    for(int i = 0; i < 2; i++) { // TODO: get total bullets from towers in use
                        // Make a hitbox out of the current bullet
                        RectHitbox r = new RectHitbox();
                        // TODO: modify towers to get there bullets hitbox here
                        //r.setLeft(lm.player.bfg.getBulletX(i));
                        //r.setTop(lm.player.bfg.getBulletY(i));
                        //r.setRight(lm.player.bfg.getBulletX(i) + .1f);
                        //r.setBottom(lm.player.bfg.getBulletY(i) + .1f);

                        if(go.getHitBox().intersects(r)) {
                            // bullet collided, despawn until it respawns as a new bullet
                            // TODO: bullet despawn code here

                            // now respond based on what bullet collided with
                            if(go.getType() != 'c' && go.getType() != 's' && go.getType() != 't') {
                                // It missed, maybe play sound
                            } else if(go.getType() == 'c') {
                                // Circle was hit, damage it, play sound, kill it

                            } else if(go.getType() == 's') {
                                // Square was hit, damage it, play sound, kill it
                            } else if(go.getType() == 't') {
                                // Triangle was hit, you know the drill
                            }
                        }
                    }

                    if(lm.isPlaying()) {
                        // Run any un-clipped updates
                        go.update(fps, lm.gravity);
                    }
                } else {
                    // Set visible flag to false
                    go.setVisible(false);
                    // Now draw() can ignore them
                }
            }
        }
        if(lm.isPlaying()) {
            // Reset the players location as the center of the viewport
            vp.setWorldCenter(lm.gameObjects.get(lm.playerIndex).getWorldLocation().x, lm.gameObjects.get(lm.playerIndex).getWorldLocation().y);
            // Check if wave is over
            if () { // TODO: Make a function to check if all enemies have been killed
                // TODO: Start next wave, with a time delay most likely
            }
        }
    }


    private void draw(){
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            paint.setColor(Color.argb(255, 0, 0, 0));
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            // Draw all gameObjects
            Rect toScreen2d = new Rect();

            // Draw a layer at a time
            for(int layer = -1; layer <= 1; layer++) {
                for(GameObject go : lm.gameObjects) {
                    // Only draw if visible and this layer
                    if(go.isVisible() && go.getWorldLocation().z == layer) {
                        toScreen2d.set(vp.worldToScreen(go.getWorldLocation().x, go.getWorldLocation().y, go.getWidth(), go.getHeight()));

                        // Draw the appropriate bitmap (probably needs more for enemies and other)
                        canvas.drawBitmap(lm.bitmapsArray[lm.getBitmapIndex(go.getType())], toScreen2d.left, toScreen2d.top, paint);
                    }
                }
            }

            // Draw the bullets
            paint.setColor(Color.argb(255, 255, 255, 255));
            for(int i = 0; i < 2; i++) { // TODO: get total tower bullets in use
                // Pass in x and y coords as usual, then .25 and .05 for the bullet width and height
                toScreen2d.set(vp.worldToScreen(0, 0, .25f, .05f)); // TODO: get bullets x and y to draw acordingly (replace the 0's)
                canvas.drawRect(toScreen2d, paint);
            }

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

            // HUD Elements
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
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(64);
                canvas.drawText("Pause", drawPause.left + 25, drawPause.bottom - 50, paint);
            } else if(!lm.isPlaying()) {
                paint.setColor(Color.argb(255, 255, 255, 255));
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
