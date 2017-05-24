package troisstudentsbjjm.theshapeofus;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Input.InputController;
import troisstudentsbjjm.theshapeofus.Level.LevelData;
import troisstudentsbjjm.theshapeofus.Level.LevelOne;
import troisstudentsbjjm.theshapeofus.LevelMaterials.WhiteTile;
import troisstudentsbjjm.theshapeofus.Primatives.GameObject;
import troisstudentsbjjm.theshapeofus.Primatives.Square;

/**
 * Created by mrber on 2017-05-15.
 */

//This class will take in a bunch of game objects from levelone then update and draw them in GameView

public class LevelManager {

    private String level;

    int mapWidth;
    int mapHeight;

    int playerIndex;

    private boolean playing = true;
    float gravity;

    LevelData levelData;
    ArrayList<GameObject> gameObjects;


    public LevelManager(Context context, int pixelsPerMeter, int screenWidth, InputController ic, String level, float px, float py){
        this.level = level;

        switch(level) {
            case "Level 1":
                levelData = new LevelOne();
                break;
            case "LevelCity":
                //levelData = new LevelCity();
                break;
            case "LevelForest":
                //levelData = new LevelForest();
                break;
            case "LevelMountain":
                //levelData = new LevelMountain();
                break;
        }

        // setup game objects
        gameObjects = new ArrayList<>();
        bitmapsArray = new Bitmap[5]; // holds one of each type
    }

    public boolean isPlaying(){

        return playing;
    }


    private void loadMapData(Context context, int pixelsPerMeter, float px, float py){

        char c;

        int currentIndex = -1;
        int teleportIndex = -1;

        mapHeight = levelData.terrain.size();
        mapWidth = levelData.terrain.get(0).length();

        for (int i = 0; i < levelData.terrain.size(); i++){
            for (int j = 0; j < levelData.terrain.get(i).length(); j++){
                c = levelData.terrain.get(i).charAt(j);
                if (c != '.'){
                    currentIndex++;
                    switch (c) {
                        case '1':
                            gameObjects.add(currentIndex, new Square());
                            gameObjects.get(currentIndex).location.set(j,i);
                            gameObjects.get(currentIndex).size = pixelsPerMeter;
                            break;
                    }
                }
            }
        }
    }


    public void switchPlayingStatus(){
        playing = !playing;

        if (playing){
            gravity = 6;
        } else {
            gravity = 0;

        }
    }
}
