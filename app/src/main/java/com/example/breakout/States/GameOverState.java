package com.example.breakout.States;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.app.ActivityCompat;

import com.example.breakout.BOGameController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.ACCOUNT_SERVICE;

public class GameOverState extends State {

    public GameOverState(BOGameController gc) {
        super(gc);
        gc.mediaPlayer.pauseSoundtrack(); // don't stop it because then we run into syncing issues
        gc.mediaPlayer.playGameOver();


        // Write the high score to the database
        //TODO: write high score, not every score... get user name/email to use as path
//        gc.myRef = gc.database.getReference(gc.user.nickname);
        gc.myRef = gc.database.getReference("Michel");
        gc.myRef.setValue(gc.score);


    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        // draw the game over message if player lost
        gc.myLayout.draw(mCanvas, mPaint); // draw the background over the blocks.
        gc.gameOver.draw(mCanvas, mPaint); // draw message over the blocks
    }


    public void run() {
        // What should game-over do while the game is running?
    }

    public void update() {
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {


            case MotionEvent.ACTION_DOWN: //placed finger on screen
                gc.mBOGame.startNewGame();
                gc.context = new GameWaitingState(gc); // move to the waiting state instead of end state.
                gc.mediaPlayer.restartSoundtrack(); // this will make it so the song plays from the beginning.
                 // restart the music

        }
        return true;
    }
}


