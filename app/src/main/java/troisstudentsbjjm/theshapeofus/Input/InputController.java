package troisstudentsbjjm.theshapeofus.Input;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.LevelManager;
import troisstudentsbjjm.theshapeofus.Viewport;

/**
 * Created by mrber on 2017-05-15.
 * Edited by Braedon Jolie 2017-05-16.
 */

//We can implement a joystick controller class down the line

public class InputController {
    // if we are going to have inputController keep track of tower tapping then isTapping will track if a tower has been tapped
    // upgradeTap will determine whether the upgrade button is active or not.
    public boolean isTapping;
    public boolean upgradeTap;
    public boolean dragging;

    Rect upgradeButton;
    Rect pauseButton;

    public InputController(int screenX, int screenY) {
        // Configure the player buttons
        int buttonWidth = screenX / 8;
        int buttonHeight = screenY / 7;
        int buttonPadding = screenX / 80;

        // Note: Rect = left, top, right, bottom

        upgradeButton = new Rect(screenX - buttonPadding - buttonWidth,
                buttonPadding + buttonHeight + buttonPadding,
                screenX - buttonPadding,
                buttonPadding + buttonHeight + buttonPadding + buttonHeight);

        pauseButton = new Rect(screenX - buttonPadding - buttonWidth,
                buttonPadding,
                screenX - buttonPadding,
                buttonPadding + buttonHeight);

        upgradeTap = true;
    }

    public void handleInput(MotionEvent motionEvent, LevelManager l, Viewport vp) {
        int pointerCount = motionEvent.getPointerCount();
        float dX = 0;
        float dY = 0;

        for(int i = 0; i < pointerCount; i++) {
            int x = (int) motionEvent.getX(i);
            int y = (int) motionEvent.getY(i);

            if(l.isPlaying()) {
                switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        if(pauseButton.contains(x, y)) {
                            l.switchPlayingStatus();
                        } else if(upgradeButton.contains(x, y)) {
                            if(upgradeTap == true) {
                                upgradeTap = false;
                            } else if(upgradeTap == false) {
                                upgradeTap = true;
                            }
                        } //else {
                            // pan the screen here
                            //dX = vp.getViewportWorldCenterX() - motionEvent.getRawX();
                            //dY = vp.getViewportWorldCenterY() - motionEvent.getRawY();
                            // isTapping = true;
                        //}
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //vp.setWorldCenter(dX + motionEvent.getRawX(), dY + motionEvent.getRawY());
                        if(motionEvent.getRawX() > 0) {
                            vp.moveViewPortLeft(motionEvent.getRawX());
                        }
                        if(motionEvent.getRawX() < 0) {
                            vp.moveViewPortRight(vp.getScreenWidth(), motionEvent.getRawX());
                        }
                        if(motionEvent.getRawY() > 0) {
                            vp.moveViewPortUp(motionEvent.getRawY());
                        }
                        if(motionEvent.getRawY() < 0) {
                            vp.moveViewPortDown(vp.getScreenHeight(), motionEvent.getRawY());
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        // isTapping = false;
                        break;
                } // End of switch
            } else { // Not playing
                // Move the viewport around to explore the map
                switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        if(pauseButton.contains(x, y)) {
                            l.switchPlayingStatus();
                        }
                        break;
                }
            }
        }
    }

    public ArrayList getButtons() {
        // create an array of buttons for the draw method
        ArrayList<Rect> currentButtonList = new ArrayList<>();
        currentButtonList.add(upgradeButton);
        currentButtonList.add(pauseButton);
        return currentButtonList;
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
