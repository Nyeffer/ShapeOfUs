package troisstudentsbjjm.theshapeofus.Enemies;




import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;

import android.graphics.PointF;

import java.util.Random;

import troisstudentsbjjm.theshapeofus.Primatives.Triangle;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by Jeffherson on 2017-05-15.
 */

public class Enemy_Triangle extends Triangle{

    private PointF velocity;

    private float angleD = 0;           //angular velocity in degrees per second

    public boolean isDead;
    private boolean facingRight = true;



    private int damage;
    private int health;
    private int x;
    private int y;
    private int speed;
    private final float GRAVITY = -10;
    public boolean isFalling;
    private boolean isJumping;
    private long jumpTime;
    private final long MAXJUMPTIME = 700;
    private final long MAX_JUMP_VELOCITY = -200;
    private float angle = 0;
    private float tempPostion;
    private final long TIME_BETWEEN_JUMPS = 500;
    private long jumpStop = 0;
    private float tempYpos;


    public Enemy_Triangle(int x,int y, int health, int pixelsPerMeter) {

        this.health = health;
        tempPostion = location.y;
        velocity = new PointF();
        location.set(x,y);
        updateSize();
        setPoints((int)location.x, (int)location.y, pixelsPerMeter);
        isDead = false;
        tempYpos = location.y + 1;
    }


//    public void setupPivot (int x,int y, int pixelsPerMeter, Canvas canvas, Paint paint) {
//    }


    public void draw(Canvas canvas, Paint paint){

        paint.setColor(Color.argb(255,100,255,255));

        Path Triangle = new Path();
        Triangle.moveTo(A.x, A.y);
        Triangle.lineTo(B.x, B.y);
        Triangle.lineTo(C.x, C.y);
        Triangle.close();
        canvas.drawPath(Triangle,paint);
        
    }


    public void jump(int pixelsPerMeter, long fps) {
        updateSize();
        if(System.currentTimeMillis() >= TIME_BETWEEN_JUMPS + jumpStop) {
            if(tempYpos <= location.y){
                velocity.set(1, MAX_JUMP_VELOCITY);
                location.y = tempYpos;
                jumpStop = System.currentTimeMillis();
            }
            if(location.y + velocity.y/fps >= tempYpos) {
                location.y = tempYpos;
            }   else    {
                location.y += velocity.y/fps;
                velocity.y -= GRAVITY;
            }
            location.x += velocity.x;
        }
    }





    private void updateSize(){setSize ((float) (health * 0.05));}



    public void takeDamage(float damage){
        health -= damage;
    }

    public void update(float pixelsPerMeter, long fps, int gravity) {



//    public void destroy()   {
//        isDead = true;
//    }





        //Log.d("Triangle", location.x + "");
        //location.x+= pixelsPerMeter / fps;
        //location.y-= pixelsPerMeter / fps;
        //setPoints((int)pixelsPerMeter);
        jump((int) pixelsPerMeter,fps);
        location.x+= pixelsPerMeter / fps;
        setPoints((int)(location.x),(int)(location.y),(int)pixelsPerMeter);

    }


    public void destroy(){
        isDead = true;
    }

    // Setter and Getter
    public void setVelocity(PointF velocity) { this.velocity = velocity;   }
    //public void setRotate(float rotate) { this.rotate = rotate; }
    public void setDamage(int damage) { this.damage = damage;   }
    public void setHealth(int health) { this.health = health;   }
    public void setIsDead(boolean isDead) { this.isDead = isDead;   }


    public PointF getVelocity() { return velocity;  }
    //public float getRotate() {  return rotate;  }
    public int getDamage() { return damage; }
    public int getHealth() { return health; }
    public boolean getIsDead() { return isDead; }
}
