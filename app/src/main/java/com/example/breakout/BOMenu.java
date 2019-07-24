package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.example.breakout.States.GameRunningState;

public class BOMenu extends BOObject {

    public float menuWidth;
    public float menuHeight;
    public BOMenuButton resumeButton;
    public BOMenuButton restartButton;
    public BOMenuButton levelsButton;
    public BOMenuButton leaderboardsButton;
    public BOMenuButton exitButton;

    BOMenu(int screenWidth, int screenHeight) {
        this.menuWidth = screenWidth / (float)1.75;
        this.menuHeight = screenHeight / (float)1.75;
        resumeButton = new BOMenuButton(screenWidth, screenHeight, "Resume", menuWidth, menuHeight);
        levelsButton = new BOMenuButton(screenWidth, screenHeight, "Levels", menuWidth, menuHeight);
        restartButton = new BOMenuButton(screenWidth, screenHeight, "Restart", menuWidth, menuHeight);
        leaderboardsButton = new BOMenuButton(screenWidth, screenHeight, "Leaderboards", menuWidth, menuHeight);
        exitButton = new BOMenuButton(screenWidth, screenHeight, "Exit", menuWidth, menuHeight);


        Log.d("MENU: ", ""+this.menuWidth);
        Log.d("MENU: ", ""+screenWidth);
        Log.d("MENU: ", ""+screenWidth);
        collider = new RectF( (screenWidth/2) - (menuWidth/3) ,(screenHeight/2) - ((float)(menuHeight/1.5)),(screenWidth/2) + (menuWidth/3),(screenHeight/2) + ((float)(menuHeight/1.5)) );
    }


}
