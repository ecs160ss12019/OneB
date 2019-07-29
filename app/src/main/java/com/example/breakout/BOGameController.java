package com.example.breakout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.example.breakout.States.GameInitState;
import com.example.breakout.States.Level1State;
import com.example.breakout.States.Level2State;
import com.example.breakout.States.Level3State;
import com.example.breakout.States.Level4State;
import com.example.breakout.States.State;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BOGameController extends Activity {

    // Actual member declarations
    public BOGame mBOGame; // Let's keep the book's style and if a variable
                            // shares it's name with a class, add an 'm' in front
                            // of it

    public int score = 0;
    public int lives = 3;

    private BOMetaData metaData;

    public BOUser user;

    //variable that decides how long a username can be
    public int userNameCharCap = 10;

    public int level;
    public int powerups;

    // GameObjects
    public BOPaddle paddle;
    public BOBall ball;
    public BOBall ball2; // only draw when power-up is true
    public ArrayList<BOBlock> blocks;
    public BOLeaderboard leaderboard;

    public BOLayout myLayout;
    public BOLayout gameOver;

    public BOMediaPlayer mediaPlayer;

    // Power ups
    public boolean doubleBallPowerUp = false;

    // Testing the timer
    public BOTimer timer = new BOTimer();
    public boolean won;

    public BOMenu menu;
    public BOPauseButton pauseButton;

    // Database connection
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef;

    // Context controller
    public State context;
    public Context resources; // allows us to grab resources from the
                              // drawables folder without needing to do it in BOGame
                              // lets objects initialize sprites essentially.


    public State levels[];
    public int currentLevel = 1;

    // level descriptions for the transition states
    public String levelDesc[] = {"A  Simple  Breakout  Game.", "Don't be confined.", "the third one", "things are backwards"};



    //TODO: Think about how many states we really need

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Code adapted from our Java text.
        // Comment here just to please Gabby ;)

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        resources = this; // set the resources so other classes can grab them.
        metaData = new BOMetaData(size.x, size.y, size.x/20, size.x/75 ); //// By default we will use 5%(1/20th) of screen width for font size, // Margin will be 1.5% (1/75th) of screen width

        // These must be before mBOGame. Otherwise the blocks are already instantiated.
        level = 1;
        powerups = 0;

        mBOGame = new BOGame(this, this, size.x, size.y);



        //initialize sound effects
//        media = MediaPlayer.create(this, R.raw.game_soundtrack);
//
//        media_won = MediaPlayer.create(this, R.raw.you_won);
//
//        media_lost = MediaPlayer.create(this, R.raw.game_over);
        mediaPlayer = new BOMediaPlayer(this);


        setContentView(mBOGame);

        Log.d("DEBUG: ", "CREATE");

        context = new GameInitState(this);

        levels = new State[] {new Level1State(this), new Level2State(this), new Level3State(this), new Level4State(this)};

    }

    @Override
    protected void onResume() {
        super.onResume();
        mBOGame.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DEBUG: ", "CREATE");
        mBOGame.pause();
    }

    long userInteractionTime = 0;

    @Override
    public void onUserInteraction() {
        userInteractionTime = System.currentTimeMillis();
        super.onUserInteraction();
        Log.i("appname","Interaction");
    }


    @Override
    public void onUserLeaveHint() {
        super.onUserLeaveHint();
        mediaPlayer.pauseSoundtrack();
    }

    public void closeApplication(View view) {
        finish();
        mediaPlayer.stopSoundtrack();
        moveTaskToBack(true);
    }

    public BOMetaData getMeta() {
        return metaData;
    }

}
