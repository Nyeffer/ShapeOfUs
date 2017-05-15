package troisstudentsbjjm.theshapeofus.Objects;

/**
 * Created by MichealNedantsis on 2017-05-15.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.animation.Animation;

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

    private float xVelocity;
    private float yVelocity;
    final int LEFT = -1;
    final int RIGHT = 1;
    private int facing;
    private boolean moves = false;

    private Animation anim = null;
    private int animFps = 1;

    private boolean traversable = false;

    public abstract void update(long fps, float gravity);

    public String getBitmapName() {
        return bitmapName;
    }

    public Bitmap prepareBitmap(Context context, String bitmapName, int pixelsPerMetre) {
        int resID = context.getResources().getIdentifier(bitmapName, "drawable", context.getPackageName());

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID);

        bitmap = Bitmap.createScaledBitmap(bitmap, (int)(width * animFrameCount * pixelsPerMetre), (int)(height * pixelsPerMetre), false);

        return bitmap;
    }

    public  Vector2Point getWorldLocation() {
        return worldLocation;
    }

    public void setWorldLocation(float x, float y, int z) {
        this.worldLocation = new Vector2Point();
        this.worldLocation.x = x;
        this.worldLocation.y = y;
        this.worldLocation.z = z;
    }

    void move(long fps) {
        if (xVelocity != 0) {
            this.worldLocation.x += xVelocity / fps;
        }

        if (yVelocity != 0) {
            this.worldLocation.y += yVelocity / fps;
        }
    }

    public void setRectHitbox() {
        rectHitbox.setTop(worldLocation.y);
        rectHitbox.setLeft(worldLocation.x);
        rectHitbox.setBottom(worldLocation.y + height);
        rectHitbox.setRight(worldLocation.x + width);
    }

    RectHitbox getRectHitbox() {
        return rectHitbox;
    }

    public void setBitmapName(String bitmapName) {
        this.bitmapName = bitmapName;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }



}
