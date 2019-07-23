package com.example.breakout;

import android.graphics.RectF;
import android.util.Log;

public class BOMenu extends BOObject {

    int screenWidth;
    int screenHeight;
    public float menuWidth;
    public float menuHeight;
    public BOMenuButton resumeButton;
    public BOMenuButton restartButton;
    public BOMenuButton levelsButton;
    public BOMenuButton leaderboardsButton;
    public BOMenuButton exitButton;

    BOMenu(int screenWidth, int screenHeight, BOGameController gc) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.menuWidth = this.screenWidth / (float)1.75;
        this.menuHeight = this.screenHeight / (float)1.75;
        resumeButton = new BOMenuButton(screenWidth, screenHeight, "Resume", gc);
        levelsButton = new BOMenuButton(screenWidth, screenHeight, "Levels", gc);
        restartButton = new BOMenuButton(screenWidth, screenHeight, "Restart", gc);
        leaderboardsButton = new BOMenuButton(screenWidth, screenHeight, "Leaderboards", gc);
        exitButton = new BOMenuButton(screenWidth, screenHeight, "Exit", gc);


        Log.d("MENU: ", ""+this.menuWidth);
        Log.d("MENU: ", ""+this.screenWidth);
        Log.d("MENU: ", ""+this.screenWidth);
        collider = new RectF( (screenWidth/2) - (menuWidth/3) ,(screenHeight/2) - ((float)(menuHeight/1.5)),(screenWidth/2) + (menuWidth/3),(screenHeight/2) + ((float)(menuHeight/1.5)) );
    }
}
