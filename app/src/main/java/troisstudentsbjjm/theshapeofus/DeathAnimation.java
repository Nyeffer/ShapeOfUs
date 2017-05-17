package troisstudentsbjjm.theshapeofus;

import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Primatives.Circle;
import troisstudentsbjjm.theshapeofus.Primatives.Square;
import troisstudentsbjjm.theshapeofus.Primatives.Triangle;

/**
 * Created by mrber on 2017-05-15.
 */

public class DeathAnimation {

    //create Array of objects to be moved and animated upon enemy death
    ArrayList<GameObject> particles;

    private final int NUM_PARTICLES = 16;

    char particlesType;

    public DeathAnimation(char enemyType){                              //The enemyType is a reference to either a Square/circle or Triangle
        this.particlesType = enemyType;                                 //So that the constructor knows what shape to put into the array
        initParticles();
    }


    private void initParticles(){

        switch (particlesType){
            //square
            case 's':
                for (int i = 0; i < NUM_PARTICLES; i++){
                    particles.add(new Square());
                }
                break;
            //circle
            case 'c':
                for (int i = 0; i < NUM_PARTICLES; i++){
                    particles.add(new Circle());
                }
                break;
            //triangle
            case 't':
                for (int i = 0; i < NUM_PARTICLES; i++){
                    particles.add(new Square());
                }
                break;
        }
    }


    public void setParticles(Point position, int height, int width){

        switch (particlesType){
            //square
            case 's':
                for (int i = 0; i < NUM_PARTICLES; i++){
                    //for each particle we need to update their position, size and velocity upon enemy death because enemies will die at different positions and different sizes
                }
                break;
            //circle
            case 'c':
                for (int i = 0; i < NUM_PARTICLES; i++){

                }
                break;
            //triangle
            case 't':
                for (int i = 0; i < NUM_PARTICLES; i++){

                }
                break;
        }
    }


    public void draw(Canvas canvas){

    }
}
