package com.example.breakout.States;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.breakout.BOGameController;
import com.example.breakout.BOObject;
import com.example.breakout.R;

public class BOLevelSelector extends State {

    float menuHeight;
    RectF collider;
    Bitmap sprite;

    public BOLevelSelector(BOGameController gc) {
        super(gc);

        float screenWidth = gc.getMeta().getDim('x');
        float screenHeight = gc.getMeta().getDim('y');

        float menuWidth = screenWidth / (float) 1.75;
        this.menuHeight = screenHeight / (float) 1.75;


        collider = new RectF((screenWidth / 2) - (menuWidth / 3), (screenHeight / 2) - ((float) (menuHeight / 1.5)), (screenWidth / 2) + (menuWidth / 3), (screenHeight / 2) + ((float) (menuHeight / 1.5)));
        sprite = BitmapFactory.decodeResource(gc.resources.getResources(), R.drawable.leaderboard);
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        // draw the pause menu if pause state and running
        gc.myLayout.draw(mCanvas, mPaint); // draw the background over the blocks.
        mCanvas.drawBitmap(sprite, null, collider, mPaint);



    }

    public void run() {
        // What should pause do while the game is running?
    }

    public void update() {
        // does nothing for now, but can be used as a 'listener' later
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }
}