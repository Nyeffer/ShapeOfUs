package troisstudentsbjjm.theshapeofus.Input;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

import troisstudentsbjjm.theshapeofus.Primatives.Circle;
import troisstudentsbjjm.theshapeofus.Primatives.GameObject;
import troisstudentsbjjm.theshapeofus.Primatives.Square;
import troisstudentsbjjm.theshapeofus.Primatives.Triangle;

/**
 * Created by mrber on 2017-05-23.
 */

public class TowerMenu extends GameObject {
    //class will create three triangles for butttons
    //class will create one circle for the center to finish the look
    //class will create one square, circle and triangle for icons on the buttons
    //extends gameobject because it will have a position and we can toggle the display on and off with the isActive switch

    public Triangle S_button;
    public Triangle C_button;
    public Triangle T_button;

    Circle center;

    Square S_icon;
    Triangle T_icon;
    Circle C_icon;

    int size = 200;           //in pixels, 200 seems decent

    public TowerMenu(){
        location.set(0,0);
        isActive = false;

        S_button = new Triangle();
        C_button = new Triangle();
        T_button = new Triangle();

        S_button.size = size;
        C_button.size = size;
        T_button.size = size;

        S_icon = new Square();
        T_icon = new Triangle();
        C_icon = new Circle();

        S_icon.size = (float) (0.15*size);
        C_icon.size = (float) (0.2*size);
        T_icon.size = (float) (0.5*size);

        center = new Circle();
        center.center = new PointF();
        C_icon.center = new PointF();
    }


    public void moveAndDisplay(int x, int y){
        location.set(x,y);
        center.center.set(location.x,location.y);
        center.size = (float) (size*0.2);
        setButtons();
        setIcons();
        isActive = true;
    }


    private void setButtons(){
        S_button.A.set(location.x, location.y);
        S_button.B.set(location.x + size, (float) (location.y + 0.5*size));
        S_button.C.set(S_button.B.x, S_button.B.y - size);
        S_button.setArea();

        C_button.A.set(S_button.A.x, S_button.A.y);
        C_button.B.set(C_button.A.x - size, S_button.B.y);
        C_button.C.set(C_button.B.x, S_button.C.y);
        C_button.setArea();

        T_button.A.set(S_button.A.x, S_button.A.y);
        T_button.B.set((float) (T_button.A.x - 0.5*size), T_button.A.y - size);
        T_button.C.set(T_button.B.x + size, T_button.B.y);
        T_button.setArea();
    }


    private void setIcons(){
        S_icon.hitBox.set((float) (S_button.A.x +0.55*S_button.size),S_button.A.y - S_icon.size, (float) (S_button.A.x + 0.55*S_button.size+2*S_icon.size), S_button.A.y + S_icon.size);

        C_icon.center.set((float) (C_button.A.x - 0.7*C_button.size), C_button.A.y);

        T_icon.A.set(S_button.A.x, S_button.A.y - (float) (0.7*T_icon.size));
        T_icon.B.set((float) (T_icon.A.x - 0.5*T_icon.size), T_icon.A.y - T_icon.size);
        T_icon.C.set(T_icon.B.x + T_icon.size, T_icon.B.y);

    }


    public void draw(Canvas canvas, Paint paint){
        if (isActive){
            paint.setColor(Color.argb(100,100,100,100));
            //draw Buttons First
            Path Sbutton = new Path();
            Sbutton.moveTo(S_button.A.x,S_button.A.y);
            Sbutton.lineTo(S_button.B.x,S_button.B.y);
            Sbutton.lineTo(S_button.C.x,S_button.C.y);
            Sbutton.close();
            canvas.drawPath(Sbutton,paint);
            Path Cbutton = new Path();
            Cbutton.moveTo(C_button.A.x,C_button.A.y);
            Cbutton.lineTo(C_button.B.x,C_button.B.y);
            Cbutton.lineTo(C_button.C.x,C_button.C.y);
            Cbutton.close();
            canvas.drawPath(Cbutton,paint);
            Path Tbutton = new Path();
            Tbutton.moveTo(T_button.A.x,T_button.A.y);
            Tbutton.lineTo(T_button.B.x,T_button.B.y);
            Tbutton.lineTo(T_button.C.x,T_button.C.y);
            Tbutton.close();
            canvas.drawPath(Tbutton,paint);
            //then draw center
            paint.setColor(Color.argb(255,170,170,170));
            canvas.drawCircle(center.center.x,center.center.y,center.size,paint);
            //then draw icons
            paint.setColor(Color.argb(100,255,255,255));
            Path Ticon = new Path();
            Ticon.moveTo(T_icon.A.x,T_icon.A.y);
            Ticon.lineTo(T_icon.B.x,T_icon.B.y);
            Ticon.lineTo(T_icon.C.x,T_icon.C.y);
            Ticon.close();
            canvas.drawPath(Ticon,paint);
            canvas.drawRect(S_icon.hitBox,paint);
            canvas.drawCircle(C_icon.center.x,C_icon.center.y,C_icon.size,paint);

        }
    }
}
