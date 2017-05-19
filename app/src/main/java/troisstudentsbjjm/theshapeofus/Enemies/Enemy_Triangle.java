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

    private boolean isDead;
    private boolean facingRight = true;



    private int damage;
    private int health;
    private int x;
    private int y;
    private int speed;
    private int directionX;
    private int directionY;
    private float gravity;
    public boolean isFalling;
    private boolean isJumping;
    private long jumpTime;
    private final long MAXJUMPTIME = 700;
    private float angle = 0;
    private float tempPostion;


    public Enemy_Triangle(int x,int y, int health, int pixelsPerMeter) {



        this.health = health;
        this.location.set(x,y);
        tempPostion = location.y;
        setPoints(pixelsPerMeter);
        isJumping = true;
        this.gravity = pixelsPerMeter;
        jumpTime = System.currentTimeMillis();

        isDead = false;
    }

    public void setupPivot (int x,int y, int pixelsPerMeter, Canvas canvas, Paint paint) {


    }

    public void draw(Canvas canvas, Paint paint){

        paint.setColor(Color.argb(255,100,255,255));

        Path Triangle = new Path();
        Triangle.moveTo(A.x, A.y);
        Triangle.lineTo(B.x, B.y);
        Triangle.lineTo(C.x, C.y);
        Triangle.close();
        canvas.drawPath(Triangle,paint);
        
    }

    public void jump(long fps) {


        if (angle >= Math.PI * 2) {

            angle = 0;
            tempPostion = location.y;
        }
        angle += Math.PI / fps;
        location.y = (float) (tempPostion - gravity * Math.abs(Math.sin(angle)));


    }


    public void update(float pixelsPerMeter, long fps, int gravity) {




        jump(fps);
        location.x+= pixelsPerMeter / fps;
        setPoints((int)pixelsPerMeter);



        //Log.d("Triangle", location.x + "");
        //location.x+= pixelsPerMeter / fps;
        //location.y-= pixelsPerMeter / fps;
        //setPoints((int)pixelsPerMeter);
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
