package troisstudentsbjjm.theshapeofus.Enemies;



import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

import java.util.Random;

import troisstudentsbjjm.theshapeofus.Primatives.Circle;
import troisstudentsbjjm.theshapeofus.Primatives.GameObject;
import troisstudentsbjjm.theshapeofus.Primatives.Square;
import troisstudentsbjjm.theshapeofus.Towers.Square_Tower;

/**
 * Created by Jeffherson on 2017-05-15.
 */

public class Enemy_Circle extends Circle {
    public PointF CollisionPoint;
    private float rotate;
    private int damage;
    private int health, maxHealth;
    private float speed;
    private boolean isDead;
    float centerY, centerX,
            collisionY, collisionX,
            pixelsPerMeter;
    private boolean isBlocked;
    private boolean rolling;
    int counter = 1;
    private int offset;
    Square_Tower squareTower;
    private int countersTillDetonation;
    private int currentCounter;




    public Enemy_Circle(int x, int y, int health, int pixelsPerMeter) {
        this.health = health;
        maxHealth = health;
        updateSize();
        location.set(x, y);
        setHitBox(x,y,pixelsPerMeter);
        centerY = location.y + pixelsPerMeter - (float) (size*0.5)*pixelsPerMeter;
        centerX = location.x + pixelsPerMeter - (float) (size*0.5)*pixelsPerMeter;
        isDead = false;
        this.pixelsPerMeter = pixelsPerMeter;
        isBlocked = false;
        rolling = true;
        CollisionPoint = new PointF(centerX,centerY);
        offset = health*6;
        squareTower = new Square_Tower(x, y, health, pixelsPerMeter);
        damage = 100;


    }

    public void update(int pixelsPerMeter, long fps) {
        if (rolling) {
            if(isBlocked)   {
                location.x += 0;
            } else {
                speed = ((float) pixelsPerMeter/fps);
                location.x += speed;
                CollisionPoint.x += speed;
            }

        }
    }

    public void isCollided(Enemy_Circle Enemy) {
        if(hitBox.contains(Enemy.getCollisionPoint().x - offset, Enemy.getCollisionPoint().y)) {
            BuildUp(Enemy);
            Log.d("EC", squareTower.counter + " ");
        }
    }


    public void BuildUp(Enemy_Circle Enemy) {
        if(counter == 1) {
            Enemy.destroy();
            setHealth(health + Enemy.getHealth());
            maxHealth = maxHealth + Enemy.getHealth();
            updateSize();
            updateCenter();
            squareTower.setCounter(0);
            isBlocked = false;
            counter++;
            currentCounter++;
        }
    }

    public void Detonate(int countersTillDetonation) {
        if(countersTillDetonation == currentCounter) {
            
        }
    }

    private void updateSize() {
        Log.d("updateSize", getHealth() + " ");
        setSize((float) (health * 0.025));
    }

    private void updateCenter() {
        centerY = location.y + pixelsPerMeter - (float)(size*0.5*pixelsPerMeter);
        centerX = location.x + pixelsPerMeter - (float)(size*0.25*pixelsPerMeter);
        setCollisionPoint(CollisionPoint = new PointF(centerX, centerY));

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
    public int getHealth() {    return health;  }

    // Setter
    public void setSpeed(float speed) { this.speed = speed;  }
    public void setHealth(int health) { this.health = health;   }
    public void setIsBlocked(boolean isBlocked) {   this.isBlocked = isBlocked; }
    public void setIsDead(boolean isDead) { this.isDead = isDead;   }
    public void setCollisionPoint(PointF collisionPoint) { this.CollisionPoint = collisionPoint;}
    public void setCountersTillDetonation(int countersTillDetonation) { this.countersTillDetonation = countersTillDetonation;   }
    public void setSquareTower(Square_Tower squareTower) { this.squareTower = squareTower;  }

    public void destroy(){  setIsDead(true);    }
    public void takeDamage(float damage){
        health -= damage;
    }
    public boolean getIsDead() { return isDead;   }
    public boolean getIsBlocked() { return isBlocked;   }

}
