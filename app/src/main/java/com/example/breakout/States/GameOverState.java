package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.breakout.BOGameController;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GameOverState extends State {

    public GameOverState(BOGameController gc) {
        super(gc);
        gc.media.pause(); // don't stop it because then we run into syncing issues
        gc.media_lost.start();


        // Write the high score to the database
        //TODO: write high score, not every score... get user name/email to use as path
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("micheleter");

        myRef.setValue(gc.score);

    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        // draw the game over message if player lost
        gc.myLayout.draw(mCanvas, mPaint); // draw the background over the blocks.
        gc.gameOver.draw(mCanvas, mPaint); // draw message over the blocks
    }


    public void run() {
        // What should gameover do while the game is running?
    }

    public void update() {
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {


            case MotionEvent.ACTION_DOWN: //placed finger on screen
                // Reset lives
                gc.lives = 3;
                gc.context = new GameWaitingState(gc); // move to the waiting state instead of end state.
                gc.media.seekTo(0); // this will make it so the song plays from the begining.
                gc.media.start(); // restart the music

        }
        return true;
    }
}


