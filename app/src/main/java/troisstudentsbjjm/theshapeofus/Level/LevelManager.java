package troisstudentsbjjm.theshapeofus.Level;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Enemies.WaveSpawner;
import troisstudentsbjjm.theshapeofus.Input.InputController;
import troisstudentsbjjm.theshapeofus.Towers.Circle_Tower;
import troisstudentsbjjm.theshapeofus.Towers.Square_Tower;
import troisstudentsbjjm.theshapeofus.Towers.Triangle_Tower;

/**
 * Created by mrber on 2017-05-24.
 */

public class LevelManager {

    int pixelsPerMeter;

    public ArrayList<Square_Tower> square_towers;
    public ArrayList<Triangle_Tower> triangle_towers;
    public ArrayList<Circle_Tower> circle_towers;

    public WaveSpawner spawner;

    public LevelManager (int xPos, int yPos, int pixelsPerMeter, int omniGonX, int omniGonY) {
        this.pixelsPerMeter = pixelsPerMeter;

        square_towers = new ArrayList<>();
        circle_towers = new ArrayList<>();
        triangle_towers = new ArrayList<>();

        spawner = new WaveSpawner(xPos,yPos,pixelsPerMeter,omniGonX,omniGonY);
    }


    public void update(InputController ic, long fps){
        spawner.update();
        for (Square_Tower blockade : square_towers) {
            blockade.update(spawner.currentWave.circles, spawner.currentWave.squares, spawner.currentWave.triangles, fps);
            if (!blockade.isActive && spawner.currentWave.waveComplete){
                ic.buildBlocks.hitBlocks.get(blockade.placementIndex).isActive = true;
            }
        }
        for (Circle_Tower shooter : circle_towers){
            shooter.update(spawner.currentWave.circles, spawner.currentWave.squares, spawner.currentWave.triangles, fps);
        }
        for (Triangle_Tower spikes : triangle_towers){
            spikes.update(spawner.currentWave.circles, spawner.currentWave.squares, spawner.currentWave.triangles, fps);
        }
        for (int i = 0; i < spawner.currentWave.squares.size(); i++) {
            if (i + 1 == spawner.currentWave.squares.size()) {
                spawner.currentWave.squares.get(i).update(spawner.currentWave.squares.get(0), pixelsPerMeter, fps);
            } else {
                spawner.currentWave.squares.get(i).update(spawner.currentWave.squares.get(i + 1), pixelsPerMeter, fps);
            }
            if (spawner.currentWave.squares.get(i).isDead){
                ic.addResources(spawner.currentWave.squares.get(i).value);
                spawner.currentWave.squares.get(i).value = 0;
            }
        }
        for (int i = 0; i < spawner.currentWave.circles.size(); i++) {
            if (i + 1 == spawner.currentWave.circles.size()) {
                spawner.currentWave.circles.get(i).update(spawner.currentWave.circles.get(0), pixelsPerMeter, fps);
            } else {
                spawner.currentWave.circles.get(i).update(spawner.currentWave.circles.get(i + 1), pixelsPerMeter, fps);
            }
            if (spawner.currentWave.circles.get(i).isDead) {
                ic.addResources(spawner.currentWave.circles.get(i).value);
                spawner.currentWave.circles.get(i).value = 0;
            }
        }
        for (int i = 0; i < spawner.currentWave.triangles.size(); i++) {
            spawner.currentWave.triangles.get(i).update(pixelsPerMeter, fps);
            if (spawner.currentWave.triangles.get(i).isDead) {
                ic.addResources(spawner.currentWave.triangles.get(i).value);
                spawner.currentWave.triangles.get(i).value = 0;
            }
        }
    }


    public void draw(Canvas canvas, Paint paint){
        spawner.draw(canvas,paint);
        for (Square_Tower tower : square_towers) {
            tower.draw(canvas, paint);
        }
        for (Circle_Tower shooter : circle_towers){
            shooter.draw(canvas,paint);
        }
        for (Triangle_Tower spikes : triangle_towers){
            spikes.draw(canvas,paint);
        }
        if (spawner.currentWave.squares.size() != 0){
            for (int i = 0; i < spawner.currentWave.squares.size(); i++){
                spawner.currentWave.squares.get(i).draw(canvas,paint);
            }
        }
        if (spawner.currentWave.triangles.size() != 0) {
            for (int i = 0; i < spawner.currentWave.triangles.size(); i++) {
                spawner.currentWave.triangles.get(i).draw(canvas, paint);
            }
        }
        if (spawner.currentWave.circles.size() != 0) {
            for (int i = 0; i < spawner.currentWave.circles.size(); i++) {
                spawner.currentWave.circles.get(i).draw(canvas, paint);
            }
        }
    }
}
