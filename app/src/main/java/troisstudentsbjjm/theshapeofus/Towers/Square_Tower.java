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

    public int counter = 1;
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
            if(hitBox.contains(Enemy.getCollisionPoint().x + 1,Enemy.getCollisionPoint().y)) {
                Enemy.location.x += hitBox.left - Enemy.getCollisionPoint().x;

                Enemy.setIsBlocked(true);

                if(hitBox.contains(Enemy.getCollisionPoint().x,Enemy.getCollisionPoint().y)) {
//                    Enemy.location.x = hitBox.left - (float)(Enemy.getHealth()*0.75);

                }
//                if (counter <= Enemy.getHealth()/4 && isAdjustmentDone == false) {// To prevent Enemy_Circle to penetrate Square_Tower
//                    Log.d("ST", counter + " ");
//                    Enemy.location.x += hitBox.left - Enemy.getCollisionPoint().x;
//                    counter++;
//                } if(counter >= Enemy.getHealth()/4) {
//                    isAdjustmentDone = true;
//                }
            }
    }


    public void update(Enemy_Square Enemy, long fps) {
        checkTowerHealth(Enemy);
        if (Enemy.facingRight && isActive){
            if (!Enemy.rolling && !Enemy.isBlocked){
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
            }
        }
    }


    private void checkTowerHealth(Enemy_Square Enemy){
        if (health <= 0){
            if (numBlocked > 0){
                Enemy.isBlocked = false;
                numBlocked--;
            } else {
                destroyTower();
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
            if (numBlocked > 0){
                Enemy.isBlocked = false;
                numBlocked--;
            } else {
                destroyTower();
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
