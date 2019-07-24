package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class BOMenu extends BOObject {

    int screenWidth;
    int screenHeight;
    public float menuWidth;
    public float menuHeight;


    BOMenu(int screenWidth, int screenHeight, BOGameController gc, Canvas mCanvas, Paint mPaint) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.menuWidth = this.screenWidth / (float) 1.75;
        this.menuHeight = this.screenHeight / (float) 1.75;

        Log.d("MENU: ", "" + this.menuWidth);
        Log.d("MENU: ", "" + this.screenWidth);
        Log.d("MENU: ", "" + this.screenWidth);
        collider = new RectF((screenWidth / 2) - (menuWidth / 3), (screenHeight / 2) - ((float) (menuHeight / 1.5)), (screenWidth / 2) + (menuWidth / 3), (screenHeight / 2) + ((float) (menuHeight / 1.5)));

    }
}
