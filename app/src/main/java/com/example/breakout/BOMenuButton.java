package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class BOMenuButton extends BOObject {
    float buttonWidth;
    float buttonHeight;
    int screenWidth;
    int screenHeight;
    float xPosition;
    float yPosition;
    String text;

    public BOMenuButton(int screenWidth, int screenHeight, String text, BOGameController gc) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.buttonWidth = this.screenWidth / (float)1.75;
        this.buttonHeight = this.screenHeight / (float)7.5;
        this.text = text;

        collider = new RectF( gc.menu.collider.left, gc.menu.collider.top + gc.menu.menuHeight/4, gc.menu.collider.right, (gc.menu.collider.top + gc.menu.menuHeight/4) + buttonHeight);



    }

    public void drawText(Canvas mCanvas, Paint mPaint) {
        mPaint.setTextSize(buttonHeight/(float)1.5);
        mPaint.setColor(Color.argb(255,255,255,255));

        mCanvas.drawText(text, collider.left +  collider.width()/(float)3.75 , collider.bottom - (collider.height()/(float)6.5), mPaint);
    }
}
