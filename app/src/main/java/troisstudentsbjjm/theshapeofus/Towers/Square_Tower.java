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
    private int health;
    private int pixelsPerMeter;
    private int height;
    private int width;
    public int counter = 1;
    public boolean isAdjustmentDone;
    private Square_Tower squareTower;

    public Square_Tower(int x, int y, int health, int pixelsPerMeter) {
        this.health = health;
        location.set(x,y);
        updateSize();
        this.pixelsPerMeter = pixelsPerMeter;
        setHitBox(x,y,pixelsPerMeter);
        isAdjustmentDone = false;
    }

    private void updateSize(){setSize ((int) (75*0.025));}

    public void update(Enemy_Circle Enemy) {

            if(hitBox.contains(Enemy.getCollisionPoint().x,Enemy.getCollisionPoint().y)) {
                Log.d("ST", Enemy.location + " ");
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
