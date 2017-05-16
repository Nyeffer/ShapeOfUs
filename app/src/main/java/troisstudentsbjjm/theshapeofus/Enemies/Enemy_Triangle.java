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
}
