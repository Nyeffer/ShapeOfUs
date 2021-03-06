package troisstudentsbjjm.theshapeofus.Level;


import android.util.Log;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Random;

import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Circle;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Square;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Triangle;
import troisstudentsbjjm.theshapeofus.Input.InputController;
import troisstudentsbjjm.theshapeofus.Primatives.GameObject;

public class Wave {

    public ArrayList<Enemy_Circle> circles;             //holds all the circles
    public ArrayList<Enemy_Square> squares;             //holds all the squares
    public ArrayList<Enemy_Triangle> triangles;         //holds all the triangles

    public final int numEnemies;
    private int enemyCounter = 0;

    private double timeBetweenEnemies;
    private long spawnTime;

    public long waveCompletionTime;
    public boolean waveComplete;

    Random gen = new Random();

    public Wave(int xStartPosition, int yStartPosition, float healthFactor, int pixelsPerMeter, int omniGonPosX, int omniGonPosY, int numEnemies, double timeBetweenEnemies){
        this.timeBetweenEnemies = (long)timeBetweenEnemies*1000;
        this.numEnemies = numEnemies;

        circles = new ArrayList<>();
        squares = new ArrayList<>();
        triangles = new ArrayList<>();

        waveComplete = false;

        initArrays(xStartPosition, yStartPosition, healthFactor, pixelsPerMeter, omniGonPosX, omniGonPosY, numEnemies);
    }


    private void initArrays(int xStartPosition, int yStartPosition, float healthFactor, int pixelsPerMeter, int omniGonPosX, int omniGonPosY, int numEnemies){
        int numCircles = (int)(numEnemies*0.25);
        if (numCircles > 0) {
            for (int i = 0; i < numCircles; i++) {
                circles.add(i, new Enemy_Circle(xStartPosition, yStartPosition, healthFactor, pixelsPerMeter, omniGonPosX, omniGonPosY));
            }
        }
        int numTriangles = (int)(numEnemies*0.5);
        if (numTriangles > 0) {
            for (int i = 0; i < numTriangles; i++) {
                triangles.add(i, new Enemy_Triangle(xStartPosition, yStartPosition, healthFactor, pixelsPerMeter, omniGonPosX, omniGonPosY));
            }
        }
        int numSquares = (numEnemies - (numCircles + numTriangles));
        if (numSquares > 0) {
            for (int i = 0; i < numSquares; i++) {
                squares.add(i, new Enemy_Square(xStartPosition, yStartPosition, healthFactor, pixelsPerMeter, omniGonPosX, omniGonPosY));
            }
        }
    }


    public void spawnEnemies(){
        if (enemyCounter < numEnemies){
            if (System.currentTimeMillis() >= spawnTime + timeBetweenEnemies){
                spawnTime = System.currentTimeMillis();
                if (enemyCounter < circles.size()){
                    circles.get(enemyCounter).isActive = true;
                }
                if (enemyCounter < squares.size()){
                    squares.get(enemyCounter).isActive = true;
                }
                if (enemyCounter < triangles.size()){
                    triangles.get(enemyCounter).isActive = true;
                }
                enemyCounter++;
            }
        }
    }


    public void setWaveComplete(){
        enemyCounter = 0;
        for (Enemy_Square enemySquare : squares){
            if (enemySquare.isDead){
                enemyCounter++;
            }
        }
        for (Enemy_Circle enemyCircle : circles){
            if (enemyCircle.isDead){
                enemyCounter++;
            }
        }
        for (Enemy_Triangle enemyTriangle : triangles){
            if (enemyTriangle.isDead){
                enemyCounter++;
            }
        }
        if (enemyCounter == numEnemies && !waveComplete){
            waveComplete = true;
            waveCompletionTime = System.currentTimeMillis();

        }
    }


    public void update(){

    }
}
