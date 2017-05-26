package troisstudentsbjjm.theshapeofus.Towers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Circle;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Square;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Triangle;
import troisstudentsbjjm.theshapeofus.Primatives.Circle;
import troisstudentsbjjm.theshapeofus.Primatives.Square;
import troisstudentsbjjm.theshapeofus.Primatives.Triangle;

/**
 * Created by mrber on 2017-05-15.
 */

public class Circle_Tower {

    private Circle circle;            // the tower will have a circle top
    private Triangle triangle;        // it will have a triangle bottom
    private Circle circleAccent;      //rotates around circle
    private Circle circleAccent2;
    private Circle bullet;            //the bullet

    private int pixelsPerMeter;
    private int range = 10;
    private int distToClosestEnemy;
//    private int damage = 10;
    private int bulletSpeed = 5;
    private final int CIRCLE_MAX_SIZE = 2;
    private float velocity = 0;
    private final float CIRCLE_MIN_SIZE = (float) 0.2;
    private float circleGrowRate;
    private float circleShrinkRate;
    private float AccentXpos;

    private boolean charging = false;
    private boolean readyToFire = true;
    private boolean bulletFired = false;

    private long fireTime;
    private long fireRate = 3000;
    private final double GROW_TIME = 0.8;
    private final double SHRINK_TIME = 2.2;               //seconds
    private final long TIME_TO_GROW = 800;
    private final long TIME_TO_SHRINK = 2200;               //seconds

    public PointF location;
    public PointF distance;

    public RectF targetingRange;


    public Circle_Tower(float x, float y, int pixelsPerMeter){
        this.pixelsPerMeter = pixelsPerMeter;
        location = new PointF(x,y);
        distance = new PointF();
        circle = new Circle();
        circle.center = new PointF();
        triangle = new Triangle();
        circleAccent = new Circle();
        circleAccent.center = new PointF();
        circleAccent2 = new Circle();
        circleAccent2.center = new PointF();
        bullet = new Circle();
        bullet.center = new PointF();
        targetingRange = new RectF((float) (x + 0.5*pixelsPerMeter) - range*pixelsPerMeter, y-2*pixelsPerMeter, (float) (x + 0.5*pixelsPerMeter) + range*pixelsPerMeter, y+2*pixelsPerMeter);
        initShapes(pixelsPerMeter);
        distToClosestEnemy = (range*pixelsPerMeter);
    }


    private void initShapes(int pixelsPerMeter){
        triangle.size = (float) 2;
        triangle.setPoints(location.x, location.y, pixelsPerMeter);
        circle.size = (float) CIRCLE_MAX_SIZE;
        circle.center.set(triangle.C.x, (float) (triangle.C.y - circle.size*0.3*pixelsPerMeter));
        circleAccent.size = (float) 0.4;
        circleAccent.center.set(circle.center.x,circle.center.y);
        circleAccent2.size = (float) 0.4;
        circleAccent2.center.set(circle.center.x,circle.center.y);
        bullet.size = CIRCLE_MIN_SIZE;
        bullet.center.set(circle.center.x,circle.center.y);
        AccentXpos = circleAccent.center.x;
    }


    public void update(ArrayList<Enemy_Circle> C_Enemies, ArrayList<Enemy_Square> S_Enemies, ArrayList<Enemy_Triangle> T_Enemies, long fps){
        update_C(C_Enemies, fps);
        update_S(S_Enemies, fps);
        update_T(T_Enemies, fps);
    }


    public void update_S(ArrayList<Enemy_Square> squares, long fps){
        updateCircleSize(fps);
        rotateAccent(fps);
        for (Enemy_Square Enemy : squares) {
            setDistToClosestEnemy(Enemy.center.x);
            if (!Enemy.rolling) {
                if (targetingRange.contains(Enemy.center.x, Enemy.center.y) && !Enemy.isDead) {
                    fire(Enemy, fps);
                }
            } else {
                if (targetingRange.contains(Enemy.center.x, Enemy.center.y) && !Enemy.isDead) {
                    fire(Enemy, fps);
                } else if (Enemy.angleD >= 45) {
                    if (targetingRange.contains(Enemy.location.x + Enemy.size * pixelsPerMeter, Enemy.center.y) && !Enemy.isDead) {
                        fire(Enemy, fps);
                    }
                }
            }

        }
    }


    private void fire(Enemy_Square Enemy, long fps){

        if (readyToFire) {
            if (Math.abs(circle.center.x - Enemy.center.x) <= distToClosestEnemy) {
                bulletFired = true;
                Enemy.hit = true;
                charging = true;
                readyToFire = false;
                fireTime = System.currentTimeMillis();
                distance.x = bullet.center.x - (Enemy.center.x);
                distance.y = bullet.center.y - (float) (Enemy.center.y - 0.5 * Enemy.size * pixelsPerMeter);
            }
        } else if (bulletFired) {
            bullet.center.x -= ((distance.x * bulletSpeed) / fps);
            bullet.center.y -= ((distance.y * bulletSpeed) / fps);
            if (System.currentTimeMillis() >= fireTime + 1000/bulletSpeed){
                bulletFired = false;
                bullet.center.set(circle.center.x, circle.center.y);
            }
        }
    }


    public void update_C(ArrayList<Enemy_Circle> circles, long fps){
        updateCircleSize(fps);
        rotateAccent(fps);
        for (Enemy_Circle Enemy : circles) {
                if (targetingRange.contains(Enemy.center.x, Enemy.center.y) && !Enemy.isDead) {
                    setDistToClosestEnemy(Enemy.center.x);
                    if (Math.abs(circle.center.x - Enemy.center.x) <= distToClosestEnemy) {
                    fire(Enemy, fps);
                }
            }
        }
    }


    private void fire(Enemy_Circle Enemy, long fps){

        if (readyToFire) {
            if (Math.abs(circle.center.x - Enemy.center.x) <= distToClosestEnemy) {
                bulletFired = true;
                Enemy.hit = true;
                charging = true;
                readyToFire = false;
                fireTime = System.currentTimeMillis();
                distance.x = bullet.center.x - Enemy.center.x;
                distance.y = bullet.center.y - Enemy.center.y;
            }
        } else if (bulletFired) {
            bullet.center.x -= ((distance.x * bulletSpeed) / fps);
            bullet.center.y -= ((distance.y * bulletSpeed) / fps);
            if (System.currentTimeMillis() >= fireTime + 1000/bulletSpeed){
                bulletFired = false;
                bullet.center.set(circle.center.x, circle.center.y);
            }
        }
    }


    public void update_T(ArrayList<Enemy_Triangle> triangles, long fps){
        updateCircleSize(fps);
        rotateAccent(fps);
        for (Enemy_Triangle Enemy : triangles) {
            if (targetingRange.contains(Enemy.center.x, Enemy.center.y) && !Enemy.isDead) {
                setDistToClosestEnemy(Enemy.center.x);
                if (Math.abs(circle.center.x - Enemy.center.x) <= distToClosestEnemy) {
                    fire(Enemy, fps);

                }
            }
        }
    }


    private void fire(Enemy_Triangle Enemy, long fps){

        if (readyToFire) {
            if (Math.abs(circle.center.x - Enemy.center.x) <= distToClosestEnemy) {
                bulletFired = true;
                Enemy.hit = true;
                charging = true;
                readyToFire = false;
                fireTime = System.currentTimeMillis();
                distance.x = bullet.center.x - Enemy.center.x;
                distance.y = bullet.center.y - Enemy.center.y;
            }
        } else if (bulletFired) {
            bullet.center.x -= (bulletSpeed*(distance.x / fps));
            bullet.center.y -= (bulletSpeed*(distance.y / fps));
            if (System.currentTimeMillis() >= fireTime + 1000/bulletSpeed){
                bulletFired = false;
                bullet.center.set(circle.center.x, circle.center.y);
            }
        }
    }


    private void updateCircleSize(long fps){

        circleShrinkRate = (float) ((CIRCLE_MAX_SIZE - CIRCLE_MIN_SIZE)/(3*SHRINK_TIME));                   // multiply by three because this is updated 3 times per frame
        circleGrowRate = (float) ((CIRCLE_MAX_SIZE - CIRCLE_MIN_SIZE)/(3*GROW_TIME));                       // same here, inefficient, but will do for now

        if (!readyToFire){
            if (System.currentTimeMillis() <= fireTime + TIME_TO_GROW){
                    if (circle.size < CIRCLE_MAX_SIZE){
                        velocity -= (Math.PI/(60*2.75*fps));
                        circle.size += circleGrowRate/fps;
                    } else if (velocity > 0){
                        velocity -= Math.PI/180;
                    } else {
                        velocity += Math.PI/180;
                    }
            } else if (System.currentTimeMillis() <= fireTime + TIME_TO_GROW + TIME_TO_SHRINK){
                    circle.size -= circleShrinkRate/fps;
                    velocity += (Math.PI/(60*fps));
                    if (circle.size <= CIRCLE_MIN_SIZE) {
                        circle.size = CIRCLE_MIN_SIZE;
                    }
            } else if (System.currentTimeMillis() >= fireTime + TIME_TO_GROW + TIME_TO_SHRINK){
                readyToFire = true;
            }
        }
    }


    private void rotateAccent(long fps){
        if (readyToFire && !bulletFired){
            if (velocity < -Math.PI){
                velocity += Math.PI/180;
            } else {
                velocity -= Math.PI/180;
            }
        }
        circleAccent.center.x = (float) (AccentXpos + (CIRCLE_MAX_SIZE*pixelsPerMeter*Math.sin(velocity/(0.1*circle.size))));
        circleAccent2.center.x = (float) (AccentXpos - (CIRCLE_MAX_SIZE*pixelsPerMeter*Math.sin(velocity/(0.1*circle.size))));
    }


    public void draw(Canvas canvas, Paint paint){

        paint.setColor(Color.argb(255,128,95,68));
        Path Triangle = new Path();
        Triangle.moveTo(triangle.A.x,triangle.A.y);
        Triangle.lineTo(triangle.B.x,triangle.B.y);
        Triangle.lineTo(triangle.C.x,triangle.C.y);
        Triangle.close();
        canvas.drawPath(Triangle,paint);
        paint.setColor(Color.argb(255,255,255,(int)((255/CIRCLE_MAX_SIZE)*circle.size)));
        canvas.drawCircle(circle.center.x,circle.center.y,(float) (circle.size*0.5*pixelsPerMeter),paint);
        paint.setColor(Color.argb(255,255,255,255));
        canvas.drawCircle(circleAccent.center.x,circleAccent.center.y,(float) (circleAccent.size*0.5*pixelsPerMeter),paint);
        canvas.drawCircle(circleAccent2.center.x,circleAccent2.center.y,(float) (circleAccent2.size*0.5*pixelsPerMeter),paint);
        if (bulletFired){
            canvas.drawCircle(bullet.center.x,bullet.center.y,(float) (bullet.size*0.5*pixelsPerMeter),paint);
        }
    }


    private void setDistToClosestEnemy(float x){
        if ( Math.abs(circle.center.x - x) < distToClosestEnemy || System.currentTimeMillis() > fireTime + fireRate + 1000){
            distToClosestEnemy = (int)Math.abs(circle.center.x - x) + 2;
        }
    }
}
