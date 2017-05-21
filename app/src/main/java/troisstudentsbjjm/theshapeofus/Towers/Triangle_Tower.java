package troisstudentsbjjm.theshapeofus.Towers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Circle;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Square;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Triangle;
import troisstudentsbjjm.theshapeofus.Primatives.Triangle;

/**
 * Created by mrber on 2017-05-15.
 */

public class Triangle_Tower {

    public ArrayList<Triangle> spikes;
    public final int NUM_SPIKES = 4;
    public float spikeSize = (float) (0.25);
    public RectF hitbox;
    private float damage = 700;
    int pixelsPerMeter;


    public PointF location;

    public Triangle_Tower(int x, int y, int pixelsPerMeter){
        this.pixelsPerMeter = pixelsPerMeter;
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


    public void update(Enemy_Square Enemy, long fps){

        if (Enemy.angleD > 60){
            if (hitbox.contains(Enemy.pivot.x+pixelsPerMeter,Enemy.pivot.y) || hitbox.contains(Enemy.center.x+pixelsPerMeter, Enemy.center.y)){
                Enemy.takeDamage(damage/fps);

                if (Enemy.health <= 0) {
                    Enemy.destroy();
                }
            }
        }  else {
            if (hitbox.contains(Enemy.pivot.x,Enemy.pivot.y) || hitbox.contains(Enemy.center.x, Enemy.center.y)){
                Enemy.takeDamage(damage/fps);
                if (Enemy.health <= 0) {
                    Enemy.destroy();
                }
                Enemy.updateSize();
            }
        }
    }


    public void update(Enemy_Triangle Enemy, long fps){

        if (hitbox.contains(Enemy.A.x,Enemy.A.y) || hitbox.contains(Enemy.B.x, Enemy.B.y)){
            Enemy.takeDamage(damage/fps);
            if (Enemy.health <= 0){
//                Enemy.destroy();
            }
        }
    }


    public void update(Enemy_Circle Enemy, long fps){

        if (hitbox.contains(Enemy.location.x, Enemy.location.y)){
            Enemy.takeDamage(damage/fps);
            if (Enemy.health <= 0) {
                Enemy.destroy();
            }
        }
    }
}
