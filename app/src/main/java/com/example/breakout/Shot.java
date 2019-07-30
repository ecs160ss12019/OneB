package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Shot extends BOObject {

    boolean isFired;
    public Shot(float len, float height, Point pos){
        super(len,height,pos);
        isFired = false;
    }

    public boolean getFired() {
        return isFired;
    }

    public void fire() {
        isFired = true;
    }

    public void finish() {
        isFired = false;
    }

    public void update() {
        collider.top += 10;
        collider.bottom += 10;
    }

    @Override
    public void draw(Canvas mCanvas, Paint mPaint)
    {
        mPaint.setColor(Color.argb(255,102, 255, 102));
        if(collider != null)
            mCanvas.drawRect(collider, mPaint);
        mPaint.setColor(Color.argb(255,255, 255, 255));
    }

}
