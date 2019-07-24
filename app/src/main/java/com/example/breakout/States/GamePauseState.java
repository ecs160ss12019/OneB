package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.breakout.BOGameController;

public class GamePauseState extends State {

    public GamePauseState(BOGameController gc) {
        super(gc);

    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        // draw the pause menu if pause state and running
        gc.myLayout.draw(mCanvas, mPaint); // draw the background over the blocks.
        gc.menu.draw(mCanvas, mPaint);
    }

    public void run() {
        // What should pause do while the game is running?
    }

    public void update() {
        // does nothing for now, but can be used as a 'listener' later
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: //placed finger on screen
                if(gc.firstStart){
                    gc.firstStart = false;
                    gc.context = new GameWaitingState(gc); // TODO: temp, once we get our gametitlestate working we can remove.
                    return true;
                }
                gc.context = new GameRunningState(gc);

        }
        return true;
    }
}
