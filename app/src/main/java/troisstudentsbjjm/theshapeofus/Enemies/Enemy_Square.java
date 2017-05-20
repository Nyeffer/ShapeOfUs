package troisstudentsbjjm.theshapeofus.Enemies;





import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;



import android.graphics.PointF;
import android.util.Log;

import java.util.Random;

import troisstudentsbjjm.theshapeofus.DeathAnimation;
import troisstudentsbjjm.theshapeofus.Primatives.Square;

/**
 * Created by Jeffherson on 2017-05-15.
 */

public class Enemy_Square extends Square{

    public DeathAnimation deathAnimation;

    public PointF velocity;
    public PointF pivot;
    public PointF center;
    public PointF spawnPoint;

    public float angleD = 0;                            //angle to rotate square on canvas
    private float angularVelocity;                      //angular velocity in degrees per second we will divide it by fps to get degrees to rotate per frame
    private final float MAX_JUMP_VELOCITY = -200;
    private int tempYpos;
    private float health;                               //added in constructor

    private final float GRAVITY = -10;                   //this will be in meters per second per second we will multiply by pixelspermeter to get pixels per second per second

    public boolean isDead = false;                     //this will be used to initialize our death animation and to remove the object
    public boolean rolling;                            // the shape is either rolling or jumping
    private boolean facingRight = true;
    public boolean isBlocked = false;                  //if not blocked...move, if blocked... attack.

    private int damage;                                 //TODO
    private int pixelsPerMeter;                         //temporary

    private final long TIME_BETWEEN_JUMPS = 500;
    private long jumpStop = 0;


    public Enemy_Square(int x,int y, int health, int pixelsPerMeter, int omniGonPosX, int omniGonPosY) {

        this.health = health;
        updateSize();
        location.set(x,y);

        setHitBox(x,y,pixelsPerMeter);
        center = new PointF((float) (hitBox.left+0.5*size), hitBox.bottom);
        pivot = new PointF();
        velocity = new PointF();
        spawnPoint = new PointF(x,y);
        isDead = false;
        this.pixelsPerMeter = pixelsPerMeter;
        deathAnimation = new DeathAnimation(pixelsPerMeter, location.y + pixelsPerMeter, omniGonPosX, omniGonPosY);
        deathAnimation.setParticles(center.x, (float) (center.y - 0.5*size*pixelsPerMeter), size);
        isBlocked = false;
        tempYpos = (int)location.y;
        jumpStop = 0;
        setRolling();
    }


    public void update(int pixelsPerMeter, long fps) {
        if (isDead && isActive){
            deathAnimation.update(center.x, (float) (center.y - 0.5*size*pixelsPerMeter), size,fps);        //passing in the center point of the shape
        } else if ((!isDead || !isBlocked) && isActive){
            if (rolling){
                angularVelocity = 60;
                roll(pixelsPerMeter,fps);
            } else {
                jump(pixelsPerMeter,fps);
            }
        } else if (!isDead && isBlocked){
            //attack();
        }
//        else if (isDead && !isActive){
//            location.set(spawnPoint.x,spawnPoint.y);
//            setHitBox(spawnPoint.x,spawnPoint.y,pixelsPerMeter);
//            center = new PointF((float) (hitBox.left+0.5*size), hitBox.bottom);
//            pivot = new PointF();
//            velocity = new PointF();
//            isDead = false;
//            this.pixelsPerMeter = pixelsPerMeter;
//            isBlocked = false;
//            tempYpos = (int)location.y;
//            jumpStop = 0;
//            setRolling();
//        }
    }

    // increments the angle and moves the square if necessary
    private void roll(int pixelsPerMeter, long fps){
        if (facingRight){
            if (angleD >= 87){
                Move(pixelsPerMeter);
                angleD = 0;
            } else if (angleD > 45){
                angleD += angularVelocity*2/(fps*size);
            } else {
                angleD += angularVelocity/(fps*size);
            }
        } else if (!facingRight){
            if (angleD <= -87){
                Move(pixelsPerMeter);
                angleD = 0;
            } else if (angleD < -45){
                angleD -= angularVelocity*2/fps;
            } else {
                angleD -= angularVelocity/fps;
            }
        }
        setPivot();
    }


    private void respawn(){

    }


    private void jump(int pixelsPerMeter, long fps){
        updateSize();
        if (System.currentTimeMillis() >= TIME_BETWEEN_JUMPS + jumpStop){
            if (tempYpos <= location.y){
                velocity.set(2,MAX_JUMP_VELOCITY);
                location.y = tempYpos;
                jumpStop = System.currentTimeMillis();
            }
            if (location.y + velocity.y/fps >= tempYpos){
                location.y = tempYpos;
                setPivot();
                setRolling();
            } else {
                location.y += (int)(velocity.y/fps);
                velocity.y -= GRAVITY;
            }
            location.x += velocity.x;
            setHitBox(location.x,location.y,pixelsPerMeter);
            center.set((float) (hitBox.left+0.5*size), hitBox.bottom);
        }
    }

    //shows relationship between health and size.
    public void updateSize(){
        if (health * 0.025 >= 0.5){
            setSize ((float) (health * 0.025));
        } else {
            setSize((float) 0.5);
        }
    }


    public void setPivot(){
        //sets the pivot point for the canvas rotate function, if the angle is less than 0, the pivot is bottom left, if it is greater than 0, the pivot is bottom right
        if (angleD < 0){
            pivot.set(hitBox.left, hitBox.bottom);
        } else {
            pivot.set(hitBox.right, hitBox.bottom);
        }
    }

    // moves rect over by a factor of its size
    public void Move(int pixelsPerMeter){
        if (facingRight){
            location.x += size*pixelsPerMeter;
            updateSize();
            setHitBox(location.x,location.y,pixelsPerMeter);
            center.set((float) (hitBox.left+0.5*size), hitBox.bottom);
            setRolling();
        } else {
            location.x -= size*pixelsPerMeter;
            updateSize();
            setHitBox(location.x,location.y,pixelsPerMeter);
            center.set((float) (hitBox.left+0.5*size), hitBox.bottom);
            setRolling();
        }
    }

    // set color to white, rotate canvas, draw rect, save the orientation, return the rest of the canvas to normal.
    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(255,255,255,255));
        if (!isDead && isVisible){
            if (rolling){
                canvas.save();
                canvas.rotate(angleD,pivot.x,pivot.y);
                canvas.drawRect(hitBox,paint);
                canvas.restore();
            } else {
                canvas.drawRect(hitBox,paint);
            }
        } else if (isDead && isActive){
            deathAnimation.draw(canvas, paint);
        }
    }


    public void destroy(){
        isDead = true;
        angularVelocity = 0;
        velocity.set(0,0);
    }


    public void setRolling(){
        if (health <= 40){
            rolling = false;
        } else {
            rolling = true;
        }
    }


    public void takeDamage(float damage){
        health -= damage;
    }
    public float getHealth() { return health; }
}
