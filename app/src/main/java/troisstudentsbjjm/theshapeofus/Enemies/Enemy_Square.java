package troisstudentsbjjm.theshapeofus.Enemies;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import troisstudentsbjjm.theshapeofus.DeathAnimation;
import troisstudentsbjjm.theshapeofus.Primatives.Square;

public class Enemy_Square extends Square{

    public DeathAnimation deathAnimation;

    public PointF center;                               //bottom center point
    public PointF pivot;                                //position used to rotate square on canvas
    public PointF spawnPoint;                           //reference to original position
    public PointF velocity;                             //velocity in meters/second

    private int pixelsPerMeter;                         //temporary (hopefully)

    public float angleD = 0;                            //angle to rotate square on canvas
    public float damage;                                //based on size, bigger == tons of damage
    private float angularVelocity;                      //angular velocity in degrees per second
    private float health;                               //added in constructor
    private float healthPool = 0;                       //amount of health to add over time, so shape does not suddenly get bigger
    private final float GRAVITY = -10;                  //this will be in meters per second per second
    private final float MAX_JUMP_VELOCITY = -200;       //how fast the shape jumps

    public boolean attacking;                           //used to deal damage to tower
    public boolean facingRight;                         //coming from the left(facingRight) or the right(!facingRight)
    public boolean isBlocked;                           //if not blocked...move, if blocked... attack.
    public boolean isDead;                              //this will be used to initialize our death animation
    public boolean rolling;                             //the shape is either rolling or jumping

    private long jumpStop = 0;                          //used as timer to implement TIME_BETWEEN_JUMPS
    private long attackTime = 0;                        //used as timer to implement TIME_BETWEEN_ATTACKS
    private final long TIME_BETWEEN_ATTACKS = 2000;     //self explanatory == 2seconds
    private final long TIME_BETWEEN_JUMPS = 500;        //self explanatory == 0.5seconds


    public Enemy_Square(int x,int y, int health, int pixelsPerMeter, int omniGonPosX, int omniGonPosY) {
        this.health = health;
        this.pixelsPerMeter = pixelsPerMeter;

        location.set(x,y);

        updateSize();
        setHitBox(x,y,pixelsPerMeter);
        setRolling();

        pivot = new PointF();
        velocity = new PointF();
        spawnPoint = new PointF(x,y);
        center = new PointF((float) (hitBox.left+0.5*size), hitBox.bottom);

        deathAnimation = new DeathAnimation(pixelsPerMeter, location.y + pixelsPerMeter, omniGonPosX, omniGonPosY);
        deathAnimation.setParticles(center.x, (float) (center.y - 0.5*size*pixelsPerMeter), size);

        isBlocked = false;
        isDead = false;
        isActive = true;
        attacking = true;
        facingRight = true;
    }


    public void update(Enemy_Square Enemy, int pixelsPerMeter, long fps) {
        combine(Enemy);
        updateHealth(fps);
        center.set((float) (hitBox.left+0.5*size), hitBox.bottom);
        if (isDead && isActive){
            deathAnimation.update(center.x, (float) (center.y - 0.5*size*pixelsPerMeter), size,fps);
        } else if (!isDead && !isBlocked && isActive){
            if (rolling){
                angularVelocity = 60;
                roll(pixelsPerMeter,fps);
            } else {
                jump(pixelsPerMeter,fps);
            }
        } else if (!isDead && isBlocked){
            attackAnimation(pixelsPerMeter,fps);
        } else if (isDead && !isActive){
            reset();
        }
    }


    private void roll(int pixelsPerMeter, long fps){
        if (facingRight){
            if (angleD >= 87){
                Move(pixelsPerMeter);
                angleD = 0;
            } else if (angleD > 45){
                angleD += angularVelocity*2/(fps*size);
            } else {
                angleD += angularVelocity/(fps*size);
            }
        } else if (!facingRight){
            if (angleD <= -87){
                Move(pixelsPerMeter);
                angleD = 0;
            } else if (angleD < -45){
                angleD -= angularVelocity*2/fps;
            } else {
                angleD -= angularVelocity/fps;
            }
        }
        setPivot();
    }


    private void combine(Enemy_Square Enemy){
        if (hitBox.contains(Enemy.center.x, Enemy.center.y)){
            if (Enemy.health < health){
                healthPool += Enemy.health;
                Enemy.health = 0;
                Enemy.isActive = false;
            } else if(Enemy.health > health){
                Enemy.healthPool += health;
                health = 0;
                isActive = false;
            }
        }
    }


    private void attackAnimation(int pixelsPerMeter, long fps){
        updateSize();
        if (System.currentTimeMillis() >= TIME_BETWEEN_ATTACKS + jumpStop){
            if (spawnPoint.y <= location.y){
                velocity.y = MAX_JUMP_VELOCITY*size;
                location.y = spawnPoint.y;
                jumpStop = System.currentTimeMillis();
                if (System.currentTimeMillis() >= TIME_BETWEEN_ATTACKS + attackTime){
                    attacking = true;
                    attackTime = System.currentTimeMillis();
                }
            }
            if (location.y + velocity.y/fps >= spawnPoint.y){
                location.y = spawnPoint.y;
                setPivot();
                setRolling();
            } else {
                location.y += (int)(velocity.y/fps);
                velocity.y -= GRAVITY*2;
            }
            location.x += velocity.x;
            center.set((float) (hitBox.left+0.5*size), hitBox.bottom);
        }
    }


    private void reset(){

    }


    private void jump(int pixelsPerMeter, long fps){
        if (System.currentTimeMillis() >= TIME_BETWEEN_JUMPS + jumpStop){
            if (spawnPoint.y <= location.y){
                velocity.set(2,MAX_JUMP_VELOCITY*size);
                location.y = spawnPoint.y;
                jumpStop = System.currentTimeMillis();
            }
            if (location.y + velocity.y/fps >= spawnPoint.y){
                location.y = spawnPoint.y;
                setPivot();
                setRolling();
            } else {
                location.y += (int)(velocity.y/fps);
                velocity.y -= GRAVITY;
            }
            location.x += velocity.x;
        }
    }


    public void updateSize(){
        if (!isBlocked){
            if (health * 0.025 >= 0.5){
                size = ((float) (health * 0.025));
                damage = health/10;
                if (size > 2){
                    size = 2;
                }
            } else {
                setSize((float) 0.5);
                damage = 1;
            }
        } else {
            if (hitBox.right - (hitBox.left -1) <= (health * 0.025)*pixelsPerMeter && hitBox.right - (hitBox.left -1) <= 4*pixelsPerMeter){
                location.x--;
                size += 0.02;
            } else if (hitBox.right - (hitBox.left +1) >= (health * 0.025)*pixelsPerMeter && hitBox.right - (hitBox.left +1) >= 0.5*pixelsPerMeter){
                location.x++;
                size -= 0.02;
            }
        }
    }


    private void updateHealth(long fps){
        health += healthPool/fps;
        healthPool -= healthPool/fps;
        if (healthPool - healthPool/fps < 0){
            healthPool = 0;
        }
        updateSize();
        setHitBox(location.x,location.y,pixelsPerMeter);
    }


    public void setPivot(){
        if (angleD < 0){
            pivot.set(hitBox.left, hitBox.bottom);
        } else {
            pivot.set(hitBox.right, hitBox.bottom);
        }
    }


    public void Move(int pixelsPerMeter){
        if (facingRight){
            location.x += size*pixelsPerMeter;

        } else {
            location.x -= size*pixelsPerMeter;

        }
        updateSize();
        setHitBox(location.x,location.y,pixelsPerMeter);
        setRolling();
    }


    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(255,255,255,255));
        if (!isDead && isActive){
            if (rolling){
                canvas.save();
                canvas.rotate(angleD,pivot.x,pivot.y);
                canvas.drawRect(hitBox,paint);
                canvas.restore();
            } else {
                canvas.drawRect(hitBox,paint);
            }
        } else if (isDead && isActive){
            deathAnimation.draw(canvas, paint);
        }
    }


    public void destroy(){
        isDead = true;
        angularVelocity = 0;
        velocity.set(0,0);
    }


    public void setRolling(){
        if (health <= 40){
            rolling = false;
        } else {
            rolling = true;
        }
    }


    public void takeDamage(float damage){
        health -= damage;
    }
}
