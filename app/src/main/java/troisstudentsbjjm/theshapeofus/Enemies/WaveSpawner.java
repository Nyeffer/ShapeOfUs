package troisstudentsbjjm.theshapeofus.Enemies;

import troisstudentsbjjm.theshapeofus.Primatives.GameObject;
import troisstudentsbjjm.theshapeofus.Wave;

public class WaveSpawner extends GameObject{

    private long timeBetweenWaves = 5000;    //5 seconds

    public Wave startWave;
    public Wave currentWave;
    public Wave nextWave;

    private int countDown = 5;
    private int enemiesInWave = 3;
    private int enemySpawnRate = 5;             // 5 seconds
    private int pixelsPerMeter;
    private int omniGonPosX;
    private int omniGonPosY;

    private float difficultyScale = 1;

    public WaveSpawner(int xStartPosition, int yStartPosition, int pixelsPerMeter, int omniGonPosX, int omniGonPosY){
        this.pixelsPerMeter = pixelsPerMeter;
        this.omniGonPosX = omniGonPosX;
        this.omniGonPosY = omniGonPosY;

        location.set(xStartPosition,yStartPosition);

        startWave = new Wave(xStartPosition, yStartPosition, difficultyScale, pixelsPerMeter, omniGonPosX, omniGonPosY, enemiesInWave, enemySpawnRate);
    }


    private void initWaves(){           //switches between waves once a wave is complete
        if (currentWave == null){       //game has just started
            currentWave = startWave;
        }
        if (currentWave.waveComplete && nextWave == null){
            nextWave = new Wave((int)location.x,(int)location.y,difficultyScale,pixelsPerMeter,omniGonPosX,omniGonPosY,enemiesInWave,enemySpawnRate);
        } else if (currentWave.waveComplete && System.currentTimeMillis() >= currentWave.waveCompletionTime + timeBetweenWaves){
            currentWave = nextWave;
            nextWave = null;
        }
    }
}
