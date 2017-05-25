package troisstudentsbjjm.theshapeofus;

/**
 * Created by MichealNedantsis on 2017-05-23.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameActivty extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point resolution = new Point();
        display.getSize(resolution);

        gameView = new GameView(this, resolution.x, resolution.y);

        setContentView(gameView);
    }

    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        if (KeyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return true;
    }

    @Override
    protected void onPause(){
        //call Android system onPause
        super.onPause();
        //call class platformview pause()
        gameView.pause();

    }


    @Override
    protected void onResume(){
        //call Android system onResume
        super.onResume();
        //call class platformview resume()
        gameView.resume();
    }


}
