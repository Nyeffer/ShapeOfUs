package troisstudentsbjjm.theshapeofus.Towers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Square;
import troisstudentsbjjm.theshapeofus.Primatives.GameObject;
import troisstudentsbjjm.theshapeofus.Primatives.Triangle;

/**
 * Created by mrber on 2017-05-15.
 */

public class Triangle_Tower {

    public ArrayList<Triangle> spikes;
    public final int NUM_SPIKES = 5;
    public float spikeSize = (float) (0.2);
    public RectF hitbox;
    private int damage = 40;
    private final long TIME_BETWEEN_DAMAGE_TICKS = 500;
    private long timeAttacked = 0;

    public PointF location;

    public Triangle_Tower(int x, int y, int pixelsPerMeter){
        location = new PointF(x,y);
        spikes = new ArrayList<Triangle>();
        initSpikes(pixelsPerMeter);
        hitbox = new RectF( x, (float) (y + 0.7*pixelsPerMeter),x + pixelsPerMeter, (float) (y + 1.2*pixelsPerMeter));
    }


    public void initSpikes(int pixelsPerMeter){
        for (int i = 0; i < NUM_SPIKES; i++){
            spikes.add(i,new Triangle());
            spikes.get(i).size = spikeSize;
            spikes.get(i).setPoints((int)(location.x + i*spikeSize*pixelsPerMeter), (int)(location.y), pixelsPerMeter);
        }
    }


    public void draw(Canvas canvas, Paint paint){

        paint.setColor(Color.argb(255,255,255,255));

        for (Triangle spike : spikes){
            Path Triangle = new Path();
            Triangle.moveTo(spike.A.x,spike.A.y);
            Triangle.lineTo(spike.B.x,spike.B.y);
            Triangle.lineTo(spike.C.x,spike.C.y);
            Triangle.close();
            canvas.drawPath(Triangle,paint);
        }
    }


    public void update(Enemy_Square Enemy){
        if (System.currentTimeMillis() - timeAttacked > TIME_BETWEEN_DAMAGE_TICKS){
            if (hitbox.contains(Enemy.pivot.x,Enemy.pivot.y)){
                Enemy.takeDamage(damage);
                if (Enemy.getHealth() <= 0) {
                    Enemy.destroy();
                }
            } else {
                Log.d("not hit","no dmg");
            }
        }
    }



}
