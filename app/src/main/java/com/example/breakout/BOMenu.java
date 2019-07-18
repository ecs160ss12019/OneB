package com.example.breakout;

import android.graphics.RectF;
import android.util.Log;

public class BOMenu extends BOObject {

    int screenWidth;
    int screenHeight;
    float menuWidth;
    float menuHeight;

    BOMenu(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.menuWidth = this.screenWidth / (float)1.75;
        this.menuHeight = this.screenHeight / (float)1.75;

        Log.d("MENU: ", ""+this.menuWidth);
        Log.d("MENU: ", ""+this.screenWidth);
        Log.d("MENU: ", ""+this.screenWidth);
        collider = new RectF( (screenWidth/2) - (menuWidth/2) ,(screenHeight/2) - (menuHeight/2),(screenWidth/2) + (menuWidth/2),(screenHeight/2) + (menuHeight/2) );
    }
}
