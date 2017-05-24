package troisstudentsbjjm.theshapeofus.Input;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.GameView;
import troisstudentsbjjm.theshapeofus.LevelManager;
import troisstudentsbjjm.theshapeofus.Primatives.Square;
import troisstudentsbjjm.theshapeofus.Towers.Circle_Tower;
import troisstudentsbjjm.theshapeofus.Towers.Square_Tower;
import troisstudentsbjjm.theshapeofus.Towers.Triangle_Tower;


//We can implement a joystick controller class down the line

public class InputController {
    // if we are going to have inputController keep track of tower tapping then isTapping will track if a tower has been tapped
    // upgradeTap will determine whether the upgrade button is active or not.
    public boolean isTapping;
    public boolean upgradeTap;
    public boolean dragging;

    private int pixelsPerMeter;
    private int towerIndex;

    private long timeTowerPlaced;

    Rect upgradeButton;
    Rect pauseButton;

    private TowerMenu towerMenu;
    public BuildBlocks buildBlocks;

    public InputController(int screenX, int screenY, int pixelsPerMeter) {
        this.pixelsPerMeter = pixelsPerMeter;
        // Configure the player buttons
        int buttonWidth = screenX / 8;
        int buttonHeight = screenY / 7;
        int buttonPadding = screenX / 80;

        upgradeButton = new Rect(screenX - buttonPadding - buttonWidth,
                buttonPadding + buttonHeight + buttonPadding,
                screenX - buttonPadding,
                buttonPadding + buttonHeight + buttonPadding + buttonHeight);

        pauseButton = new Rect(screenX - buttonPadding - buttonWidth,
                buttonPadding,
                screenX - buttonPadding,
                buttonPadding + buttonHeight);

        buildBlocks = new BuildBlocks(0,screenX,screenY/2 + pixelsPerMeter,pixelsPerMeter);
        towerMenu = new TowerMenu();

        upgradeTap = true;
    }

    public void handleInput(MotionEvent motionEvent, GameView gv) {
        int pointerCount = motionEvent.getPointerCount();
        float dX = 0;
        float dY = 0;

        for (int i = 0; i < pointerCount; i++) {
            int x = (int) motionEvent.getX(i);
            int y = (int) motionEvent.getY(i);


            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (towerMenu.isActive){
                        if (towerMenu.S_button.contains(x,y)){
                            gv.square_towers.add(new Square_Tower((int)(buildBlocks.hitBlocks.get(towerIndex).location.x),(int)(buildBlocks.hitBlocks.get(towerIndex).location.y), pixelsPerMeter, towerIndex));
                            towerMenu.isActive = false;
                            buildBlocks.hitBlocks.get(towerIndex).isActive = false;
                        } else if (towerMenu.T_button.contains(x,y)){
                            gv.triangle_towers.add(new Triangle_Tower((int)(buildBlocks.hitBlocks.get(towerIndex).location.x),(int)(buildBlocks.hitBlocks.get(towerIndex).location.y), pixelsPerMeter));
                            towerMenu.isActive = false;
                            buildBlocks.hitBlocks.get(towerIndex).isActive = false;
                        } else if (towerMenu.C_button.contains(x,y)){
                            gv.circle_towers.add(new Circle_Tower((int)(buildBlocks.hitBlocks.get(towerIndex).location.x),(int)(buildBlocks.hitBlocks.get(towerIndex).location.y), pixelsPerMeter));
                            towerMenu.isActive = false;
                            buildBlocks.hitBlocks.get(towerIndex).isActive = false;
                        } else {
                            towerMenu.isActive = false;
                        }
                    }
                    if(pauseButton.contains(x, y)) {
                        gv.playing = !gv.playing;
                    } else if(upgradeButton.contains(x, y)) {
                        if(upgradeTap) {
                            upgradeTap = false;
                        } else if(!upgradeTap) {
                            upgradeTap = true;
                        }
                    }
                    if (!towerMenu.isActive) {
                        for (int j = 0; j < buildBlocks.hitBlocks.size(); j++) {
                            if (buildBlocks.hitBlocks.get(j).hitBox.contains(x, y)) {
                                if (buildBlocks.hitBlocks.get(j).isActive) {
                                    towerMenu.moveAndDisplay(x, y);
                                    towerIndex = j;
                                }
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:

                    break;
            } // End of switch
        }
    }


    public ArrayList getButtons() {
        ArrayList<Rect> currentButtonList = new ArrayList<>();
        currentButtonList.add(upgradeButton);
        currentButtonList.add(pauseButton);
        return currentButtonList;
    }


    public void drawButtons(Canvas canvas, Paint paint){
        towerMenu.draw(canvas,paint);
    }

    public Rect UpgradeButton() {
        return upgradeButton;
    }

    public Rect PauseButton() {
        return pauseButton;
    }


    public boolean isUpgradeTapped() {
        return upgradeTap;
    }
}
