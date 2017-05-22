package troisstudentsbjjm.theshapeofus;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.Matrix;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import troisstudentsbjjm.theshapeofus.Primatives.GameObject;
import troisstudentsbjjm.theshapeofus.Primatives.Square;

/**
 * Created by Jeffherson on 2017-05-22.
 */

public class OmniGon extends Square {
    private SurfaceHolder ourHolder;


    OmniGon(Context context, float x, float y, char type, int pixelsPerMeter) {
        AssetManager assetManager = context.getAssets();
        AssetFileDescriptor descriptor;

        try {

            descriptor = assetManager.openFd("omnigon.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final int ANIMATION_FPS = 11;
        final int ANIMATION_FRAME_COUNT = 11;
        final String BITMAP_NAME= "omnigon";

        final float HEIGHT = 5;
        final float WIDTH = 3;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);
        setMoves(false);
        setBitmapName(BITMAP_NAME);
        setAnimFps(ANIMATION_FPS);
        setAnimFrameCount(ANIMATION_FRAME_COUNT);
        setBitmapName(BITMAP_NAME);
        setAnimated(context, pixelsPerMeter, true);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas = ourHolder.lockCanvas();

        // clear the last frame
        paint.setColor(Color.argb(255, 0 , 0, 255));
        canvas.drawColor(Color.argb(255, 0, 0, 255));
    }


}
