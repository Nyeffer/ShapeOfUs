package troisstudentsbjjm.theshapeofus.Enemies;


import android.graphics.Point;
import android.graphics.PointF;

import java.util.Random;

import troisstudentsbjjm.theshapeofus.Primatives.Triangle;

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
    private int gravity;


    Enemy_Triangle() {

        isDead = false;
        Random rand = new Random();
        directionX = rand.nextInt(5);
        directionY = -rand.nextInt(10);
        speed = rand.nextInt(7);
        gravity = 7;
    }


    public void update(int pixelsPerMeter, long fps) {


    }

    public void deathAnim() {
        if(isDead) {
            // Draw the death sprite here
        }
    }










}
