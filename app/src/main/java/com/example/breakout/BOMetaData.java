package com.example.breakout;

import com.example.breakout.Point;

public class BOMetaData {
    /*
    This class is a container class that holds the Meta Data of our game
    (screen resolution, font sizes and fonts
     */

    // Frame-rate calculations
    private long FPS;
    private final int MILLIS_IN_SECONDS = 1000;

    // Hold resolution of the screen
    private int mScreenX;
    private int mScreenY;

    // Holds text size
    private int fontSize;
    private int fontMargin;

    BOMetaData(int screenX, int screenY, int fontSize, int fontMargin) {
        mScreenX = screenX;
        mScreenY = screenY;
        this.fontSize = fontSize;
        this.fontMargin = fontMargin;
    }



    // getters and setters

    public int getFontSize() {
        return fontSize;
    }

    public long getFPS()
    {
        return FPS;
    }

    /*
    overloaded getDim functions. If no parameter is passed in, it will return a Point object containing both dimensions, else
    a character (x,y) is expected and it'll return the appropreate dimension size. Used for more flexibility.
     */
    public Point getDim()
    {
        return new Point(mScreenX, mScreenY);
    }

    public int getDim(char choice) {
        if( choice == 'x')
            return mScreenX;
        else if(choice == 'y')
            return mScreenY;
        else
            return -1;
    }

    public void updateFPS(long timeThisFrame) {
        FPS = MILLIS_IN_SECONDS / timeThisFrame;
    }


}
