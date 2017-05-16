package troisstudentsbjjm.theshapeofus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Input.InputController;
import troisstudentsbjjm.theshapeofus.Level.LevelData;

/**
 * Created by mrber on 2017-05-15.
 */

//This class will take in a bunch of game objects from levelone then update and draw them in GameView

public class LevelManager {

    private String level;

    int mapWidth;
    int mapHeight;

    int playerIndex;

    private boolean playing;
    float gravity;

    LevelData levelData;
    ArrayList<GameObject> gameObjects;

    Bitmap[] bitmapsArray;

    public LevelManager(Context context, int pixelsPerMeter, int screenWidth, InputController ic, String level, float px, float py){

        this.level = level;


        gameObjects = new ArrayList<>();

        bitmapsArray = new Bitmap[25];


        // playing = true;
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
            case 'c':
                index = 3;
                break;
            case 'u':
                index = 4;
                break;
            case 'e':
                index = 5;
                break;
            case 'd':
                index = 6;
                break;
            case 'g':
                index = 7;
                break;
            case 'f':
                index = 8;
                break;
            case '2':
                index = 9;
                break;
            case '3':
                index = 10;
                break;
            case '4':
                index = 11;
                break;
            case '5':
                index = 12;
                break;
            case '6':
                index = 13;
                break;
            case '7':
                index = 14;
                break;
            case 'w':
                index = 15;
                break;
            case 'x':
                index = 16;
                break;
            case 'l':
                index = 17;
                break;
            case 'r':
                index = 18;
                break;
            case 's':
                index = 19;
                break;
            case 'm':
                index = 20;
                break;
            case 'z':
                index = 21;
                break;
            case 't':
                index = 22;
                break;
            default:
                index = 0;
                break;
        }

        return bitmapsArray[index];
    }



//    private void loadMapData(Context context, int pixelsPerMeter, float px, float py){
//
//        char c;
//
//        int currentIndex = -1;
//        int teleportIndex = -1;
//
//        mapHeight = levelData.terrain.size();
//        mapWidth = levelData.terrain.get(0).length();
//
//        for (int i = 0; i < levelData.terrain.size(); i++){
//
//            for (int j = 0; j < levelData.terrain.get(i).length(); j++){
//
//                c = levelData.terrain.get(i).charAt(j);
//
//                if (c != '.'){
//
//                    currentIndex++;
//                    switch (c) {
//                        case '1':
//                            break;
//                    }
//                }
//            }
//        }
//    }


    public void switchPlayingStatus(){
        playing = !playing;

        if (playing){
            gravity = 6;
        } else {
            gravity = 0;

        }
    }
}
