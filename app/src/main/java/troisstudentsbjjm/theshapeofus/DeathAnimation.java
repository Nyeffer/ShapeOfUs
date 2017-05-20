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
    public ArrayList<Circle> particles;

    public PointF omniGonPos;

    private final int NUM_PARTICLES = 16;
    private int pixelsPerMeter;
    private int stopCounter = 0;
    float maxY;

    private final long PARTICLE_REST_TIME = 3000;
    private long particleStopTime;

    private boolean initialized;
    private boolean resting;
    private boolean rising = true;

    char particlesType;

    Random gen = new Random();


    public DeathAnimation(int pixelsPerMeter, float maxY, float omniGonPosX, float omniGonPosY){
        this.pixelsPerMeter = pixelsPerMeter;
        this.maxY = maxY;
        particles = new ArrayList<>(NUM_PARTICLES);
        initParticles();
        initialized = false;
        omniGonPos = new PointF(omniGonPosX,omniGonPosY);

    }


    private void initParticles(){
        for (int i = 0; i < NUM_PARTICLES; i++){
            particles.add(i, new Circle());
            particles.get(i).center = new PointF();
            particles.get(i).particleVel = new PointF();
        }
    }


    public void setParticles(float x, float y, float size){
        rising = true;
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 8; j++){
                particles.get(j+(i*8)).size = (float) (size*0.5);
                particles.get(j+(i*8)).center.set((float) ( x - (size/4*Math.sin(j*(Math.PI/4)))) ,(float) (y+(size/4*Math.sin(j*(Math.PI/4)))));
                particles.get(j+(i*8)).particleVel.set(gen.nextInt(12) - 6, gen.nextInt(12) - 8);
            }
        }
    }


    public void update(float x, float y, float size, long fps){
        if (!initialized){
            setParticles(x, y, size);
            initialized = true;
        } else {
            for (Circle particle : particles){

                if (particle.isActive){
                    if (particle.center.y + particle.particleVel.y >= maxY){
                        particle.center.y = maxY;
                        particle.isActive = false;
                        stopCounter++;
                    } else {
                        particle.particleVel.y += 1;
                        particle.center.x += particle.particleVel.x;
                        particle.center.y += particle.particleVel.y;
                    }
                }
                if (stopCounter == 16 && !resting){
                    particleStopTime = System.currentTimeMillis();
                    resting = true;
                } else if ((System.currentTimeMillis() >= particleStopTime + PARTICLE_REST_TIME) && resting){
                    moveToOmnigon(particle,fps);
                }
            }
        }
    }


    private void moveToOmnigon(Circle particle, long fps){
        if (particle.center.y > (maxY - 3*pixelsPerMeter) && rising){
            particle.center.y += 2*((maxY - 3.1*pixelsPerMeter) - particle.center.y)/fps;
        } else {
            rising = false;
            particle.center.x += (omniGonPos.x - particle.center.x)/fps;
            particle.center.y += (omniGonPos.y - particle.center.y)/fps;
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
