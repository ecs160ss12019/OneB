package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.breakout.BOGameController;
import com.example.breakout.BOMenuButton;

public class GamePauseState extends State {

    BOMenuButton resumeButton;
    BOMenuButton restartButton;

    public GamePauseState(BOGameController gc) {
        super(gc);
        resumeButton = new BOMenuButton(gc.mScreenX, gc.mScreenY, "Resume", gc, gc.menu.collider.top);
        restartButton = new BOMenuButton(gc.mScreenX, gc.mScreenY, "Restart", gc, (gc.menu.collider.top + gc.mScreenY/(float)7.5));


    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        // draw the pause menu if pause state and running
        gc.myLayout.draw(mCanvas, mPaint); // draw the background over the blocks.
        gc.menu.draw(mCanvas, mPaint);

        //draw the resume button
        resumeButton.draw(mCanvas, mPaint);
        //draw the restart button
        restartButton.draw(mCanvas, mPaint);

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
                if (motionEvent.getX() > resumeButton.collider.left && motionEvent.getX() < resumeButton.collider.right && motionEvent.getY() < resumeButton.collider.bottom
                        && motionEvent.getY() > resumeButton.collider.top) {
                    gc.context = new GameRunningState(gc);
                }

        }
        return true;
    }
}
