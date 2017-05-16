package troisstudentsbjjm.theshapeofus;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class SoUActivity extends Activity {

    private GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //get device resolution
        Display display = getWindowManager().getDefaultDisplay();
        //create point type and store resolution in it
        Point resolution = new Point();
        display.getSize(resolution);
        //instantiate objectview from class
        gameView = new GameView(this,resolution.x,resolution.y);
        //set created view onto screen
        setContentView(gameView);
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
