package com.example.breakout.States;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.breakout.BOBall;
import com.example.breakout.BOGameController;
import com.example.breakout.BONoPowerUp;
import com.example.breakout.BOPaddle;
import com.example.breakout.Point;
import com.example.breakout.R;

public class GameOverState extends State {

    public GameOverState(BOGameController gc) {
        super(gc);
        gc.mediaPlayer.pauseSoundtrack(); // don't stop it because then we run into syncing issues
        gc.mediaPlayer.playGameOver();

        // Write the high score to the database
        gc.myRef = gc.database.getReference("users/" + gc.user.nickname + "/score");
        gc.myRef.setValue(gc.score);
        gc.powerUp.time();

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
                Point dim = gc.getMeta().getDim();

                gc.ball = new BOBall(gc.getMeta().getDim('x'),  BitmapFactory.decodeResource(gc.resources.getResources(), R.drawable.ball));

                gc.paddle = new BOPaddle((int)dim.x,(int)dim.y, gc.resources);
                gc.ball.reset(gc.paddle);

                gc.powerUp = new BONoPowerUp(gc);
                gc.currentLevel = 1;
                gc.level = 1;
                gc.mBOGame.startNewGame();
                gc.context = new GameTransitionState(gc); // move to the waiting state instead of end state.
                gc.mediaPlayer.restartSoundtrack(); // this will make it so the song plays from the beginning.
                 // restart the music

        }
        return true;
    }
}


