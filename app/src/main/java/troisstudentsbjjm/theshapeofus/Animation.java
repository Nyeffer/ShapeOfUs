package troisstudentsbjjm.theshapeofus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by Jeffherson on 2017-05-21.
 */

public class Animation {
    Bitmap bitmapSheet;
    String bitmapName;
    private Rect sourceRect;
    private int frameCount,
                currentFrame,
                framePeriod,
                frameWidth,
                frameHeight;
    private long frameTicker;
    int pixelsPerMeter;

    public Animation (Context context,
               String bitmapName, float frameHeight,
               float frameWidth, int animFps,
               int frameCount, int pixelsPerMeter) {
        this.currentFrame = 0;
        this.frameCount = frameCount;
        this.frameWidth = (int) frameWidth * pixelsPerMeter;
        this.frameHeight = (int) frameHeight * pixelsPerMeter;
        sourceRect = new Rect(0, 0, this.frameWidth, this.frameHeight);
        framePeriod = 1000/ animFps;
        frameTicker = 0l;
        this.bitmapName = " " + bitmapName;
        this.pixelsPerMeter = pixelsPerMeter;
    }

    public Rect getCurrentFrame(long time,
                                    float X_Velocity, boolean moves) {
        if(time > frameTicker + framePeriod) {
            frameTicker = time;
            currentFrame++;
            if(currentFrame >= frameCount) {
                currentFrame = 0;
            }


        }
        this.sourceRect.right = this.sourceRect.left + frameWidth;
        return sourceRect;
    }

}
