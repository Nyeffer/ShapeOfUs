package troisstudentsbjjm.theshapeofus.Input;

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

        
    }
}
