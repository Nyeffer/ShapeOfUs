package troisstudentsbjjm.theshapeofus.Towers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;

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
    private float damage = 3;
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

        paint.setColor(Color.argb(255,150,150,150));

        for (Triangle spike : spikes){
            Path Triangle = new Path();
            Triangle.moveTo(spike.A.x,spike.A.y);
            Triangle.lineTo(spike.B.x,spike.B.y);
            Triangle.lineTo(spike.C.x,spike.C.y);
            Triangle.close();
            canvas.drawPath(Triangle,paint);
        }
    }


    public void update(ArrayList<Enemy_Circle> C_Enemies, ArrayList<Enemy_Square> S_Enemies, ArrayList<Enemy_Triangle> T_enemies, long fps){
        update_C(C_Enemies, fps);
        update_S(S_Enemies, fps);
        update_T(T_enemies, fps);
    }


    public void update_S(ArrayList<Enemy_Square> S_Enemies, long fps) {
        for (Enemy_Square Enemy : S_Enemies) {
            if (Enemy.angleD > 60) {
                if (hitbox.contains(Enemy.pivot.x + pixelsPerMeter, Enemy.pivot.y) || hitbox.contains(Enemy.center.x + pixelsPerMeter, Enemy.center.y)) {
                    Enemy.takeDamage(damage / fps);
                    if (Enemy.health <= 0) {
                        Enemy.destroy();
                    }
                }
            } else {
                if (hitbox.contains(Enemy.pivot.x, Enemy.pivot.y) || hitbox.contains(Enemy.center.x, Enemy.center.y)) {
                    Enemy.takeDamage(damage / fps);
                    if (Enemy.health <= 0) {
                        Enemy.destroy();
                    }
                    Enemy.setSize();
                }
            }
        }
    }


    public void update_T(ArrayList<Enemy_Triangle> T_Enemies, long fps) {
        for (Enemy_Triangle Enemy : T_Enemies) {
            if (hitbox.contains(Enemy.A.x, Enemy.A.y) || hitbox.contains(Enemy.B.x, Enemy.B.y)) {
                Enemy.takeDamage(damage / fps);
                if (Enemy.health <= 0) {
                    Enemy.destroy();
                }
            }
        }
    }


    public void update_C(ArrayList<Enemy_Circle> C_Enemies, long fps) {
        for (Enemy_Circle Enemy : C_Enemies) {
            if (hitbox.left < Enemy.center.x + Enemy.size * 0.5 * pixelsPerMeter && hitbox.right > Enemy.center.x - Enemy.size * 0.5 * pixelsPerMeter) {
                Enemy.takeDamage(damage / fps);
                if (Enemy.health <= 0) {
                    Enemy.destroy();
                }
            }
        }
    }
}
