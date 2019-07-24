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

    BOMenuButton(int screenWidth, int screenHeight, String text, float menuWidth, float menuHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.buttonWidth = menuWidth;
        this.buttonHeight = menuHeight/6;
        this.text = text;

        collider = new RectF( (screenWidth/2) - (menuWidth/3), (menuHeight/6), (screenWidth/2) + (menuWidth/3), (menuHeight/6) + buttonHeight);
    }

    public void drawText() {

    }
}
