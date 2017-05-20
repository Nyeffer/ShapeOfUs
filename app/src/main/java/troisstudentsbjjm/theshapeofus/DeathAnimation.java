package troisstudentsbjjm.theshapeofus;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Random;

import troisstudentsbjjm.theshapeofus.Primatives.Circle;
import troisstudentsbjjm.theshapeofus.Primatives.GameObject;
import troisstudentsbjjm.theshapeofus.Primatives.Square;

/**
 * Created by mrber on 2017-05-15.
 */

public class DeathAnimation {

    //create Array of objects to be moved and animated upon enemy death
    public int explosiveForce = 1;
    ArrayList<Circle> particles;

    private final int NUM_PARTICLES = 16;
    int pixelsPerMeter;

    private boolean initialized;

    char particlesType;

    Random gen = new Random();

    public DeathAnimation(int pixelsPerMeter){
        this.pixelsPerMeter = pixelsPerMeter;
        particles = new ArrayList<>(NUM_PARTICLES);
        initParticles();
        initialized = false;
    }


    private void initParticles(){
        for (int i = 0; i < NUM_PARTICLES; i++){
            particles.add(i, new Circle());
        }
    }


    public void setParticles(float x, float y, float size){
        for (int i = 0; i < NUM_PARTICLES; i++){
            particles.get(i).size = (float) (size*0.25);
            particles.get(i).center = new PointF((float) ((x - size*pixelsPerMeter) + 0.125*size*pixelsPerMeter) ,(float) ((y - size*pixelsPerMeter) + 0.125*size*pixelsPerMeter));
        }

//        for (int i = 0; i < NUM_PARTICLES/4; i++){
//            particles.get(i).size = (float) (size*0.25);
//            for (int j = 0; j < NUM_PARTICLES/4; j++){
//                particles.get(i).center = new PointF((float) ((x - size*pixelsPerMeter) + 0.125*size*(j+1)*pixelsPerMeter) ,(float) ((y - size*pixelsPerMeter) + 0.125*size*(j+1)*pixelsPerMeter));
//            }
//        }
    }


    public void update(float x, float y, float size, long fps){
        if (!initialized){
            setParticles(x, y, size);
            initialized = true;
        } else {
//            for (Circle particle : particles){
//                x = gen.nextInt(6);
//                y = gen.nextInt(6);
//                particle.center.x += x;
//                particle.center.y += y;
//            }
        }
    }


    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.argb(255, 255, 255, 255));
        if (initialized){
            for (Circle particle : particles){
                canvas.drawCircle(particle.center.x, particle.center.y, (float)(particle.size*0.5*pixelsPerMeter), paint);
            }
        }
    }
}
