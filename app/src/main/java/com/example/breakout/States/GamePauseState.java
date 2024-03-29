package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.breakout.BOGameController;
import com.example.breakout.BOMenuButton;
import com.example.breakout.Point;

public class GamePauseState extends State {
    /*
    The pause state handles logic when the game is paused. This includes things like rendering the menu buttons
    and giving touch. But mb I should refactor this. Comment here to remind me.
     */

    private BOMenuButton resumeButton;
    private BOMenuButton restartButton;
    private BOMenuButton levelButton;
    private BOMenuButton exitButton;
    private BOMenuButton leaderButton;

    public GamePauseState(BOGameController gc) {

        super(gc);

        Point dim = gc.getMeta().getDim();

        resumeButton = new BOMenuButton((int)dim.x, (int)dim.y, "           Resume", gc, gc.menu.collider.top);
        restartButton = new BOMenuButton((int)dim.x, (int)dim.y, "         Restart", gc, (gc.menu.collider.top + dim.y/(float)7.5));
        levelButton = new BOMenuButton((int)dim.x, (int)dim.y,  "           Levels", gc, (gc.menu.collider.top + (dim.y/(float)7.5)*2));
        leaderButton = new BOMenuButton((int)dim.x, (int)dim.y, "Leaderboard", gc, (gc.menu.collider.top + (dim.y/(float)7.5)*3));
        exitButton = new BOMenuButton((int)dim.x, (int)dim.y,  "               Exit", gc, (gc.menu.collider.top + (dim.y/(float)7.5)*4));

    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        // draw the pause menu if pause state and running
        gc.myLayout.draw(mCanvas, mPaint); // draw the background over the blocks.
        gc.menu.draw(mCanvas, mPaint);

        //draw the resume button
        resumeButton.draw(mCanvas, mPaint);
        //draw the restart button
        restartButton.draw(mCanvas, mPaint);
        //draw the level button
        levelButton.draw(mCanvas, mPaint);
        //draw the leaderboard button
        leaderButton.draw(mCanvas, mPaint);
        //draw the exit button
        exitButton.draw(mCanvas, mPaint);

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
                    gc.levelSelector.selectLevel(gc.currentLevel);
                }
                if (motionEvent.getX() > restartButton.collider.left && motionEvent.getX() < restartButton.collider.right && motionEvent.getY() < restartButton.collider.bottom
                        && motionEvent.getY() > restartButton.collider.top) {
                    gc.mBOGame.startNewGame();
                    gc.context = new GameWaitingState(gc);
                }
                if (motionEvent.getX() > exitButton.collider.left && motionEvent.getX() < exitButton.collider.right && motionEvent.getY() < exitButton.collider.bottom
                        && motionEvent.getY() > exitButton.collider.top) {
                    gc.closeApplication(gc.mBOGame);

                }
                if (motionEvent.getX() > levelButton.collider.left && motionEvent.getX() < levelButton.collider.right && motionEvent.getY() < levelButton.collider.bottom
                        && motionEvent.getY() > levelButton.collider.top) {
                    gc.context = new BOLevelSelector(gc);
                }
                if (motionEvent.getX() > leaderButton.collider.left && motionEvent.getX() < leaderButton.collider.right && motionEvent.getY() < leaderButton.collider.bottom
                        && motionEvent.getY() > leaderButton.collider.top) {
                    gc.context = new GameLBState(gc);

                }


        }
        return true;
    }
}
