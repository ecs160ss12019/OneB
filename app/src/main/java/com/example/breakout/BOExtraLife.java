package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BOExtraLife extends BOPowerUp {

    public BOGameController gc;

    public BOExtraLife (BOGameController gc){
        this.gc = gc;
        gc.lives++;
    }

    public void draw(Canvas mCanvas, Paint mPaint)
    {
        Point dim = gc.getMeta().getDim();

        mCanvas.drawText(this.type(), dim.x / 55,dim.y / 4, mPaint);
    }

    @Override
    public void apply(BOGameController gc) {
        return;
    }
    public String type(){
        return "Extra Life";
    }

    public BOObject getMember() {
        return null;
    }

    @Override
    public void time() {
        // don't do anything
    }
}

