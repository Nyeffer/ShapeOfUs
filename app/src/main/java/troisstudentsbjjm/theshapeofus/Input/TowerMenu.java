package troisstudentsbjjm.theshapeofus.Input;

import android.graphics.Canvas;
import android.graphics.Paint;

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

    Triangle S_button;
    Triangle C_button;
    Triangle T_button;

    Circle center;

    Square S_icon;
    Triangle T_icon;
    Circle C_icon;

    int size = 200;           //in pixels, 200 seems decent

    TowerMenu(){
        location.set(0,0);
        isActive = false;

        S_button = new Triangle();
        C_button = new Triangle();
        T_button = new Triangle();

    }


    public void moveAndDisplay(int x, int y){
        location.set(x,y);
        center.center.set(location.x,location.y);
        center.size = (float) (size*0.25);
        setButtons();
        setIcons();
        isActive = true;
    }


    private void setButtons(){
        S_button.A.set(location.x, location.y);
        S_button.B.set(location.x + size, (float) (location.y + 0.5*size));
        S_button.C.set(S_button.B.x, S_button.B.y - size);

        C_button.A.set(S_button.A.x, S_button.A.y);
        C_button.B.set(C_button.A.x - size, S_button.B.y);
        C_button.C.set(C_button.B.x, S_button.C.y);

        T_button.A.set(S_button.A.x, S_button.A.y);
        T_button.B.set((float) (T_button.A.x - 0.5*size), T_button.A.y - size);
        T_button.C.set(T_button.B.x + size, T_button.B.y);
    }


    private void setIcons(){

    }


    public void draw(Canvas canvas, Paint paint){
        if (isActive){
            //draw Buttons First

            //then draw center

            //then draw icons
        }
    }
}
