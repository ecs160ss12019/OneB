package com.example.breakout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class BOPaddle extends BOObject {
    /*
    This will handle all logic having to do with the paddle.
     */

    private float speed;
    private int screenX;
    private boolean reachedPosition = true; // used to determine whether the
                                     // paddle has reached its position
                                     // and should wait for a new
                                    // movement commmand

    public float touched; // public because BOGame needs to access it
                        // to set and im too lazy for a setter


    BOPaddle(int x, int y) {
        /*
            Since the bat will be a fixed position based
            on the screen, our bat needs to know where
            the screen is
         */

        // by default we will make the bat have a length of 1/8
        // by default we will have a height of 1/40th
        // by default the paddle should start in the middle of the screen (x/2) and floating a tiny bit above the bottom (bout 1/40 from bottom)
        super(x / 8, y / 40, new Point( x /2 , y - (y / 40)));

        screenX = x;
        touched = getPos().x; //initialize our initial touch to the xpos

        //initialize our collider to the middle of the screen
        collider = new RectF(getPos().x, getPos().y - 30, getPos().x + getLength(), getPos().y + getHeight() - 10); // TODO: fixme. magic numbers here
        speed = screenX;


    }

    // Update
    public void update(long fps) {


        if(reachedPosition)
            return; // don't move
        else if(touched < getPos().x + (collider.width() / 2)) { // go left towards the touched position
            translateX(-(speed / fps));
            if (getPos().x+ (collider.width() / 2) < touched) { // after an update we've exceeded our position
                reachedPosition = true;
            }
        }
        else{ // go right towards position
            translateX((speed/fps));
            if (getPos().x > touched) { // after an update we've exceeded our position
                reachedPosition = true;
            }
        }
        // as with the ball, update the other points to keep our shape
        collider.left = getPos().x;
        collider.right = getPos().x + getLength();

    }


    public void draw(Canvas mCanvas, Paint mPaint) {
        mCanvas.drawBitmap(sprite, null, collider, null);
    }

    // getters and setters
    public void setReachedPosition(boolean state) {
        reachedPosition = state;
    }

}
