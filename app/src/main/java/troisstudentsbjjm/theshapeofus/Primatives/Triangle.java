package troisstudentsbjjm.theshapeofus.Primatives;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by mrber on 2017-05-15.
 */

public class Triangle extends GameObject {

    float area;

    public PointF A = new PointF();
    public PointF B = new PointF();
    public PointF C = new PointF();


    public void setPoints(float x, float y,float pixelsPerMeter) {
        A.set(x, y + pixelsPerMeter);
        B.set((A.x + size * pixelsPerMeter), A.y);
        C.set((float) (A.x + 0.5 * size * pixelsPerMeter), (A.y - size * pixelsPerMeter) );

    }


    public boolean contains(int x, int y){
        //function takes in a x and y position, it then creates areas using this position, adds them together and then checks
        // to see if it is equal to the normal shapes area, if it is then the point is within the triangle
        float area1 = (float) Math.abs(0.5 * (x * (B.y - C.y) + B.x * (C.y - y) + C.x * (y - B.y)));
        float area2 = (float) Math.abs(0.5 * (A.x * (y - C.y) + x * (C.y - A.y) + C.x * (A.y - y)));
        float area3 = (float) Math.abs(0.5 * (A.x * (B.y - y) + B.x * (y - A.y) + x * (A.y - B.y)));

        if (area1 + area2 + area3 == area){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void update(long fps, float gravity) {

    }

    //area of triangle, half base times height
    public void setArea(){area =(float) (0.5*size*size);}
    public float getArea(){return area;}
    //the following getters we would use to check if the triangle is in another objects hitbox
    public PointF getA(){return A;}
    public PointF getB(){return B;}
    public PointF getC() {return C;}
}