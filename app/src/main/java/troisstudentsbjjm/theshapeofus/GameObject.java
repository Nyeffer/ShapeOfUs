package troisstudentsbjjm.theshapeofus;

/**
 * Created by mrber on 2017-05-15.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.animation.Animation;

public abstract class GameObject  {
    private float size;

    private boolean active = true;
    private boolean visible = true;
    private char type;

    private String bitmapName;

    private PointF location;

    private int facing;
    private boolean moves = false;

    private boolean traversable = false;

    public abstract void update(long fps, float gravity);

    public String getBitmapName() {
        return bitmapName;
    }

    public Bitmap prepareBitmap(Context context, String bitmapName, int pixelsPerMetre) {
        int resID = context.getResources().getIdentifier(bitmapName, "drawable", context.getPackageName());

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID);

        bitmap = Bitmap.createScaledBitmap(bitmap, (int)(size * pixelsPerMetre), (int)(size * pixelsPerMetre), false);

        return bitmap;
    }


    public void setBitmapName(String bitmapName) {
        this.bitmapName = bitmapName;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public PointF getLocation() {
        return location;
    }

    public void setLocation(PointF location) {
        this.location = location;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }


    public boolean isMoves() {
        return moves;
    }

    public void setMoves(boolean moves) {
        this.moves = moves;
    }


    public void setActive(boolean active) {
        this.active = active;
    }

    public void setTraversable() {
        traversable = true;
    }

    public boolean isTraversable() {
        return traversable;
    }
}

