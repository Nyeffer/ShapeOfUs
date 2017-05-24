package troisstudentsbjjm.theshapeofus;

import android.content.Context;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.Input.InputController;
import troisstudentsbjjm.theshapeofus.Level.LevelData;
import troisstudentsbjjm.theshapeofus.Primatives.GameObject;

/**
 * Created by mrber on 2017-05-15.
 */

//This class will take in a bunch of game objects from levelone then update and draw them in GameView

public class LevelManager {

    int mapWidth;
    int mapHeight;

    int playerIndex;

    private boolean playing = true;
    float gravity;

    LevelData levelData;
    ArrayList<GameObject> gameObjects;


    public LevelManager(Context context, int pixelsPerMeter, int screenWidth, InputController ic, String level, float px, float py){
    }

    public boolean isPlaying(){
        return playing;
    }


    public void switchPlayingStatus(){
        playing = !playing;
    }
}
