package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class BOMenuButton extends BOObject {
    float buttonWidth;
    float buttonHeight;
    private int screenWidth;
    private int screenHeight;
    private String text;

    public BOMenuButton(int screenWidth, int screenHeight, String text, BOGameController gc, float top) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.buttonWidth = this.screenWidth / (float)1.75;
        this.buttonHeight = this.screenHeight / (float)7.5;
        this.text = text;

        collider = new RectF( gc.menu.collider.left, top + gc.menu.menuHeight/4, gc.menu.collider.right, (top + gc.menu.menuHeight/4) + buttonHeight);

    }

    public void drawText(Canvas mCanvas, Paint mPaint) {

        Rect bounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        float x = (collider.left + collider.width() / (float)7.0);
        float y = ((collider.bottom - collider.height() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2)) ; // code stolen from : https://stackoverflow.com/questions/3630086/how-to-get-string-width-on-android

        mPaint.setTextSize(buttonHeight/(float)1.5);
        mPaint.setColor(Color.argb(255,255,255,255));
        mCanvas.drawText(text, x  , y, mPaint);
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        mPaint.setColor(Color.argb(255,222,113,144));
        mCanvas.drawRect(collider, mPaint);
        drawText(mCanvas, mPaint);

    }
}
