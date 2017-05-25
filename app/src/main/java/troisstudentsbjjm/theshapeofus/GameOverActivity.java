package troisstudentsbjjm.theshapeofus;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by MichealNedantsis on 2017-05-25.
 */

public class GameOverActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setup the UI layout as the view
        setContentView(R.layout.activity_gameover);
        SharedPreferences prefs;
        SharedPreferences.Editor editor;
        final Button buttonPlay = (Button)findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        // Create new Intent
        Intent i = new Intent(this, GameActivty.class);
        // Start the game activity
        startActivity(i);
        // shutdown this (main) activity
        finish();
    }
}
