package troisstudentsbjjm.theshapeofus.Primatives;

/**
 * Created by mrber on 2017-05-15.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import troisstudentsbjjm.theshapeofus.RectHitbox;
import troisstudentsbjjm.theshapeofus.Vector2Point;

public abstract class GameObject  {

    public float size;
    public boolean isActive;
    public PointF location = new PointF();
}

