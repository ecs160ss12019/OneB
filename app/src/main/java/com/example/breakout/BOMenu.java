package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class BOMenu extends BOObject {

    float menuHeight;

    BOMenu(int screenWidth, int screenHeight) {
        float menuWidth = screenWidth / (float) 1.75;
        this.menuHeight = screenHeight / (float) 1.75;

        Log.d("MENU: ", "" + menuWidth);
        Log.d("MENU: ", "" + screenWidth);
        Log.d("MENU: ", "" + screenWidth);
        collider = new RectF((screenWidth / 2) - (menuWidth / 3), (screenHeight / 2) - ((float) (menuHeight / 1.5)), (screenWidth / 2) + (menuWidth / 3), (screenHeight / 2) + ((float) (menuHeight / 1.5)));

    }
}
