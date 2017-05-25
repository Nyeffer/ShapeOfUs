package troisstudentsbjjm.theshapeofus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.opengl.Matrix;

import troisstudentsbjjm.theshapeofus.Primatives.Square;

/**
 * Created by Jeffherson on 2017-05-22.
 */

public class OmniGon extends Square{
    private GameView view;
    float x, y;
    private Bitmap bitmap;
    String name = "Onmi";
    Bitmap[] bitmaps;
    private Animation anim;
    Rect[] frames;
    private final int HEIGHT = 139;
    private final int WIDTH = 65;
    final int ANIMATION_FPS = 2;
    final int ANIMATION_FRAME_COUNT = 10;
    final String BITMAP_NAME= "omnigon";
    int bitmapIndex = 0;
    final long TIME_BETWEEN_DRAWS = 100;
    long drawTime;


    OmniGon(Context context, float x, float y, int pixelsPerMeter) {
//        AssetManager assetManager = context.getAssets();
//        AssetFileDescriptor descriptor;
//
//        try {
//
//            descriptor = assetManager.openFd("omnigon.png");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        frames = new Rect[ANIMATION_FRAME_COUNT];



        Matrix flipper = new Matrix();
        size = 5;


        this.x = x;
        this.y = y;
        anim = new Animation(context, BITMAP_NAME, HEIGHT, WIDTH, ANIMATION_FPS, ANIMATION_FRAME_COUNT, pixelsPerMeter);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.omnigon);
        bitmaps = new Bitmap[10];
        bitmaps[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi1);
        bitmaps[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi2);
        bitmaps[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi3);
        bitmaps[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi4);
        bitmaps[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi5);
        bitmaps[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi6);
        bitmaps[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi7);
        bitmaps[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi8);
        bitmaps[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi9);
        bitmaps[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.onmi10);
        setHeight(HEIGHT);
        setWidth(WIDTH);

        setMoves(false);
        setBitmapName(BITMAP_NAME);
        setAnimFps(ANIMATION_FPS);
        setAnimFrameCount(ANIMATION_FRAME_COUNT);
        setBitmapName(BITMAP_NAME);
        setAnimated(context, pixelsPerMeter, true);
        setHitBox(x,y,pixelsPerMeter);

    }

    public Rect getRectToDraw(long deltaTime) {
        return anim.getCurrentFrame(deltaTime);
    }





    public float getHeight() { return HEIGHT;}
    public float getWidth() { return WIDTH; }
    public int getAnimCount() { return ANIMATION_FRAME_COUNT;   }

    public void draw(Canvas canvas, Paint paint) {
        // clear the last frame
//        Rect r = getRectToDraw(System.currentTimeMillis());
        paint.setColor(Color.argb(255, 0 , 0, 255));
        canvas.drawColor(Color.argb(255, 0, 0, 255));
//        Log.d("OmniGon", r.left + " " + r.top);
//        canvas.drawBitmap(bitmap,r.left, r.top,paint);
        canvas.drawBitmap(bitmaps[bitmapIndex], hitBox.left, hitBox.top, paint);
        if(System.currentTimeMillis() >= drawTime + TIME_BETWEEN_DRAWS) {
            drawTime = System.currentTimeMillis();
            if(bitmapIndex < 9) {
                bitmapIndex++;
            } else {
                bitmapIndex = 0;
            }
        }



    }

}
