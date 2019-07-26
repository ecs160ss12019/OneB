package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.breakout.BOGameController;

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


}
