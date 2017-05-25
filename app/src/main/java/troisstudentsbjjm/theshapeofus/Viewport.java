package troisstudentsbjjm.theshapeofus;

import android.graphics.Rect;

/**
 * Created by mrber on 2017-05-15.
 * Edited by Braedon Jolie on 2017-05-17.
 */

public class Viewport {

    private Rect convertedRect;
    public int pixelsPerMeter;
    private int screenXResolution;
    private int screenYResolution;
    private int screenCenterX;
    private int screenCenterY;
    private int metersToShowX;
    private int metersToShowY;
    private int numClipped;

    Viewport(int x, int y) {
        screenXResolution = x;
        screenYResolution = y;

        pixelsPerMeter = (int)(screenXResolution * 0.03);

        screenCenterX = screenXResolution / 2;
        screenCenterY = screenYResolution / 2;

        metersToShowX = 34;
        metersToShowY = 20;

        convertedRect = new Rect();

    }

    public int getScreenWidth() {
        return screenXResolution;
    }

    public int getScreenHeight() {
        return screenYResolution;
    }

    public int getyCenter() {
        return screenCenterY;
    }

    public int getNumClipped() {
        return numClipped;
    }

    public void resetNumClipped() {
        numClipped = 0;
    }
}