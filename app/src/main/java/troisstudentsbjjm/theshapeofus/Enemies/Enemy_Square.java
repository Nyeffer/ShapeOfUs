package troisstudentsbjjm.theshapeofus.Enemies;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.Random;

import troisstudentsbjjm.theshapeofus.DeathAnimation;
import troisstudentsbjjm.theshapeofus.Primatives.Square;

/**
 * Created by Jeffherson on 2017-05-15.
 */

public class Enemy_Square extends Square{

    private PointF velocity;
    private PointF pivot;

    private boolean facingRight = true;

    private int angleD = 0;                             //angle to rotate square on canvas
    private int angularVelocity = 180;                  //angular velocity in degrees per second
    private final int GRAVITY = 7;

    private boolean isDead;
    private boolean rolling;

    private int damage;
    private int health;
    private int speed;
    private int pixelsPerMeter;

    private boolean isBlocked;

    public Enemy_Square(int x,int y, int health, int pixelsPerMeter) {

        this.health = health;
        updateSize();
        location.set(x,y);
        setHitBox(x,y,pixelsPerMeter);
        pivot = new PointF();
        isDead = false;
        this.pixelsPerMeter = pixelsPerMeter;
        isBlocked = false;

    }

    // the enemy squares update will rotate the square by incrementing a angle and using this angle to rotate the rect in draw
    // if the angle is approaching +-90 degrees then the shape is moved and the angle is reset.
    // also if the angle is greater than 45 degree the square rotates faster, think of a tipping over effect
    public void update(int pixelsPerMeter, long fps) {
        setPivot();
        if (facingRight){
            if (angleD >= 85){
                Move(pixelsPerMeter);
                angleD = 0;
            } else if (angleD > 45){
                angleD += angularVelocity*2/fps;
            } else {
                angleD += angularVelocity/fps;
            }
        } else if (!facingRight){
            if (angleD <= -85){
                Move(pixelsPerMeter);
                angleD = 0;
            } else if (angleD < -45){
                angleD -= angularVelocity*2/fps;
            } else {
                angleD -= angularVelocity/fps;
            }
        }
    }

    public void deathAnim() {
        if(isDead) {
            // Draw the death sprite here

        }
    }

    //shows relationship between health and size.
    private void updateSize(){setSize ((float) (health * 0.025));}


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
        if (angleD >= 85){
            location.x += size*pixelsPerMeter;
            setHitBox((int)location.x,(int)location.y,pixelsPerMeter);
            angleD = 0;
        } else if (angleD <= -85){
            location.x -= size*pixelsPerMeter;
            setHitBox((int)location.x,(int)location.y,pixelsPerMeter);
            angleD = 0;
        }
    }

    // set color to white, rotate canvas, draw rect, save the orientation, return the rest of the canvas to normal.
    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(255,255,255,255));
        canvas.rotate(angleD,pivot.x,pivot.y);
        canvas.drawRect(hitBox,paint);
        canvas.save();
        canvas.restore();
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
//    public int getHealth() { return health; }
//    public boolean getIsDead() { return isDead; }



}
