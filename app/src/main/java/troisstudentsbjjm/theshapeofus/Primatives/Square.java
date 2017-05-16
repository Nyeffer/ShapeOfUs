package troisstudentsbjjm.theshapeofus.Primatives;

import android.graphics.Rect;

import troisstudentsbjjm.theshapeofus.Primatives.GameObject;

/**
 * Created by mrber on 2017-05-15.
 */

public class Square extends GameObject {

    Rect hitBox;

    public Square(){
        //initialize hitbox, we can set it later
        //note: The hitboxes will in-effect be the objects themselves as they are the same shape and will be updated as such
        hitBox = new Rect();
    }
}
