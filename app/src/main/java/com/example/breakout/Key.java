package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Key {
    char key;
    RectF box;

    Key(char k, RectF b) {
        key = k;
        box = b;
    }

    void draw(Canvas mCanvas, Paint mPaint){
        mPaint.setColor(Color.argb(255,183,81,111));
        mCanvas.drawRect(box, mPaint);
        mPaint.setColor(Color.argb(255,255,255,255));
        mCanvas.drawText(""+key, box.left + box.width()/3, box.bottom - box.height()/3, mPaint);
    }

}
