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
    private int health = 40;
    private int pixelsPerMeter;
    private int height;
    private int width;

    public Square_Tower(int x, int y, int pixelsPerMeter) {
        location.set(x,y);
        size = 1;
        this.pixelsPerMeter = pixelsPerMeter;
        setHitBox(x,y,pixelsPerMeter);
    }


    public void update(Enemy_Circle Enemy, long fps) {
            if(hitBox.contains(Enemy.getCollisionPoint().x + 1,Enemy.getCollisionPoint().y)) {
                Enemy.location.x += hitBox.left - Enemy.getCollisionPoint().x;
                Enemy.setIsBlocked(true);
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
            }
        }
    }


    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(255,255,255,255));
        canvas.drawRect(hitBox,paint);
    }
}
