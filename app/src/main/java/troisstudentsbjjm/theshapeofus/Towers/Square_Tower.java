package troisstudentsbjjm.theshapeofus.Towers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.AbstractCollection;
import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Circle;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Square;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Triangle;
import troisstudentsbjjm.theshapeofus.Primatives.Square;

public class Square_Tower extends Square {
    public float health = 80;
    private int pixelsPerMeter;
    public int placementIndex;

    public int numBlocked = 0;


    public Square_Tower(int x, int y, int pixelsPerMeter, int towerIndex) {
        location.set(x,y);
        size = 1;
        this.pixelsPerMeter = pixelsPerMeter;
        this.placementIndex = towerIndex;
        setHitBox(x,y,pixelsPerMeter);
        isActive = true;
    }


    public void update(ArrayList<Enemy_Circle> C_Enemies, ArrayList<Enemy_Square> S_Enemies, ArrayList<Enemy_Triangle> T_Enemies, long fps) {
        update_C(C_Enemies, fps);
        update_S(S_Enemies, fps);
        update_T(T_Enemies, fps);
    }


    private void update_C(ArrayList<Enemy_Circle> C_Enemies, long fps) {
        for (Enemy_Circle Enemy : C_Enemies) {
            checkTowerHealth(Enemy);
            if (isActive) {
                if (Enemy.facingRight && isActive) {
                    if (!Enemy.isBlocked) {
                        if (((Enemy.center.x + Enemy.radius * pixelsPerMeter) + Enemy.velocityX / fps) >= hitBox.left && ((Enemy.center.x + Enemy.radius * pixelsPerMeter) + Enemy.velocityX / fps) < hitBox.right) {
                            Enemy.location.x += (hitBox.left - (Enemy.center.x + (0.5 * Enemy.size * pixelsPerMeter)));
                            Enemy.isBlocked = true;
                        }
                    } else if (Enemy.isDead && !Enemy.readyToExplode && Enemy.center.x + Enemy.size > hitBox.left && Enemy.center.x + Enemy.size < hitBox.right) {
                        health -= Enemy.damage * 0.5;
                    } else if (!Enemy.isDead && Enemy.readyToExplode && Enemy.center.x + Enemy.size > hitBox.left && Enemy.center.x + Enemy.size < hitBox.right) {
                        health -= Enemy.damage;
                        Enemy.destroy();
                    } else {
                        if (Enemy.center.x + Enemy.radius * pixelsPerMeter > hitBox.left && Enemy.center.x + Enemy.radius * pixelsPerMeter < hitBox.right) {
                            if (Enemy.location.x - (hitBox.left - Enemy.center.x + Enemy.radius * pixelsPerMeter) / fps < hitBox.left) {
                                Enemy.location.x = (hitBox.left - (Enemy.size * pixelsPerMeter));
                            } else {
                                Enemy.location.x -= (hitBox.left - Enemy.center.x + Enemy.radius * pixelsPerMeter) / fps;
                            }
                        }
                    }
                }
            }
        }
    }


    private void checkTowerHealth(Enemy_Circle Enemy){
        if (health <= 0){
            destroyTower();
            if (Enemy.isBlocked && !isActive){
                Enemy.isBlocked = false;
            }
        }
    }


    private void update_S(ArrayList<Enemy_Square> S_Enemies, long fps) {
        for (Enemy_Square Enemy : S_Enemies) {
            checkTowerHealth(Enemy);
            if (isActive) {
                if (Enemy.facingRight) {
                    if (!Enemy.rolling && !Enemy.isBlocked && Enemy.hitBox.bottom < location.y + pixelsPerMeter) {
                        if ((Enemy.hitBox.right + Enemy.velocity.x) >= hitBox.left) {
                            Enemy.location.x += (hitBox.left - Enemy.hitBox.right);
                            Enemy.velocity.x = 0;
                            if (Enemy.location.y + Enemy.velocity.y / fps >= Enemy.spawnPoint.y) {
                                Enemy.location.y = Enemy.spawnPoint.y;
                                Enemy.isBlocked = true;
                                Enemy.velocity.y = 0;
                            }
                        }
                    } else if (Enemy.rolling && !Enemy.isBlocked) {
                        if ((Enemy.hitBox.right + Enemy.size * pixelsPerMeter) >= hitBox.left) {
                            Enemy.rolling = false;
                        }
                    } else if (Enemy.isBlocked && Enemy.attacking) {
                        health -= Enemy.damage;
                        Enemy.attacking = false;
                    } else {
                        if (Enemy.hitBox.right > hitBox.left && Enemy.hitBox.right < hitBox.right) {
                            if (Enemy.location.x - (hitBox.left - Enemy.hitBox.right) / fps < hitBox.left) {
                                Enemy.location.x = (hitBox.left - (Enemy.size * pixelsPerMeter));
                            } else {
                                Enemy.location.x -= (hitBox.left - Enemy.hitBox.right) / fps;
                            }
                        }
                    }
                }
            }
        }
    }


    private void checkTowerHealth(Enemy_Square Enemy){
        if (health <= 0){
            destroyTower();
            if (Enemy.isBlocked && !isActive && (Enemy.hitBox.right+0.5*pixelsPerMeter > hitBox.left && Enemy.hitBox.right+0.5*pixelsPerMeter < hitBox.right)){
                Enemy.isBlocked = false;
            }
        }
    }


    private void update_T(ArrayList<Enemy_Triangle> T_Enemies, long fps){
        for (Enemy_Triangle Enemy : T_Enemies) {
            checkTowerHealth(Enemy);
            if (isActive) {
                if (Enemy.facingRight) {
                    if ((Enemy.A.x + pixelsPerMeter) >= hitBox.left && !Enemy.isBlocked && (Enemy.A.x + pixelsPerMeter) < hitBox.right) {
                        Enemy.location.x = (hitBox.left - pixelsPerMeter);
                        Enemy.velocity.x = 0;
                        if (Enemy.location.y + Enemy.velocity.y / fps >= Enemy.spawnPoint.y) {
                            Enemy.location.y = Enemy.spawnPoint.y;
                            Enemy.isBlocked = true;
                            Enemy.velocity.y = 0;
                        }
                    } else if (hitBox.contains(Enemy.center.x, Enemy.center.y) && !Enemy.isDead) {
                        health -= Enemy.damage;
                        Enemy.destroy();
                    }
                }
            }
        }
    }


    private void checkTowerHealth(Enemy_Triangle Enemy){
        if (health <= 0){
            destroyTower();
            if (Enemy.isBlocked && !isActive){
                if ((Enemy.A.x + 1.5*pixelsPerMeter) >= hitBox.left && (Enemy.A.x + pixelsPerMeter) < hitBox.right)
                Enemy.isBlocked = false;
            }
        }
    }


    private void destroyTower(){
        isActive = false;
    }


    public void draw(Canvas canvas, Paint paint){
        if (isActive){
            paint.setColor(Color.argb(255,68,133,255));
            canvas.drawRect(hitBox,paint);
        }
    }
}
