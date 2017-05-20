package troisstudentsbjjm.theshapeofus.Enemies;



import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

import java.util.Random;

import troisstudentsbjjm.theshapeofus.Primatives.Circle;

/**
 * Created by Jeffherson on 2017-05-15.
 */

public class Enemy_Circle extends Circle {
    public PointF CollisionPoint;
    private float rotate;
    private int damage;
    private float health;
    private float speed;
    public boolean isDead;
    float centerY, centerX,
            collisionY, collisionX,
            pixelsPerMeter;
    private boolean isBlocked;
    private boolean rolling;


    public Enemy_Circle(int x, int y, int health, int pixelsPerMeter) {
        this.health = health;
        updateSize();
        location.set(x, y);
        setHitBox(x,y,pixelsPerMeter);
        centerY = location.y + pixelsPerMeter - (float) (size*0.5)*pixelsPerMeter;
        centerX = location.x + pixelsPerMeter - (float) (size*0.5)*pixelsPerMeter;
        collisionY = centerY;
        collisionX = centerX;
        isDead = false;
        this.pixelsPerMeter = pixelsPerMeter;
        isBlocked = false;
        rolling = true;
        CollisionPoint = new PointF(collisionX,collisionY);
    }

    public void update(int pixelsPerMeter, long fps) {
        if (rolling) {
            if(isBlocked)   {
            } else {
                speed = ((float) pixelsPerMeter/fps);
                location.x += speed;
                CollisionPoint.x += speed;
            }

        }
    }

    public void update(Enemy_Circle Enemy) {
        if(hitBox.contains(Enemy.getCollisionPoint().x,Enemy.getCollisionPoint().y)) {
            BuildUp(Enemy);
            updateSize();
            
        }
    }

    public void BuildUp(Enemy_Circle Enemy) {
        setHealth(health + Enemy.getHealth());
    }

    private void updateSize() {
        setSize((float) (health * 0.025));
    }


    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255, 255, 255, 255));
        canvas.drawCircle(location.x, centerY, (float)(size*0.5*pixelsPerMeter), paint);
    }


    // Randomly returns either 1 or -1
    public int LeftorRight() {
        Random rand = new Random();
        int i = 0;
        if (isDead) {
            i = rand.nextInt(1);
            if (i != 0) {
                i = 1;
            } else {
                i = -1;
            }
        }
        return i;
    }


    // Getter
    public PointF getCollisionPoint() {  return CollisionPoint;  }
    public float getSpeed() {   return speed;   }

    // Setter
    public void setSpeed(float speed) { this.speed = speed;  }
    public void setHealth(float health) { this.health = health;   }
    public void setIsBlocked(boolean isBlocked) {   this.isBlocked = isBlocked; }


    public void destroy(){
        isDead = true;
    }


    public void takeDamage(float damage){
        health -= damage;
    }


    public float getHealth() { return health; }

    public boolean getIsDead() { return isDead;   }

}
