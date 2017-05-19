package troisstudentsbjjm.theshapeofus.Primatives;


import android.graphics.PointF;

/**
 * Created by mrber on 2017-05-15.
 */

public class Circle extends GameObject {

    int radius;
    //we can add more points to indicate where the circles boundary is at select angles
    //it does not need to be precise so I think one to the bottom right and one to the bottom left at 45 degrees should be ok
    PointF bottomRight;
    PointF bottomLeft;
    public PointF center;
}
