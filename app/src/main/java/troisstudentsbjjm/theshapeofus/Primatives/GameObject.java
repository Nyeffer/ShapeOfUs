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


    private boolean traversable = false;

    // For animation
    private Animation anim = null;
    private boolean animated;
    private int animFps;
    private int animFrameCount;


    public void setAnimated(Context context,int pixelsPerMeter, boolean animated) {
        this.animated = animated;
        this.anim = new Animation(context, getBitmapName(), getSize(), getSize(), animFps, animFrameCount, pixelsPerMeter);
    }




    //boolean getters
    public boolean isMoves() {
        return moves;
    }
    public boolean isTraversable() {
        return traversable;
    }
    public boolean isAnimated() { return isAnimated();  }
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
    public void setHeight(float height) {this.height = height;  }
    public void setWidth(float width) { this.width = width; }
    public void setTraversable() {
        traversable = true;
    }
    public void setType(char type) {
        this.type = type;

    }
    public void setAnimFps(int animFps) {   this.animFps = animFps; }
    public void setAnimFrameCount(int animFrameCount) { this.animFrameCount = animFrameCount;   }


}

