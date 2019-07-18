package com.example.breakout;

import android.graphics.RectF;
import android.util.Log;

public class BOMenuButton extends BOObject {
    float pauseWidth;
    float pauseHeight;
    int screenWidth;
    int screenHeight;

    BOMenuButton(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.pauseWidth = this.screenWidth / (float)8;
        this.pauseHeight = this.screenHeight / (float)8;

        Log.d("MENU: ", ""+this.pauseWidth);
        Log.d("MENU: ", ""+this.screenWidth);
        Log.d("MENU: ", ""+this.screenWidth);
        collider = new RectF( (screenWidth) - (screenWidth/8), screenHeight/16, (screenWidth) - (screenWidth/8) + pauseWidth, screenHeight/16 + pauseHeight);
    }


}
