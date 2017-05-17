package troisstudentsbjjm.theshapeofus.Enemies;



import android.graphics.PointF;

import java.util.Random;

/**
 * Created by Jeffherson on 2017-05-15.
 */

public class Enemy_Triangle {
    private PointF velocity;
    private float rotate;
    private int damage,
            health;
    private boolean isDead;
    private int x, y,
            speed,
            directionX, directionY,
            gravity;
    private boolean isBlocked;


    public Enemy_Triangle(int x,int y) {
        isDead = false;
        Random rand = new Random();
        directionX = rand.nextInt(5);
        directionY = -rand.nextInt(10);
        speed = rand.nextInt(7);
        gravity = 7;
        isBlocked = false;
    }

    public void update(int spawnPointX, int spawnPointY) {
        x = spawnPointX + speed;


        if(isBlocked) {
            speed = 0;
            // BuildUp();
        }

    }

    public void deathAnim() {
        if(isDead) {
            // Draw the death sprite here
            for(int i = 0; i < directionY; i++) {
                y--; // Cause the sprite to go up
                for(int j = 0; j < directionX; j++) {
                    // check if it's left or right
                    if(LeftorRight() == 1) {
                        x++; // Go to the right
                    }   else    {
                        x--; // Go to the left
                    }
                }
            }
        }
    }



    public int LeftorRight() {
        Random rand = new Random();
        int i = 0;
        if(isDead) {
            i = rand.nextInt(1);
            if (i != 0){
                i = 1;
            }   else {
                i = -1;
            }
        }
        return i;
    }



    // Setter and Getter
    public void setVelocity(PointF velocity) { this.velocity = velocity;   }
    public void setRotate(float rotate) { this.rotate = rotate; }
    public void setDamage(int damage) { this.damage = damage;   }
    public void setHealth(int health) { this.health = health;   }
    public void setIsDead(boolean isDead) { this.isDead = isDead;   }

    public PointF getVelocity() { return velocity;  }
    public float getRotate() {  return rotate;  }
    public int getDamage() { return damage; }
    public int getHealth() { return health; }
    public boolean getIsDead() { return isDead; }
}
