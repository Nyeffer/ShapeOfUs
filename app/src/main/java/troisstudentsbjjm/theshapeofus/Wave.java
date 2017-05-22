package troisstudentsbjjm.theshapeofus;


import java.util.ArrayList;
import java.util.Random;

import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Circle;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Square;
import troisstudentsbjjm.theshapeofus.Enemies.Enemy_Triangle;

public class Wave {

    public ArrayList<Enemy_Circle> circles;             //holds all the circles
    public ArrayList<Enemy_Square> squares;             //holds all the squares
    public ArrayList<Enemy_Triangle> triangles;         //holds all the triangles

    public int numEnemies;
    private int enemyCounter = 0;

    private long timeBetweenEnemies;
    private long spawnTime;

    public boolean waveComplete;

    Random gen = new Random();

    Wave(int xStartPosition, int yStartPosition, float healthFactor, int pixelsPerMeter, int omniGonPosX, int omniGonPosY, int numEnemies, double timeBetweenEnemies){
        this.timeBetweenEnemies = (long)timeBetweenEnemies*1000;
        this.numEnemies = numEnemies;

        circles = new ArrayList<>();
        squares = new ArrayList<>();
        triangles = new ArrayList<>();

        waveComplete = false;

        initArrays(xStartPosition, yStartPosition, healthFactor, pixelsPerMeter, omniGonPosX, omniGonPosY, numEnemies);
    }


    private void initArrays(int xStartPosition, int yStartPosition, float healthFactor, int pixelsPerMeter, int omniGonPosX, int omniGonPosY, int numEnemies){
        int numCircles = numEnemies/3;
        for (int i = 0; i < numCircles; i++){
            circles.add(i,new Enemy_Circle(xStartPosition,yStartPosition,healthFactor,pixelsPerMeter,omniGonPosX,omniGonPosY));
        }
        int numSquares = numEnemies/2;
        for (int i = 0; i < numSquares; i++){
            squares.add(i,new Enemy_Square(xStartPosition,yStartPosition,healthFactor,pixelsPerMeter,omniGonPosX,omniGonPosY));
        }
        int numTriangles = numEnemies-(numCircles+numSquares);
        for (int i = 0; i < numTriangles; i++){
            triangles.add(i,new Enemy_Triangle(xStartPosition,yStartPosition,healthFactor,pixelsPerMeter,omniGonPosX,omniGonPosY));
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
}