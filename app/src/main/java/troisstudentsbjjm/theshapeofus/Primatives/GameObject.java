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
    public PointF location = new PointF();

    private Vector2Point worldLocation;
    private float width;
    private float height;

    private boolean active = false;
    private boolean visible = false;

    private char type;

    private String bitmapName;

    private float xVelocity;
    private float yVelocity;
    final int LEFT = -1;
    final int RIGHT = 1;
    private int facing;
    private boolean moves = false;

    private RectHitbox rectHitBox = new RectHitbox();

    private boolean traversable = false;

    public abstract void update(long fps, float gravity);

    public Bitmap prepareBitmap(Context context, String bitmapName, int pixelsPerMetre) {
        int resID = context.getResources().getIdentifier(bitmapName, "drawable", context.getPackageName());

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID);

        bitmap = Bitmap.createScaledBitmap(bitmap, (int)(size * pixelsPerMetre), (int)(size * pixelsPerMetre), false);

        return bitmap;
    }

    void move(long fps) {
        if(xVelocity != 0) {
            this.worldLocation.x += xVelocity / fps;
        }

        if(yVelocity != 0) {
            this.worldLocation.y += yVelocity / fps;
        }
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
    public float getHeight() {
        return height;
    }
    public RectHitbox getHitBox() { return rectHitBox; }
    public float getSize() {
        return size;
    }
    public char getType() {
        return type;
    }
    public float getWidth() {
        return width;
    }
    public Vector2Point getWorldLocation() {
        return worldLocation;
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
    public void setHeight(float height) {
        this.height = height;
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
    public void setWidth(float width) {
        this.width = width;
    }
    public void setRectHitbox() {
        rectHitBox.setTop(worldLocation.y);
        rectHitBox.setLeft(worldLocation.x);
        rectHitBox.setBottom(worldLocation.y + height);
        rectHitBox.setRight(worldLocation.x + width);
    }
    public void setWorldLocation(float x, float y, int z) {
        this.worldLocation = new Vector2Point();
        this.worldLocation.x = x;
        this.worldLocation.y = y;
        this.worldLocation.z = z;
    }



}

