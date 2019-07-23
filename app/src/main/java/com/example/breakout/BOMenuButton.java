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

    public BOMenuButton(int screenWidth, int screenHeight, String text, BOGameController gc) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.buttonWidth = this.screenWidth / (float)1.75;
        this.buttonHeight = this.screenHeight / (float)7.5;
        this.text = text;

        collider = new RectF( gc.menu.collider.left, gc.menu.collider.top + gc.menu.menuHeight/4, gc.menu.collider.right, (gc.menu.collider.top + gc.menu.menuHeight/4) + buttonHeight);


    }
}
