package com.example.breakout.States;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.breakout.BOGameController;
import com.example.breakout.BOLayout;
import com.example.breakout.Point;
import com.example.breakout.R;

public class GameWonState extends State {

    BOLayout winScreen;
    public GameWonState(BOGameController gc) {
        super(gc);

    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        // draw the New Game menu if in won state
        gc.media.pause();
        gc.media_won.start();
        gc.myLayout.draw(mCanvas, mPaint); // draw the background over the blocks.
        mPaint.setTextSize(100);
        Point dim = gc.getMeta().getDim();

        mCanvas.drawText("You Win!",(dim.x / (float)3.5) ,dim.y / (float)2, mPaint);
    }

    public void run() {
        // What should wonState do while the game is running?
    }

    public void update() {
        // does nothing for now, but can be used as a 'listener' later
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: //placed finger on screen
                gc.media.seekTo(0); // this will make it so the song plays from the begining.
                gc.currentLevel++; // increment current level
                gc.media.start(); // restart the music
                gc.level++; // increase the level for power-ups
                gc.mBOGame.startNewGame();
                gc.context = new GameWaitingState(gc);
        }
        return true;
    }
}
