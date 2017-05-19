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

    private PointF velocity;
    public PointF pivot;
    public PointF center;

    public float angleD = 0;                            //angle to rotate square on canvas
    private float angularVelocity;                      //angular velocity in degrees per second we will divide it by fps to get degrees to rotate per frame
    private final float MAX_JUMP_VELOCITY = -200;
    private float tempYpos;
    private float health;                               //added in constructor

    private final float GRAVITY = -10;                   //this will be in meters per second per second we will multiply by pixelspermeter to get pixels per second per second

    private boolean isDead = false;                     //this will be used to initialize our death animation and to remove the object
    public boolean rolling;                            // the shape is either rolling or jumping
    private boolean facingRight = true;
    private boolean isBlocked;                          //if not blocked...move, if blocked... attack.

    private int damage;                                 //TODO
    private int pixelsPerMeter;                         //temporary

    private final long TIME_BETWEEN_JUMPS = 500;
    private long jumpStop = 0;


    public Enemy_Square(int x,int y, int health, int pixelsPerMeter) {

        this.health = health;
        updateSize();
        location.set(x,y);
        setHitBox(x,y,pixelsPerMeter);
        center = new PointF((float) (hitBox.left+0.5*size), hitBox.bottom);
        pivot = new PointF();
        velocity = new PointF();
        isDead = false;
        this.pixelsPerMeter = pixelsPerMeter;
        isBlocked = false;
        tempYpos = location.y + 1;
        jumpStop = 0;
        setRolling();
    }
    // the enemy squares update will rotate the square by incrementing a angle and using this angle to rotate the rect in draw
    // if the angle is approaching +-90 degrees then the shape is moved and the angle is reset.
    // also if the angle is greater than 45 degree the square rotates faster, think of a tipping over effect
    public void update(int pixelsPerMeter, long fps) {
        if (!isDead){
            if (rolling){
                angularVelocity = 60;
                roll(pixelsPerMeter,fps);
            } else {
                jump(pixelsPerMeter,fps);
            }
        } else {
            angularVelocity = 0;
        }
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



    private void jump(int pixelsPerMeter, long fps){
        updateSize();
        if (System.currentTimeMillis() >= TIME_BETWEEN_JUMPS + jumpStop){
            if (tempYpos <= location.y){
                velocity.set(1,MAX_JUMP_VELOCITY);
                location.y = tempYpos;
                jumpStop = System.currentTimeMillis();
            }
            if (location.y + velocity.y/fps >= tempYpos){
                location.y = tempYpos;
            } else {
                location.y += velocity.y/fps;
                velocity.y -= GRAVITY;
            }
            location.x += velocity.x;
            setHitBox(location.x,location.y,pixelsPerMeter);
            center.set((float) (hitBox.left+0.5*size), hitBox.bottom);
            setPivot();
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
        if (rolling){
            canvas.save();
            canvas.rotate(angleD,pivot.x,pivot.y);
            canvas.drawRect(hitBox,paint);
            canvas.restore();
        } else {
            canvas.drawRect(hitBox,paint);
        }
    }


    public void destroy(){
        isDead = true;
    }


    public void takeDamage(float damage){
        health -= damage;
    }

    // Setter and Getter

//    public void setVelocity(PointF velocity) { this.velocity = velocity;   }
//    public void setRotate(float rotate) { this.rotate = rotate; }
//    public void setDamage(int damage) { this.damage = damage;   }
//    public void setIsDead(boolean isDead) { this.isDead = isDead;   }
//
//    public PointF getVelocity() { return velocity;  }
//    public float getRotate() {  return rotate;  }
//    public int getDamage() { return damage; }
    public float getHealth() { return health; }
    public void setRolling(){
        if (health <= 40){
            rolling = false;
        } else {
            rolling = true;
        }
    }
//    public boolean getIsDead() { return isDead; }




}
