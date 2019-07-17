package com.example.breakout;

import android.graphics.RectF;

public class BOLayout extends BOObject {

    BOLayout(int screenX, int screenY){
        // standard constructor that always makes the layout
        // fill up the screen
        super(screenX, screenY, new Point(0,0));

        collider = new RectF();
    }

    // Update
    void update(long fps) {
        /*
         update() will be called repeatedly every frame

         */

    }


}
