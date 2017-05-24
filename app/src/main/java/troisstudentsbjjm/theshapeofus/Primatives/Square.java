package troisstudentsbjjm.theshapeofus.Primatives;


import android.graphics.Rect;
import android.graphics.RectF;


import troisstudentsbjjm.theshapeofus.Primatives.GameObject;

/**
 * Created by mrber on 2017-05-15.
 */

public class Square extends GameObject {

    public RectF hitBox = new RectF();

    protected void setHitBox(float x, float y, float pixelsPerMeter){
        hitBox.left = x;
        hitBox.right = hitBox.left + size*pixelsPerMeter;
        hitBox.bottom = y + pixelsPerMeter;
        hitBox.top = hitBox.bottom - size*pixelsPerMeter;
    }
}
