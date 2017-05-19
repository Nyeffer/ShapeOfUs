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
    private PointF velocity;
    private float rotate;
    private int damage,
            health;
    private boolean isDead;
    float center,
            pixelsPerMeter;
    private boolean isBlocked;
    private boolean rolling;


    public Enemy_Circle(int x, int y, int health, int pixelsPerMeter) {
        this.health = health;
        updateSize();
        location.set(x, y);
        center = location.y + pixelsPerMeter - (float) (size*0.5)*pixelsPerMeter;
        isDead = false;
        this.pixelsPerMeter = pixelsPerMeter;
        isBlocked = false;
        rolling = true;
    }

    public void update(int pixelsPerMeter, long fps) {
        if (rolling) {
            this.location.x += ((float)pixelsPerMeter/fps);
        }
    }

    private void updateSize() {
        setSize((float) (health * 0.025));
    }


    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255, 255, 255, 255));
        canvas.drawCircle(location.x, center, (float)(size*0.5*pixelsPerMeter), paint);
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


    public void destroy(){
        isDead = true;
    }


    public void takeDamage(float damage){
        health -= damage;
    }


    public float getHealth() { return health; }
    public boolean getIsDead() { return isDead;   }
}
