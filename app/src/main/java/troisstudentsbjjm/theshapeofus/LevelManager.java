package troisstudentsbjjm.theshapeofus;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Input.InputController;
import troisstudentsbjjm.theshapeofus.Level.LevelData;
import troisstudentsbjjm.theshapeofus.Level.LevelOne;
import troisstudentsbjjm.theshapeofus.LevelMaterials.WhiteTile;
import troisstudentsbjjm.theshapeofus.Primatives.GameObject;

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

    Bitmap[] bitmapsArray;

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

        loadMapData(context, pixelsPerMeter, px, py);
        //loadBackgrounds(context, pixelsPerMeter, screenWidth);
    }

    public boolean isPlaying(){

        return playing;
    }


    public Bitmap getBitmap(char blockType){

        int index;

        switch (blockType){
            case '.':
                index = 0;
                break;
            case '1':
                index = 1;
                break;
            case 'p':
                index = 2;
                break;
            case 'b':
                index = 3;
                break;
            case 's':
                index = 4;
                break;
            case 't':
                index = 5;
                break;
            case 'c':
                index = 6;
                break;
            default:
                index = 0;
                break;
        }

        return bitmapsArray[index];
    }

    public int getBitmapIndex(char blockType){

        int index;

        switch (blockType){
            case '.':
                index = 0;
                break;
            case '1':
                index = 1;
                break;
            case 'p':
                index = 2;
                break;
            case 'b':
                index = 3;
                break;
            case 's':
                index = 4;
                break;
            case 't':
                index = 5;
                break;
            case 'c':
                index = 6;
                break;
            default:
                index = 0;
                break;
        }

        return index;
    }



    private void loadMapData(Context context, int pixelsPerMeter, float px, float py){

        char c;

        int currentIndex = -1;

        mapHeight = levelData.terrain.size();
        mapWidth = levelData.terrain.get(0).length();

        for (int i = 0; i < levelData.terrain.size(); i++){

            for (int j = 0; j < levelData.terrain.get(i).length(); j++) {

                c = levelData.terrain.get(i).charAt(j);

                if (c != '.') {

                    currentIndex++;
                    switch (c) {
                        case '1':
                            // Add the floor tiles
                            gameObjects.add(new WhiteTile(j, i, c));
                            break;
                        /*case 'p':
                            // Add the player's main base
                            break;
                        case 'b':
                            // Add a blank tower
                            break;
                        case 's':
                            // Add square (spawner perhaps)
                            break;
                        case 't':
                            // Add triangle
                            break;
                        case 'c':
                            // Add circle
                            break;*/
                    }

                    // look to ensure that the indexed bitmap has already been loaded
                    if (bitmapsArray[getBitmapIndex(c)] == null) {
                        bitmapsArray[getBitmapIndex(c)] = gameObjects.get(currentIndex).prepareBitmap(context, gameObjects.get(currentIndex).getBitmapName(), pixelsPerMeter);
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
