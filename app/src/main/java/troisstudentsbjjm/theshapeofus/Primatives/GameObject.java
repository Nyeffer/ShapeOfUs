package troisstudentsbjjm.theshapeofus.Primatives;

/**
 * Created by mrber on 2017-05-15.
 */

import android.graphics.PointF;

import troisstudentsbjjm.theshapeofus.Animation;

public abstract class GameObject  {

    public float size;

    public boolean isActive;

    public PointF location = new PointF();

    public void update(long fps, float gravity) {
    }
}

