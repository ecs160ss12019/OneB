package com.example.breakout;

import android.graphics.RectF;

public class BOMenuButton extends BOObject {
    float buttonWidth;
    float buttonHeight;
    int screenWidth;
    int screenHeight;
    float xPosition;
    float yPosition;
    String text;

    BOMenuButton(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.buttonWidth = this.screenWidth / (float)16;
        this.buttonHeight = this.screenWidth / (float)16; // to make it square

        collider = new RectF( (screenWidth) - (screenWidth/10), screenHeight/16, (screenWidth) - (screenWidth/10) + buttonWidth, screenHeight/16 + buttonHeight);
    }
}
