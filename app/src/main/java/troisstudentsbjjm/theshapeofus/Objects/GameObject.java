package troisstudentsbjjm.theshapeofus.Objects;

/**
 * Created by MichealNedantsis on 2017-05-15.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public abstract class GameObject  {
    private Vector2Point worldLocation;
    private float width;
    private float height;

    private boolean active = true;
    private boolean visible = true;
    private int animFrameCount = 1;
    private char type;

    private String bitmapName;
    private RectHitbox rectHitbox = new RectHitbox();



}
