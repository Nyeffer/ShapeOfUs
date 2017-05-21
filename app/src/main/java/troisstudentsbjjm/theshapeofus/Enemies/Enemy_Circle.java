package troisstudentsbjjm.theshapeofus.Enemies;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Size;

import java.util.ArrayList;
import troisstudentsbjjm.theshapeofus.DeathAnimation;
import troisstudentsbjjm.theshapeofus.Primatives.Circle;
import troisstudentsbjjm.theshapeofus.Primatives.Triangle;

public class Enemy_Circle extends Circle {

    private DeathAnimation deathAnimation;              //when it goes to heaven

    public ArrayList<Triangle> triangles;               //used for visual representation of timer
    public ArrayList<Path> paths;                       //used for drawing triangles

    public PointF center;                               //bottom center point
    public PointF spawnPoint;                           //reference to original position

    private int pixelsPerMeter;                         //temporary (hopefully)
    private int tickCounter = 0;                        //counting ticks to detonation
    private final int NUM_TRIANGLES = 6;                //number of triangles

    public float angleL = 0;                            //angle to rotate line on circle
    public float angleT = 30;                           //angle to rotate triangles on canvas
    public float damage = 100;                          //based on size, bigger == tons of damage
    public float health;                                //added in constructor
    public float velocityX;                             //movement on x axis
    private float healthPool = 0;                       //amount of health to add over time, so shape does not suddenly get bigger

    public boolean readyToExplode;                      //used to deal damage to tower
    public boolean facingRight;                         //coming from the left(facingRight) or the right(!facingRight)
    public boolean isBlocked;                           //if not blocked...move, if blocked... attack.
    public boolean isDead;                              //this will be used to initialize our death animation

    private long tickStartTime = 0;                     //used as timer to implement TIME_BETWEEN_TICKS
    private final long TIME_BETWEEN_TICKS = 500;        //self explanatory == 0.5seconds


    public Enemy_Circle(int x,int y, int health, int pixelsPerMeter, int omniGonPosX, int omniGonPosY) {
        this.health = health;
        this.pixelsPerMeter = pixelsPerMeter;

        isBlocked = false;
        rolling = true;
        CollisionPoint = new PointF(centerX,centerY);
        offset = health*6;
        squareTower = new Square_Tower(x,y, pixelsPerMeter);
        damage = 100;


        triangles = new ArrayList<>();
        paths = new ArrayList<>();

        location.set(x,y);

        initTriangles();
        updateSize();

        spawnPoint = new PointF(x,y);
        center = new PointF((float) (x + 0.5*size*pixelsPerMeter), (float) ((y + pixelsPerMeter) - 0.5*size*pixelsPerMeter));

        setFacing(omniGonPosX);

        deathAnimation = new DeathAnimation(pixelsPerMeter, location.y + pixelsPerMeter, omniGonPosX, omniGonPosY, 3);
        deathAnimation.setParticles(center.x, center.y, size);

        isBlocked = false;
        isDead = false;
        isActive = true;
    }

    public void update(int pixelsPerMeter, long fps) {

        if (rolling) {
            Log.d("EC", isBlocked + " ");
            if(isBlocked)   {
                location.x += 0;

        updateCenter();
        updateHealth(fps);
        if (isDead && isActive){
            deathAnimation.update(center.x, (float) (center.y - 0.5*size*pixelsPerMeter), size,fps);
        } else if (!isDead && !isBlocked && isActive){
            setVelocityX();
            roll(fps);
        } else if (!isDead && isBlocked){
            startTimer(pixelsPerMeter,fps);
        } else if (isDead && !isActive){
            reset();
        }
    }


    public void draw(Canvas canvas, Paint paint) {
        if (!isDead){
            paint.setColor(Color.argb(255, 255, 255, 255));
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


    public void isCollided(Enemy_Circle Enemy) {
        if(hitBox.contains(Enemy.getCollisionPoint().x - health*5, Enemy.getCollisionPoint().y)) {
            BuildUp(Enemy);
//            Log.d("EC", squareTower.counter + " ");


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



    public void BuildUp(Enemy_Circle Enemy) {
        if(counter == 1) {
            Enemy.destroy();
            setHealth(health + Enemy.getHealth());
            maxHealth = maxHealth + Enemy.getHealth();
            updateSize();
            updateCenter();
//            squareTower.setCounter(0);
            isBlocked = false;
            counter++;
            currentCounter++;
            Detonate(3);
        }
    }

    public void Detonate(int countersTillDetonation) {
        if(countersTillDetonation == currentCounter) {
            if(location.y == health) {
                location.y++;
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
            if (tickCounter == 7){
                readyToExplode = true;
            }
        }
    }


    public void roll(long fps){
        location.x += velocityX/fps;
        angleL += (180)/(fps*size);
    }


    private void updateHealth(long fps){
        health += healthPool/fps;
        healthPool -= healthPool/fps;
        if (healthPool - healthPool/fps < 0){
            healthPool = 0;
        }
        updateSize();
    }


    public void setFacing(int omniGonPosX) {
        if (spawnPoint.x < omniGonPosX){
            facingRight = true;
        } else {
            facingRight = false;
        }
    }


    private void updateSize() {
        float tempSize;
        tempSize = (float) (health*0.025);
        if (tempSize > size){
            size = tempSize;
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


    // Setter
    public void setSpeed(float speed) { this.speed = speed;  }
    public void setHealth(float health) { this.health = (int)(health);   }
    public void setIsBlocked(boolean isBlocked) {   this.isBlocked = isBlocked; }
    public void setIsDead(boolean isDead) { this.isDead = isDead;   }
    public void setCollisionPoint(PointF collisionPoint) { this.CollisionPoint = collisionPoint;}
    public void setCountersTillDetonation(int countersTillDetonation) { this.countersTillDetonation = countersTillDetonation;   }
    public void setSquareTower(Square_Tower squareTower) { this.squareTower = squareTower;  }


    public void takeDamage(float damage){
        health -= damage;
    }

    public int getHealth() { return health;    }
    public boolean getIsDead() { return isDead;   }
    public boolean getIsBlocked() { return isBlocked;   }



    public void destroy(){
        isDead = true;
        velocityX = 0;
    }
}
