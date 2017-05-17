package troisstudentsbjjm.theshapeofus.Primatives;

/**
 * Created by mrber on 2017-05-15.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

public abstract class GameObject  {

    public float size;


    private boolean active = false;
    private boolean visible = false;

    private char type;

    private String bitmapName;

    public PointF location = new PointF();

    private int facing;

    private boolean moves = false;

    private boolean traversable = false;


    public Bitmap prepareBitmap(Context context, String bitmapName, int pixelsPerMetre) {
        int resID = context.getResources().getIdentifier(bitmapName, "drawable", context.getPackageName());

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID);

        bitmap = Bitmap.createScaledBitmap(bitmap, (int)(size * pixelsPerMetre), (int)(size * pixelsPerMetre), false);

        return bitmap;
    }

    //boolean getters
    public boolean isActive() {
        return active;
    }
    public boolean isMoves() {
        return moves;
    }
    public boolean isTraversable() {
        return traversable;
    }
    public boolean isVisible() {
        return visible;
    }
    //getters
    public String getBitmapName() {return bitmapName;}
    public int getFacing() {
        return facing;
    }
    public PointF getLocation() {
        return location;
    }
    public float getSize() {
        return size;
    }
    public char getType() {
        return type;
    }
    //setters
    public void setActive(boolean active) {
        this.active = active;
    }
    public void setBitmapName(String bitmapName) {
        this.bitmapName = bitmapName;
    }
    public void setFacing(int facing) {
        this.facing = facing;
    }
    public void setLocation(PointF location) {
        this.location = location;
    }
    public void setMoves(boolean moves) {
        this.moves = moves;
    }
    public void setSize(float size) {
        this.size = size;
    }
    public void setTraversable() {
        traversable = true;
    }
    public void setType(char type) {
        this.type = type;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }



}

