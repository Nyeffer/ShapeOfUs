package troisstudentsbjjm.theshapeofus.Primatives;



import android.graphics.PointF;
import android.graphics.RectF;


/**
 * Created by mrber on 2017-05-15.
 */

public class Circle extends GameObject {
    //we can add more points to indicate where the circles boundary is at select angles
    //it does not need to be precise so I think one to the bottom right and one to the bottom left at 45 degrees should be ok

    PointF bottomRight;
    PointF bottomLeft;
    public PointF center;
    public PointF particleVel;
    public PointF distanceToOmniGon;

    public RectF hitBox = new RectF();

    public void setHitBox(int x, int y, int pixelsPerMeter){
        hitBox.left = x;
        hitBox.right = hitBox.left + size*pixelsPerMeter;
        hitBox.bottom = y + pixelsPerMeter;
        hitBox.top = hitBox.bottom - size*pixelsPerMeter;

    }
}
