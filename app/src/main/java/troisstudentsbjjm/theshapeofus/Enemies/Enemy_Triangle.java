package troisstudentsbjjm.theshapeofus.Enemies;




import android.graphics.Color;
import android.graphics.Path;

import android.graphics.PointF;

import troisstudentsbjjm.theshapeofus.Primatives.Triangle;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Jeffherson on 2017-05-15.
 */

public class Enemy_Triangle extends Triangle{

    public DeathAnimation deathAnimation;               //when it goes to heaven

    public PointF center;                               //center point
    public PointF velocity;                             //velocity in meters/second
    public final PointF spawnPoint;                     //reference to original position

    private int pixelsPerMeter;                         //temporary (hopefully)
    public int value = 2;                               //how much money you get from killing it

    public float angleD = 0;                            //angle to rotate on canvas
    public float damage = 40;                           //kamikaze, after dealing damage triangle will die
    public float health = 10;                           //added in constructor
    private final float GRAVITY = -10;                  //this will be in meters per second per second
    private final float MAX_JUMP_VELOCITY = -200;       //how fast the shape jumps

    public boolean attacking;                           //used to deal damage to tower
    public boolean facingRight;                         //coming from the left(facingRight) or the right(!facingRight)
    public boolean hit;
    public boolean inPositionToAttack;                  //self explanatory
    public boolean isBlocked;                           //if not blocked...move, if blocked... attack.
    public boolean isDead;                              //this will be used to initialize our death animation

    private long jumpStop = 0;                          //used as timer to implement TIME_BETWEEN_JUMPS
    private long timeHit = 0;
    private long attackTime = 0;
    private final long TIME_BETWEEN_JUMPS = 250;        //self explanatory == 0.5seconds
    private final double TIME_TO_ROTATE = 0.4;          //how long it will take triangle to rotate


    public Enemy_Triangle(int x,int y, double healthFactor, int pixelsPerMeter, int omniGonPosX, int omniGonPosY) {
        this.pixelsPerMeter = pixelsPerMeter;
        this.health *= healthFactor;

        size = (float) 0.5;

        location.set(x,y);
        setPoints(x,y,pixelsPerMeter);

        velocity = new PointF((float) 2.5,MAX_JUMP_VELOCITY);
        spawnPoint = new PointF(x,y);
        center = new PointF();

        deathAnimation = new DeathAnimation(pixelsPerMeter, location.y + pixelsPerMeter, omniGonPosX, omniGonPosY, 1);
        deathAnimation.setParticles(center.x, (float) (center.y - 0.5*size*pixelsPerMeter), (float) (0.5*size));
        deathAnimation.setColor(0,255,255);

        isBlocked = false;
        inPositionToAttack = false;
        isDead = false;
        attacking = false;
        facingRight = true;
    }


    public void jump(long fps) {
        if (System.currentTimeMillis() >= TIME_BETWEEN_JUMPS + jumpStop){
            angleD = 0;
            if (location.y == spawnPoint.y){
                location.y = spawnPoint.y;
                jumpStop = System.currentTimeMillis();
                velocity.y = MAX_JUMP_VELOCITY;
                velocity.x = (float) 2.5;
            }
            if (location.y + velocity.y/fps >= spawnPoint.y){
                location.y = spawnPoint.y;
            } else {
                location.y += velocity.y/fps;
                velocity.y -= GRAVITY;
            }
            location.x += velocity.x;
        }
    }


    public void update(int pixelsPerMeter, long fps) {
        if (isActive){
            checkIfDamaged();
            setPoints(location.x,location.y,pixelsPerMeter);
            setCenter();
            if (isDead){
                deathAnimation.update(center.x, center.y, size, fps);
            } else if (!isDead && !isBlocked && isActive){
                jump(fps);
            } else if (!isDead && isBlocked){
                attackAnimation(pixelsPerMeter,fps);
            }
        }
    }


    private void attackAnimation(int pixelsPerMeter, long fps){
        if (location.y > spawnPoint.y - 2.5*pixelsPerMeter && !attacking){
            location.y += -6*pixelsPerMeter/fps;
        } else if (angleD <= 135){
            angleD += 135/(TIME_TO_ROTATE*fps);
            if (angleD >= 135){
                attackTime = System.currentTimeMillis();
                attacking = true;
            }
        } else if (attacking){
            location.y += (8*pixelsPerMeter)/fps;
            location.x += (4*pixelsPerMeter)/fps;
            if (System.currentTimeMillis() >= attackTime + 1000 && !isDead){
                destroy();
            }
        }
    }


    public void draw(Canvas canvas, Paint paint){
        if (isActive && !isDead){
            paint.setColor(Color.argb(255,0,255,255));
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


    private void checkIfDamaged(){
        if (hit){
            timeHit = System.currentTimeMillis();
            hit = false;
        } else if (timeHit != 0 && System.currentTimeMillis() >= timeHit + 200){
            takeDamage(100);
            timeHit = 0;
        }
        if (health <= 0){
            destroy();
        }
    }


    private void setCenter(){ center.set((float) (A.x + 0.5*size*pixelsPerMeter), (float) (A.y + 0.33*size*pixelsPerMeter)); }
    public void destroy(){
        isDead = true;
    }
    public void takeDamage(float damage){
        health -= damage;
    }
}
