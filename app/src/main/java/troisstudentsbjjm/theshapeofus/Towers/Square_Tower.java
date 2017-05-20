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

    public Square_Tower(int x, int y, int health, int pixelsPerMeter) {
        this.health = health;
        location.set(x,y);
        updateSize();
        this.pixelsPerMeter = pixelsPerMeter;
        setHitBox(x,y,pixelsPerMeter);
    }

    private void updateSize(){setSize ((int) (75*0.025));}

    public void update(Enemy_Circle Enemy) {
            if(hitBox.contains(Enemy.getCollisionPoint().x + 1,Enemy.getCollisionPoint().y)) {
                Enemy.location.x += hitBox.left - Enemy.getCollisionPoint().x;
                Enemy.setIsBlocked(true);
        }
    }


    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(255,255,255,255));
        canvas.drawRect(hitBox,paint);
    }
}
