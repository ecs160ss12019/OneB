package com.example.breakout;

import android.graphics.RectF;
import android.util.Log;

public class BOPauseButton extends BOObject {
    float pauseWidth;
    float pauseHeight;
    int screenWidth;
    int screenHeight;

    BOPauseButton(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.pauseWidth = this.screenWidth / (float)16;
        this.pauseHeight = this.screenWidth / (float)16; // to make it square

        collider = new RectF( (screenWidth) - (screenWidth/10), screenHeight/16, (screenWidth) - (screenWidth/10) + pauseWidth, screenHeight/16 + pauseHeight);
    }


}
