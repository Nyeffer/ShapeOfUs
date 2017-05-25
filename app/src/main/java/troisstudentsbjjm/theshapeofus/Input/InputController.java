package troisstudentsbjjm.theshapeofus.Input;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import java.util.ArrayList;

import troisstudentsbjjm.theshapeofus.GameView;
import troisstudentsbjjm.theshapeofus.Level.LevelManager;
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

    private int resources = 15;

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

    public void handleInput(MotionEvent motionEvent, LevelManager lm, GameView gv) {
        int pointerCount = motionEvent.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {
            int x = (int) motionEvent.getX(i);
            int y = (int) motionEvent.getY(i);


            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (towerMenu.isActive){
                        if (towerMenu.S_button.contains(x,y) && resources >= 4){
                            lm.square_towers.add(new Square_Tower((int)(buildBlocks.hitBlocks.get(towerIndex).location.x),(int)(buildBlocks.hitBlocks.get(towerIndex).location.y), pixelsPerMeter, towerIndex));
                            towerMenu.isActive = false;
                            buildBlocks.hitBlocks.get(towerIndex).isActive = false;
                            subtractResources(4);
                        } else if (towerMenu.S_button.contains(x,y) && resources < 4){
                            towerMenu.isActive = false;
                            gv.notEnoughResources = true;
                        } else if (towerMenu.T_button.contains(x,y) && resources >= 2){
                            lm.triangle_towers.add(new Triangle_Tower((int)(buildBlocks.hitBlocks.get(towerIndex).location.x),(int)(buildBlocks.hitBlocks.get(towerIndex).location.y), pixelsPerMeter));
                            towerMenu.isActive = false;
                            buildBlocks.hitBlocks.get(towerIndex).isActive = false;
                            subtractResources(2);
                        } else if (towerMenu.T_button.contains(x,y) && resources < 2){
                            towerMenu.isActive = false;
                            gv.notEnoughResources = true;
                        } else if (towerMenu.C_button.contains(x,y) && resources >= 10){
                            lm.circle_towers.add(new Circle_Tower((int)(buildBlocks.hitBlocks.get(towerIndex).location.x),(int)(buildBlocks.hitBlocks.get(towerIndex).location.y), pixelsPerMeter));
                            towerMenu.isActive = false;
                            buildBlocks.hitBlocks.get(towerIndex).isActive = false;
                            subtractResources(10);
                        } else if (towerMenu.C_button.contains(x,y) && resources < 10){
                            towerMenu.isActive = false;
                            gv.notEnoughResources = true;
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


    public void drawButtons(Canvas canvas, Paint paint, GameView gv){
        towerMenu.draw(canvas,paint);
        DrawPauseButton(canvas, paint, gv);
        DrawUpgradeButton(canvas, paint);
        DrawResourceText(canvas, paint);
    }

    public Rect UpgradeButton() {
        return upgradeButton;
    }

    public Rect PauseButton() {
        return pauseButton;
    }

    public void DrawPauseButton(Canvas canvas, Paint paint, GameView gv) {
        // Draws the pause button
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.argb(80, 255, 255, 255));
        Rect drawPause;
        drawPause = PauseButton();

        RectF rp = new RectF(drawPause.left, drawPause.top, drawPause.right, drawPause.bottom);
        canvas.drawRoundRect(rp, 15f, 15f, paint);

        if(gv.playing) {
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(64);
            canvas.drawText("Pause", drawPause.left + 25, drawPause.bottom - 50, paint);
        } else if(!gv.playing) {
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(64);
            canvas.drawText("Play", drawPause.left + 50, drawPause.bottom - 50, paint);
        }
    }

    public void DrawUpgradeButton(Canvas canvas, Paint paint) {
        // Draws the upgrade button
        paint.setTextAlign(Paint.Align.LEFT);
        // determines whether the button is active or not
        if(isUpgradeTapped()) {
            paint.setColor(Color.argb(180, 255, 255, 255));
        } else if(!isUpgradeTapped()) {
            paint.setColor(Color.argb(80, 255, 255, 255));
        }
        Rect drawUpgrade;
        drawUpgrade = UpgradeButton();

        RectF ru = new RectF(drawUpgrade.left, drawUpgrade.top, drawUpgrade.right, drawUpgrade.bottom);
        canvas.drawRoundRect(ru, 15f, 15f, paint);

        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(52);
        canvas.drawText("Upgrade", drawUpgrade.left + 15, drawUpgrade.bottom - 55, paint);

    }


    public void DrawResourceText(Canvas canvas, Paint paint) {
        paint.setTextSize(64);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.argb(255, 255, 0, 0));
        canvas.drawText("Resources: " + getResources(), 52, 60, paint);
    }

    public int getResources() {
        return resources;
    }

    public void addResources(int deadBodiesTaken) {
        resources += deadBodiesTaken;
    }

    public void subtractResources(int deadBodiesRecycled) {
        resources -= deadBodiesRecycled;
    }

    public boolean isUpgradeTapped() {
        return upgradeTap;
    }
}
