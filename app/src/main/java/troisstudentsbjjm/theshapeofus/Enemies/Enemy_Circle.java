package troisstudentsbjjm.theshapeofus.Enemies;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Input.InputController;
import troisstudentsbjjm.theshapeofus.Primatives.Circle;
import troisstudentsbjjm.theshapeofus.Primatives.Triangle;

public class Enemy_Circle extends Circle {

    private DeathAnimation deathAnimation;              //when it goes to heaven

    public ArrayList<Triangle> triangles;               //used for visual representation of timer
    public ArrayList<Path> paths;                       //used for drawing triangles

    public PointF center;                               //bottom center point
    public final PointF spawnPoint;                     //reference to original position

    private int pixelsPerMeter;                         //temporary (hopefully)
    private int tickCounter = 0;                        //counting ticks to detonation
    public int value = 2;                               //how much money you get from killing it
    private final int NUM_TRIANGLES = 6;                //number of triangles

    public float angleL = 0;                            //angle to rotate line on circle
    public float angleT = 30;                           //angle to rotate triangles on canvas
    public float damage;                                //based number of circles that have combined together
    public float health = 30;                           //added in constructor
    public float radius;                                //circle radius (size/2)
    public float velocityX;                             //movement on x axis
    private float healthPool = 0;                       //amount of health to add over time, so shape does not suddenly get bigger

    public boolean readyToExplode;                      //used to deal damage to tower
    public boolean combined;                            //used to delay timer if circles are combining;
    public boolean facingRight;                         //coming from the left(facingRight) or the right(!facingRight)
    public boolean hit;
    public boolean isBlocked;                           //if not blocked...move, if blocked... attack.
    public boolean isDead;                              //this will be used to initialize our death animation

    private long timeHit = 0;
    private long tickStartTime = 0;                     //used as timer to implement TIME_BETWEEN_TICKS
    private final long TIME_BETWEEN_TICKS = 500;        //self explanatory == 0.5seconds


    public Enemy_Circle(int x, int y, double healthFactor, int pixelsPerMeter, int omniGonPosX, int omniGonPosY) {
        this.pixelsPerMeter = pixelsPerMeter;
        this.health *= healthFactor;

        isBlocked = false;
        damage = 20;

        triangles = new ArrayList<>();
        paths = new ArrayList<>();

        location.set(x,y);

        initTriangles();
        setSize();

        spawnPoint = new PointF(x,y);
        center = new PointF((float) (x + 0.5*size*pixelsPerMeter), (float) ((y + pixelsPerMeter) - 0.5*size*pixelsPerMeter));

        setFacing(omniGonPosX);

        deathAnimation = new DeathAnimation(pixelsPerMeter, location.y + pixelsPerMeter, omniGonPosX, omniGonPosY, 3);
        deathAnimation.setParticles(center.x, center.y, size);
        deathAnimation.setColor(255,0,0);

        isBlocked = false;
        isDead = false;
    }


    public void update(Enemy_Circle Enemy ,int pixelsPerMeter, long fps) {
        if (isActive){
            combine(Enemy);
            updateCenter();
            setHealth(fps);
            if (isDead){
                deathAnimation.update(center.x, (float) (center.y - 0.5*size*pixelsPerMeter), size,fps);
            } else if (!isBlocked && isActive){
                setVelocityX();
                roll(fps);
            } else if (isBlocked){
                startTimer(pixelsPerMeter,fps);
            }
        }
    }


    public void draw(Canvas canvas, Paint paint) {
        if (!isDead && isActive){
            paint.setColor(Color.argb(255, 255, 0, 0));
            canvas.drawCircle(center.x, center.y, (float)(size*0.5*pixelsPerMeter), paint);
            if (!isBlocked){
                drawLine(canvas,paint);

            } else {
                drawLine(canvas,paint);
                drawTriangles(canvas,paint);
            }
        } else if (isDead && isActive){
            deathAnimation.draw(canvas, paint);
        }
    }


    private void setPaths(){
        for (int i = 0; i < NUM_TRIANGLES; i++){
            paths.add(i,new Path());
            paths.get(i).moveTo(triangles.get(i).A.x, triangles.get(i).A.y);
            paths.get(i).lineTo(triangles.get(i).B.x, triangles.get(i).B.y);
            paths.get(i).lineTo(triangles.get(i).C.x, triangles.get(i).C.y);
            paths.get(i).close();
        }
    }



    private void combine(Enemy_Circle Enemy){
        if (Enemy != null){
            if (!Enemy.isDead && Enemy.isActive && !isDead){
                if (Enemy.center.x < center.x + 0.5*size*pixelsPerMeter && Enemy.center.x > center.x - 0.5*size*pixelsPerMeter){
                    if (Enemy.health <= health){
                        healthPool += Enemy.health;
                        Enemy.health = 0;
                        Enemy.isDead = true;
                        Enemy.isActive = false;
                        combined = true;
                    } else if(Enemy.health > health){
                        Enemy.healthPool += health;
                        health = 0;
                        isDead =  true;
                        isActive = false;
                        Enemy.combined = true;
                    }
                }
            }
        }
    }



    private void drawTriangles(Canvas canvas, Paint paint){
        setPaths();
        paint.setColor(Color.argb(150, 0, 0, 0));
        for (int i = 0; i < tickCounter; i++){
            canvas.save();
            canvas.rotate(angleL+(angleT*(2*i+1)),center.x,center.y);
            canvas.drawPath(paths.get(i),paint);
            canvas.restore();

        }
    }


    private void drawLine(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(255, 0, 0, 0));
        paint.setStrokeWidth(3);
        canvas.save();
        canvas.rotate(angleL,center.x,center.y);
        canvas.drawLine(center.x,center.y,center.x,(float) (center.y + 0.5*size*pixelsPerMeter),paint);
        canvas.restore();
    }


    private void initTriangles(){
        for (int i = 0; i < NUM_TRIANGLES; i++){
            triangles.add(i, new Triangle());
        }
    }


    private void setTriangles(){
        for (Triangle triangle : triangles){
            triangle.size = (float) (0.4*size);
            triangle.C.set(center.x,center.y);
            triangle.A.set((float) (center.x - 0.5*triangle.size*pixelsPerMeter), center.y + triangle.size*pixelsPerMeter);
            triangle.B.set((float) (center.x + 0.5*triangle.size*pixelsPerMeter), triangle.A.y);
        }
    }


    public void startTimer(int pixelsPerMeter, long fps){
        setTriangles();
        if (System.currentTimeMillis() >= tickStartTime + TIME_BETWEEN_TICKS){
            tickStartTime = System.currentTimeMillis();
            tickCounter += 1;
            Log.d("tickCounter", "startTimer: ");
            if (combined){
                tickCounter = 0;
                combined = false;
            }
            if (tickCounter == 7){
                readyToExplode = true;
            } else if (tickCounter > 7){
                destroy();
            }
        }
    }


    public void roll(long fps){
        location.x += velocityX/fps;
        angleL += (180)/(fps*size);
    }


    private void setHealth(long fps){
        checkIfDamaged();
        health += healthPool/fps;
        healthPool -= healthPool/fps;
        if (healthPool - healthPool/fps < 0){
            healthPool = 0;
        }
        if (health <= 0){
            destroy();
        }
        setSize();
    }


    public void setFacing(int omniGonPosX) {
        if (spawnPoint.x < omniGonPosX){
            facingRight = true;
        } else {
            facingRight = false;
        }
    }


    private void setSize() {
        float tempSize;
        tempSize = (float) (health*0.025);
        if (tempSize > size) {
            size = tempSize;
            radius = (float) (size*0.5);
        }
        setDamage();
    }


    private void setDamage(){
        if (combined){
            damage += 20;
        }
        if (damage < 40){
            damage = 40;
        } else if (damage > 80){
            damage = 80;
        }
    }


    private void setVelocityX(){
        velocityX = (float) (Math.PI * (1/(2*size)) * pixelsPerMeter);
    }


    private void updateCenter() {
        center.set((float) (location.x + 0.5*size*pixelsPerMeter), (float) ((location.y + pixelsPerMeter) - 0.5*size*pixelsPerMeter));
    }


    public void reset(){

    }

    public void takeDamage(float damage){
        health -= damage;
    }

    public void destroy(){
        isDead = true;
        velocityX = 0;
    }


    private void checkIfDamaged(){
        if (hit){
            timeHit = System.currentTimeMillis();
            hit = false;
        } else if (timeHit != 0 && System.currentTimeMillis() >= timeHit + 200){
            takeDamage(20);
            timeHit = 0;
        }
    }
}
