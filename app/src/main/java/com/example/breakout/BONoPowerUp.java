package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BONoPowerUp extends BOPowerUp {

    public BOGameController gc;

    public BONoPowerUp (BOGameController gc) {
        this.gc = gc;
    }

    @Override
    public void apply(BOGameController gc) {
        // does nothing.
    }
    public String type(){
        return "No PowerUp";
    }

    public BOObject getMember() {
        return null;
    }

    @Override
    public void time() {

    }

    public void draw(Canvas mCanvas, Paint mPaint)
    {
        Point dim = gc.getMeta().getDim();

        mCanvas.drawText("", dim.x / 55,dim.y / 4, mPaint);
    }
}

