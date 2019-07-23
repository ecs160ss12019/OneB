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

    BOMenuButton(int screenWidth, int screenHeight, String text, BOGameController gc) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.buttonWidth = this.screenWidth / (float)1.75;
        this.buttonHeight = this.screenHeight / (float)1.75;
        this.text = text;

        collider = new RectF( (screenWidth) - (gc.menu.menuWidth/3), (gc.menu.menuHeight/6), (screenWidth) - (gc.menu.menuWidth/3) + buttonWidth, (gc.menu.menuHeight/16) + buttonHeight);
    }
}
