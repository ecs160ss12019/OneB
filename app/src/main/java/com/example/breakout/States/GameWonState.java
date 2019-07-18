package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.breakout.BOGameController;

public class GameWonState extends State {

    public GameWonState(BOGameController gc) {
        super(gc);

    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        // draw the New Game menu if in won state
        gc.myLayout.draw(mCanvas, mPaint); // draw the background over the blocks.
        gc.menu.draw(mCanvas, mPaint);
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
