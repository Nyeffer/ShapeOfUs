package troisstudentsbjjm.theshapeofus.Primatives;

import android.graphics.Point;

/**
 * Created by mrber on 2017-05-15.
 */

public class Triangle extends GameObject {

    float area;
    Point A;
    Point B;
    Point C;

    Triangle(Point A, Point B, Point C){
        //This is the constructor that will create a triangle with three points
        //alternatively we can just add in a height and width, we would just need a little more math here
        this.A = new Point(A.x,A.y);                                                    //          O
        this.B = new Point(B.x,B.y);                                                    //         / \
        this.C = new Point(C.x,C.y);                                                    //        /   \
                                                                                        //       /     \
        setArea();                                                                      //      O-------O
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

    //area of triangle, calculated from values inputed in constructor
    private void setArea(){area =(float) Math.abs(0.5 * (A.x * (B.y - C.y) + B.x * (C.y - A.y) + C.x * (A.y - B.y)));}
    public float getArea(){return area;}
    //the following getters we would use to check if the triangle is in another objects hitbox
    public Point getA(){return A;}
    public Point getB(){return B;}
    public Point getC() {return C;}
}