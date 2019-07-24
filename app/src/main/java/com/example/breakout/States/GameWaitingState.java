package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import com.example.breakout.BOGame;
import com.example.breakout.BOGameController;

public class GameWaitingState extends State{
    /*
    This is the state that the game gets set to when the game needs to 'wait' for the
    user to do something. This is different from the pause state as the pause state is
    entered when the user wants to access the menu. This state is typically accessed from
    the GameRunningState when the player loses a life.
     */

    public GameWaitingState(BOGameController gc){
        super(gc);
        gc.ball.reset();
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        new GameRunningState(gc).draw(mCanvas, mPaint); // this is good practice?? LOL Probably not, but its useful.
        mPaint.setTextSize(gc.mScreenX / 15);
        mCanvas.drawText("Tap Anywhere To Launch!",(gc.mScreenX / (float)6.6) ,gc.mScreenY / (float)1.5, mPaint); // TODO: move this to UI class? Also magic numbers FIX ME
    }

    public void update() {

    }

    public void run() {

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) { // get an action

            case MotionEvent.ACTION_DOWN: //placed finger on screen

                //TODO: this needs to be fixed so that a new game is started once the player beats all the levels
                //also this part is probably completely wrong
                //go to the next level in the level array here
                Log.d("DEBUG", "going to level:" + gc.currentLevel);
                if(gc.currentLevel <= gc.levels.length) {
                    gc.context = gc.levels[gc.currentLevel - 1]; //new Level1State(gc); // go to running state
                }
                else{
                    gc.currentLevel = 1;
                    gc.context = new Level1State(gc);
                }
                break;
        }
        return true;
    }
}
