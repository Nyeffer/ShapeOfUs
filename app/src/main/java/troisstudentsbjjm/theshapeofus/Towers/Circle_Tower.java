package troisstudentsbjjm.theshapeofus.Towers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;

import troisstudentsbjjm.theshapeofus.Primatives.Circle;
import troisstudentsbjjm.theshapeofus.Primatives.Square;
import troisstudentsbjjm.theshapeofus.Primatives.Triangle;

/**
 * Created by mrber on 2017-05-15.
 */

public class Circle_Tower {

    private Circle circle;            // the tower will have a circle top
    private Triangle triangle;        // it will have a triangle bottom
    private Square square;            //rotates around circle

    private int pixelsPerMeter;
    private int range = 5;

    public PointF location;

    public RectF targetingRange;


    public Circle_Tower(float x, float y, int pixelsPerMeter){
        this.pixelsPerMeter = pixelsPerMeter;
        location = new PointF(x,y);
        circle = new Circle();
        circle.center = new PointF();
        triangle = new Triangle();
        square = new Square();
        initShapes(pixelsPerMeter);
        targetingRange = new RectF((float) (x + 0.5*pixelsPerMeter) - range*pixelsPerMeter, y-pixelsPerMeter, (float) (x + 0.5*pixelsPerMeter) + range*pixelsPerMeter, y+pixelsPerMeter);
    }


    private void initShapes(int pixelsPerMeter){
        triangle.size = (float) 2;
        triangle.setPoints(location.x, location.y, pixelsPerMeter);
        circle.size = (float) 0.5;
        circle.center.set(triangle.C.x, (float) (triangle.C.y - circle.size*0.7*pixelsPerMeter));
        square.size = (float) 0.2;
        square.location.set((circle.center.x - circle.size*pixelsPerMeter - square.size*pixelsPerMeter),(float) (circle.center.y - pixelsPerMeter+square.size*0.5*pixelsPerMeter));
        square.setHitBox(square.location.x, square.location.y, pixelsPerMeter);
    }


    public void update(){

    }


    public void draw(Canvas canvas, Paint paint){

        paint.setColor(Color.argb(255,255,255,255));
        canvas.drawRect(square.hitBox,paint);
        canvas.drawCircle(circle.center.x,circle.center.y,(float) (circle.size*0.5*pixelsPerMeter),paint);
        Path Triangle = new Path();
        Triangle.moveTo(triangle.A.x,triangle.A.y);
        Triangle.lineTo(triangle.B.x,triangle.B.y);
        Triangle.lineTo(triangle.C.x,triangle.C.y);
        Triangle.close();
        canvas.drawPath(Triangle,paint);
    }
}
