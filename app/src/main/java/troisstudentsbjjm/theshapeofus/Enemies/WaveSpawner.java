package troisstudentsbjjm.theshapeofus.Enemies;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import troisstudentsbjjm.theshapeofus.Primatives.GameObject;
import troisstudentsbjjm.theshapeofus.Wave;

public class WaveSpawner extends GameObject{

    private long timeBetweenWaves = 9000;    //5 seconds
    private long timer;

    public Wave currentWave = null;
    public Wave nextWave = null;

    private int waveNumber = 1;
    private int countDown = 10;
    private int enemiesInWave = 3;
    private double enemySpawnRate = 5;             // 5 seconds
    private final double MIN_SPAWN_RATE = 0.5;
    private int pixelsPerMeter;
    private int omniGonPosX;
    private int omniGonPosY;
    private int nextWaveNumber = 2;

    private float difficultyScale = 1;

    public WaveSpawner(int xStartPosition, int yStartPosition, int pixelsPerMeter, int omniGonPosX, int omniGonPosY){
        this.pixelsPerMeter = pixelsPerMeter;
        this.omniGonPosX = omniGonPosX;
        this.omniGonPosY = omniGonPosY;

        location.set(xStartPosition,yStartPosition);

        currentWave = new Wave(xStartPosition, yStartPosition, difficultyScale, pixelsPerMeter, omniGonPosX, omniGonPosY, enemiesInWave, enemySpawnRate);
        nextWave = currentWave;
    }


    private void initWaves(){           //switches between waves once a wave is complete

        if (nextWave == currentWave) {
            enemiesInWave += 1;
            difficultyScale += 0.1;
            if (enemySpawnRate < MIN_SPAWN_RATE){
                enemySpawnRate = MIN_SPAWN_RATE;
            } else {
                enemySpawnRate *= 0.5;
            }
            nextWave = new Wave((int) location.x, (int) location.y, difficultyScale, pixelsPerMeter, omniGonPosX, omniGonPosY, enemiesInWave, enemySpawnRate);
        }
        if (System.currentTimeMillis() >= timer + 1000 && currentWave.waveComplete){
            timer = System.currentTimeMillis();
            countDown--;
        }
        if (System.currentTimeMillis() >= currentWave.waveCompletionTime + timeBetweenWaves && currentWave.waveComplete && currentWave != nextWave){
            currentWave = nextWave;
            waveNumber++;
            nextWaveNumber++;
            countDown = 10;
        }
    }


    public void update(){
        initWaves();
        currentWave.spawnEnemies();
        currentWave.setWaveComplete();
    }


    public void draw(Canvas canvas, Paint paint){
        paint.setTextSize(30);
        paint.setColor(Color.argb(255, 255, 255, 255));
        if (!currentWave.waveComplete) {
            canvas.drawText("Wave " + waveNumber, 825, 100, paint);
        } else {
            canvas.drawText("Wave  " + nextWaveNumber + " in "+ countDown + " seconds.", 825, 100, paint);
        }
    }
}
