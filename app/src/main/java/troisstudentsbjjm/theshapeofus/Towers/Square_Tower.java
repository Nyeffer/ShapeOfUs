package troisstudentsbjjm.theshapeofus.Towers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Circle;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Square;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Triangle;
import troisstudentsbjjm.theshapeofus.Primatives.Square;

public class Square_Tower extends Square {
    private int health = 80;
    private int pixelsPerMeter;

    public int numBlocked = 0;
    public boolean isAdjustmentDone;

    public Square_Tower(int x, int y, int pixelsPerMeter) {
        location.set(x,y);
        size = 1;
        this.pixelsPerMeter = pixelsPerMeter;
        setHitBox(x,y,pixelsPerMeter);
        isAdjustmentDone = false;
        isActive = true;
    }


    public void update(Enemy_Circle Enemy, long fps) {

        checkTowerHealth(Enemy);
        if (Enemy.facingRight && isActive){
            if (!Enemy.isBlocked){
                if (((Enemy.center.x+0.5*size*pixelsPerMeter) + Enemy.velocityX/fps) >= hitBox.left){
                    Enemy.location.x += (hitBox.left - (Enemy.center.x+(0.5*Enemy.size*pixelsPerMeter)));
                    Enemy.isBlocked = true;
                    numBlocked++;
                }
            } else  if (Enemy.isBlocked && Enemy.isDead && !Enemy.readyToExplode){
                health -= Enemy.damage*0.5;
                Enemy.isBlocked = false;
                numBlocked--;
            } else if (Enemy.isBlocked && !Enemy.isDead && Enemy.readyToExplode){
                health -= Enemy.damage;
                Enemy.destroy();
            } else {
                if (Enemy.center.x + Enemy.radius*pixelsPerMeter > hitBox.left){
                    if ( Enemy.location.x -(hitBox.left - Enemy.center.x + Enemy.radius*pixelsPerMeter)/fps < hitBox.left){
                        Enemy.location.x = (hitBox.left - (Enemy.size*pixelsPerMeter));
                    } else {
                        Enemy.location.x -= (hitBox.left - Enemy.center.x + Enemy.radius*pixelsPerMeter)/fps;
                    }
                }
            }
        }
    }


    private void checkTowerHealth(Enemy_Circle Enemy){
        if (health <= 0){
            destroyTower();
            if (numBlocked > 0 && Enemy.isBlocked){
                Enemy.isBlocked = false;
                numBlocked--;

            }
        }
    }


    public void update(Enemy_Square Enemy, long fps) {
        checkTowerHealth(Enemy);
        if (Enemy.facingRight && isActive){
            if (!Enemy.rolling && !Enemy.isBlocked && Enemy.hitBox.bottom < location.y + pixelsPerMeter){
                if ((Enemy.hitBox.right + Enemy.velocity.x) >= hitBox.left){
                    Enemy.location.x += (hitBox.left - Enemy.hitBox.right);
                    Enemy.velocity.x = 0;
                    if (Enemy.location.y + Enemy.velocity.y/fps >= Enemy.spawnPoint.y){
                        Enemy.location.y = Enemy.spawnPoint.y;
                        Enemy.isBlocked = true;
                        numBlocked++;
                        Enemy.velocity.y = 0;
                    }
                }
            } else if (Enemy.rolling && !Enemy.isBlocked){
                if ((Enemy.hitBox.right + Enemy.size*pixelsPerMeter) >= hitBox.left){
                    Enemy.rolling = false;
                }
            } else  if (Enemy.isBlocked && Enemy.attacking){
                health -= Enemy.damage;
                Enemy.attacking = false;
            } else {
                if (Enemy.hitBox.right > hitBox.left){
                    if ( Enemy.location.x -(hitBox.left - Enemy.hitBox.right)/fps < hitBox.left){
                        Enemy.location.x = (hitBox.left - (Enemy.size*pixelsPerMeter));
                    } else {
                        Enemy.location.x -= (hitBox.left - Enemy.hitBox.right)/fps;
                    }
                }
            }
        }
    }


    private void checkTowerHealth(Enemy_Square Enemy){
        if (health <= 0){
            destroyTower();
            if (numBlocked > 0 && Enemy.isBlocked){
                Enemy.isBlocked = false;
                numBlocked--;
            }
        }
    }


    public void update(Enemy_Triangle Enemy, long fps){
        checkTowerHealth(Enemy);
        if ((Enemy.A.x + pixelsPerMeter) >= hitBox.left && !Enemy.isBlocked){
            Enemy.location.x = (hitBox.left - pixelsPerMeter);
            Enemy.velocity.x = 0;
            numBlocked++;
            if (Enemy.location.y + Enemy.velocity.y/fps >= Enemy.spawnPoint.y){
                Enemy.location.y = Enemy.spawnPoint.y;
                Enemy.isBlocked = true;
                numBlocked++;
                Enemy.velocity.y = 0;
            }
        } else if (hitBox.contains(Enemy.center.x,Enemy.center.y)){
            health -= Enemy.damage;
            Enemy.destroy();
        }
    }


    private void checkTowerHealth(Enemy_Triangle Enemy){
        if (health <= 0){
            destroyTower();
            if (numBlocked > 0 && Enemy.isBlocked){
                Enemy.isBlocked = false;
                numBlocked--;
            }
        }
    }


    private void destroyTower(){
        isActive = false;
    }


    public void draw(Canvas canvas, Paint paint){
        if (isActive){
            paint.setColor(Color.argb(255,255,255,255));
            canvas.drawRect(hitBox,paint);
        }
    }
}
