package troisstudentsbjjm.theshapeofus.Enemies;




import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;

import android.graphics.PointF;

import java.util.Random;

import troisstudentsbjjm.theshapeofus.DeathAnimation;
import troisstudentsbjjm.theshapeofus.Primatives.Triangle;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by Jeffherson on 2017-05-15.
 */

public class Enemy_Triangle extends Triangle{

    public DeathAnimation deathAnimation;               //when it goes to heaven

    public PointF center;                               //center point
    public PointF spawnPoint;                           //reference to original position
    public PointF velocity;                             //velocity in meters/second

    private int pixelsPerMeter;                         //temporary (hopefully)

    public float angleD = 0;                            //angle to rotate on canvas
    public float damage = 20;                           //kamikaze, after dealing damage triangle will die
    public float health;                                //added in constructor
    private float angularVelocity;                      //angular velocity in degrees per second
    private final float GRAVITY = -10;                  //this will be in meters per second per second
    private final float MAX_JUMP_VELOCITY = -200;       //how fast the shape jumps

    public boolean attacking;                           //used to deal damage to tower
    public boolean facingRight;                         //coming from the left(facingRight) or the right(!facingRight)
    public boolean inPositionToAttack;                  //self explanatory
    public boolean isBlocked;                           //if not blocked...move, if blocked... attack.
    public boolean isDead;                              //this will be used to initialize our death animation

    private long jumpStop = 0;                          //used as timer to implement TIME_BETWEEN_JUMPS
    private long attackTime = 0;                        //used as timer to implement TIME_TO_ROTATE
    private final long TIME_BETWEEN_JUMPS = 250;        //self explanatory == 0.5seconds
    private final double TIME_TO_ROTATE = 0.4;          //how long it will take triangle to rotate


    public Enemy_Triangle(int x,int y, int health, int pixelsPerMeter, int omniGonPosX, int omniGonPosY) {
        this.health = health;
        this.pixelsPerMeter = pixelsPerMeter;

        size = (float) 0.5;

        location.set(x,y);
        setPoints(x,y,pixelsPerMeter);

        velocity = new PointF((float) 2.5,MAX_JUMP_VELOCITY);
        spawnPoint = new PointF(x,y);
        center = new PointF();

        deathAnimation = new DeathAnimation(pixelsPerMeter, location.y + pixelsPerMeter, omniGonPosX, omniGonPosY, 1);
        deathAnimation.setParticles(center.x, (float) (center.y - 0.5*size*pixelsPerMeter), (float) (0.5*size));

        isBlocked = false;
        inPositionToAttack = false;
        isDead = false;
        isActive = true;
        attacking = false;
        facingRight = true;
    }


    public void jump(int pixelsPerMeter, long fps) {
        if (System.currentTimeMillis() >= TIME_BETWEEN_JUMPS + jumpStop){
            if (spawnPoint.y <= location.y){
                location.y = spawnPoint.y;
                jumpStop = System.currentTimeMillis();
                velocity.y = MAX_JUMP_VELOCITY;
            }
            if (location.y + velocity.y/fps >= spawnPoint.y){
                location.y = spawnPoint.y;
            } else {
                location.y += (int)(velocity.y/fps);
                velocity.y -= GRAVITY;
            }
            location.x += velocity.x;
        }
    }


    public void update(int pixelsPerMeter, long fps, int gravity) {
        setPoints(location.x,location.y,pixelsPerMeter);
        setCenter();
        if (isDead && isActive){
            deathAnimation.update(center.x, center.y, size, fps);
        } else if (!isDead && !isBlocked && isActive){
            jump(pixelsPerMeter,fps);
        } else if (!isDead && isBlocked){
            attackAnimation(pixelsPerMeter,fps);
        } else if (isDead && !isActive){
            reset();
        }
    }


    private void attackAnimation(int pixelsPerMeter, long fps){
        if (location.y > spawnPoint.y - 2.5*pixelsPerMeter && !attacking){
            location.y += -6*pixelsPerMeter/fps;
        } else if (angleD <= 135){
            angleD += 135/(TIME_TO_ROTATE*fps);
            if (angleD >= 135){
                attacking = true;
            }
        } else if (attacking){
            location.y += (8*pixelsPerMeter)/fps;
            location.x += (4*pixelsPerMeter)/fps;
        }
    }


    private void jumpAndAttack(int pixelsPerMeter, long fps){

    }


    private void reset(){
    }

    public void draw(Canvas canvas, Paint paint){
        if (isActive && !isDead){
            paint.setColor(Color.argb(255,100,255,255));
            Path Triangle = new Path();
            Triangle.moveTo(A.x, A.y);
            Triangle.lineTo(B.x, B.y);
            Triangle.lineTo(C.x, C.y);
            Triangle.close();
            if (angleD != 0){
                canvas.save();
                canvas.rotate(angleD,center.x,center.y);
                canvas.drawPath(Triangle,paint);
                canvas.restore();
            } else {
                canvas.drawPath(Triangle,paint);
            }
        } else if (isDead && isActive){
            deathAnimation.draw(canvas, paint);
        }
    }


    private void setCenter(){
        center.set((float) (A.x + 0.5*size*pixelsPerMeter), (float) (A.y + 0.33*size*pixelsPerMeter));
    }


    public void destroy(){
        isDead = true;
    }
    public void takeDamage(float damage){
        health -= damage;
    }

    // Setter and Getter
    public void setVelocity(PointF velocity) { this.velocity = velocity;   }
    //public void setRotate(float rotate) { this.rotate = rotate; }
    public void setDamage(int damage) { this.damage = damage;   }
    public void setIsDead(boolean isDead) { this.isDead = isDead;   }


    public PointF getVelocity() { return velocity;  }
    //public float getRotate() {  return rotate;  }
    public boolean getIsDead() { return isDead; }
}
