package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.breakout.BOGameController;
import com.example.breakout.Point;

public abstract class State {
    /*
        Abstract state class that all future states will inherit from
        Look here to see what must be included in a state class.

        Children state should be public due to directory dependencies being mad if not
     */
    public BOGameController gc;

    State(BOGameController gc) {
        this.gc = gc;
    }
    public abstract void draw(Canvas mCanvas, Paint mPaint);
    public abstract void run();
    public abstract void update();
    public abstract boolean onTouchEvent(MotionEvent motionEvent);
    public void drawUI(Canvas mCanvas, Paint mPaint) {
        Point dim = gc.getMeta().getDim();
        mCanvas.drawText("Level: " + gc.currentLevel,dim.x / 55,dim.y / 6, mPaint);
        mCanvas.drawText("Score: " + gc.score,dim.x / 55,dim.y / 9, mPaint); // TODO: move this to UI class?
        mCanvas.drawText("Lives: " + gc.lives,dim.x / 55,dim.y / 20, mPaint);
        gc.powerUp.draw(mCanvas, mPaint);
    }

}
