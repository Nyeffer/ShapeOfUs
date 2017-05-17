package troisstudentsbjjm.theshapeofus.Primatives;


import android.graphics.Rect;
import android.graphics.RectF;


import troisstudentsbjjm.theshapeofus.Primatives.GameObject;

/**
 * Created by mrber on 2017-05-15.
 */

public class Square extends GameObject {



    public RectF hitBox = new RectF();


    public void setHitBox(int x, int y, int pixelsPerMeter){
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = hitBox.left + size*pixelsPerMeter;
        hitBox.bottom = hitBox.top + size*pixelsPerMeter;
    }
}
