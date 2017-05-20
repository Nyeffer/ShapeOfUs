package troisstudentsbjjm.theshapeofus.Towers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Circle;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Square;
import troisstudentsbjjm.theshapeofus.Primatives.Square;

/**
 * Created by mrber on 2017-05-15.
 */

public class Square_Tower extends Square {
    private int health = 80;
    private int pixelsPerMeter;

    private int height;
    private int width;
    public int counter = 1;
    public boolean isAdjustmentDone;
    private Square_Tower squareTower;



    public Square_Tower(int x, int y, int pixelsPerMeter) {
        location.set(x,y);
        size = 1;
        this.pixelsPerMeter = pixelsPerMeter;
        setHitBox(x,y,pixelsPerMeter);
        isAdjustmentDone = false;
    }



    public void update(Enemy_Circle Enemy, long fps) {
            if(hitBox.contains(Enemy.getCollisionPoint().x + 1,Enemy.getCollisionPoint().y)) {
                Enemy.location.x += hitBox.left - Enemy.getCollisionPoint().x;

                Enemy.setIsBlocked(true);
                Enemy.setSquareTower(getSquare_Tower());

                if(hitBox.contains(Enemy.getCollisionPoint().x,Enemy.getCollisionPoint().y)) {
                    Enemy.location.x = hitBox.left - (float)(Enemy.getHealth()*0.75);

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
        if (Enemy.facingRight){
            if (!Enemy.rolling && !Enemy.isBlocked){
                if ((Enemy.hitBox.right + Enemy.velocity.x) >= hitBox.left){
                    Enemy.location.x += (hitBox.left - Enemy.hitBox.right);
                    Enemy.velocity.x = 0;
                    if (Enemy.location.y == Enemy.spawnPoint.y){
                        Enemy.isBlocked = true;
                    }
                }
            } else if (Enemy.rolling && !Enemy.isBlocked){
                if ((Enemy.hitBox.right + Enemy.size*pixelsPerMeter) >= hitBox.left){
                    Enemy.rolling = false;
                }
            } else  if (Enemy.isBlocked && Enemy.attacking){
                health -= Enemy.damage;
                Enemy.attacking = false;
                Log.d("enemy attacking", "Square tower health: " + health);
            }
        }
    }


    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(255,255,255,255));
        canvas.drawRect(hitBox,paint);
    }

    public void setIsAdjust(boolean isAdjustmentDone) {
        this.isAdjustmentDone = isAdjustmentDone;
    }

    public int setCounter(int counter) { this.counter = counter;  return counter; }

    public Square_Tower getSquare_Tower() { return squareTower;    }
}
