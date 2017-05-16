package troisstudentsbjjm.theshapeofus;

import android.graphics.PointF;

/**
 * Created by mrber on 2017-05-15.
 */

public class Viewport {

    //viewport will take in a screenWidth and screenHeight and use that to calculate where everything should be
    // this will boil down to our pixelsPerMeter
    int viewportHeight;
    int viewportWidth;
    PointF screenCenter;

    //we can edit this to zoom in and out
    int pixelsPerMeter;

    public Viewport(int screenWidth, int screenHeight){

        this.viewportWidth = screenWidth;
        this.viewportHeight = screenHeight;
        screenCenter = new PointF((float) (screenWidth*0.5), (float) (screenHeight*0.5));

        //this assigns pixelsPerMeter to the screenWidth divided by roughly 33, 1/33 = 0.03, this implies we will have around 33 meters shown in our world
        pixelsPerMeter = (int)(screenWidth*0.03);
    }


    public int getPixelsPerMeter(){return pixelsPerMeter;}
}
