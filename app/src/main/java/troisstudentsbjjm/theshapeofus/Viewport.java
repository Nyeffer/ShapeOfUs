package troisstudentsbjjm.theshapeofus;

import android.graphics.Rect;

/**
 * Created by mrber on 2017-05-15.
 * Edited by Braedon Jolie on 2017-05-17.
 */

public class Viewport {
    private Vector2Point currentViewportWorldCenter;
    private Rect convertedRect;
    private int pixelsPerMeterX;
    private int pixelsPerMeterY;
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

        screenCenterX = screenXResolution / 2;
        screenCenterY = screenYResolution / 2;

        pixelsPerMeterX = screenXResolution / 32;
        pixelsPerMeterY = screenYResolution / 18;

        metersToShowX = 34;
        metersToShowY = 20;

        convertedRect = new Rect();
        currentViewportWorldCenter = new Vector2Point();
    }

    public void setWorldCenter(float x, float y) {
        currentViewportWorldCenter.x = x;
        currentViewportWorldCenter.y = y;
    }

    public int getScreenWidth() {
        return screenXResolution;
    }

    public int getScreenHeight() {
        return screenYResolution;
    }

    public int getPixelsPerMeterX() {
        return pixelsPerMeterX;
    }

    public int getPixelsPerMeterY() {
        return pixelsPerMeterY;
    }

    public int getyCenter() {
        return screenCenterY;
    }

    public float getViewportWorldCenterY() {
        return currentViewportWorldCenter.y;
    }

    public float getViewportWorldCenterX() {
        return currentViewportWorldCenter.x;
    }

    public Rect worldToScreen(float objX, float objY, float objWidth, float objHeight) {
        int left = (int)(screenCenterX - ((currentViewportWorldCenter.x - objX) * pixelsPerMeterX));
        int top = (int)(screenCenterY - ((currentViewportWorldCenter.y - objY) * pixelsPerMeterY));
        int right = (int)(left + (objWidth * pixelsPerMeterX));
        int bottom = (int)(top + (objHeight * pixelsPerMeterY));

        convertedRect.set(left, top, right, bottom);

        return convertedRect;
    }

    public boolean clipObjects(float objX, float objY, float objWidth, float objHeight) {
        boolean clipped = true;

        if(objX - objWidth < currentViewportWorldCenter.x + (metersToShowX / 2)) {
            if(objX + objWidth > currentViewportWorldCenter.x - (metersToShowX / 2)) {
                if(objY - objHeight < currentViewportWorldCenter.y + (metersToShowY / 2)) {
                    if(objY + objHeight > currentViewportWorldCenter.y - (metersToShowY / 2)) {
                        clipped = false; // don't clip it, its on the screen!
                    }
                }
            }
        }

        // for debugging
        if(clipped) {
            numClipped++;
        }

        return clipped;
    }

    public int getNumClipped() {
        return numClipped;
    }

    public void resetNumClipped() {
        numClipped = 0;
    }

    // Material to move the viewport, but this is only for small movements
    public void moveViewPortRight(int maxWidth, float move) {
        if(currentViewportWorldCenter.x < maxWidth - (metersToShowX/2) + 3) {
            currentViewportWorldCenter.x += move;
        }
    }

    public void moveViewPortLeft(float move) {
        if(currentViewportWorldCenter.x > (metersToShowX/2) - 3) {
            currentViewportWorldCenter.x -= move;
        }
    }

    public void moveViewPortUp(float move) {
        if(currentViewportWorldCenter.y < (metersToShowY/2) - 3) {
            currentViewportWorldCenter.y -= move;
        }
    }

    public void moveViewPortDown(int maxHeight, float move) {
        if(currentViewportWorldCenter.y < maxHeight - (metersToShowY/2) + 3) {
            currentViewportWorldCenter.y += move;
        }
    }
}