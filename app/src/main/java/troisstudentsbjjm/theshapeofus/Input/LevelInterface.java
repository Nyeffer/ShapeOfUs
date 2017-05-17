package troisstudentsbjjm.theshapeofus.Input;

import android.view.MotionEvent;
import android.view.View;

import troisstudentsbjjm.theshapeofus.R;

/**
 * Created by Braedon Jolie on 2017-05-16.
 */

public class LevelInterface extends InputController {
    // Level Interface will have all the elements needed for the player to interface things in the level
    // Like panning/scaling the screen, upgrade button, etc...

    LevelInterface(View view) {
        //view.findViewById(R.id.upgrade_button).setOnTouchListener(UpgradeButton);
    }

    private class UpgradeButton implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();

            if(action == MotionEvent.ACTION_DOWN) {
                if(upgradeTap == true) {
                    upgradeTap = false;
                } else if (upgradeTap == false) {
                    upgradeTap = true;
                }
            } else if(action == MotionEvent.ACTION_UP) {

            }
            return true;
        }
    }
}
