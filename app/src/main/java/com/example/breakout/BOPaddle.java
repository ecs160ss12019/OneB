package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class BOPaddle {
    /*
    This will handle all logic having to do with the paddle.
     */

    private RectF collider;
    private float length;
    private float height;
    private float xPos;
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
        screenX = x;

        // by default we will make the bat have a length of 1/8
        length = screenX / 8;

        // by default we will have a height of 1/40th
        height = y / 40;

        //TODO: check to see if any better configureation?
        xPos = screenX / 2;  // exact middle of our screen
        touched = xPos; //initialize our initial touch to the xpos
        float yPos = y - height;

        //initialize our collider to the middle of the screen
        collider = new RectF(xPos, yPos, xPos + length, yPos + height);
        speed = screenX;


    }

    // Update
    void update(long fps) {


        if(reachedPosition) {
            return; // don't move
        }
        else if(touched < xPos + (collider.width() / 2)) { // go left towards the touched position
            xPos = xPos - (speed / fps);
            if (xPos + (collider.width() / 2) < touched) { // after an update we've exceeded our position
                reachedPosition = true;
            }
        }
        else{ // go right towards position
            xPos = xPos + (speed / fps);
            if (xPos > touched) { // after an update we've exceeded our position
                reachedPosition = true;
            }
        }


        // as with the ball, update the other points to keep our shape
        collider.left = xPos;
        collider.right = xPos + length;


    }



    void draw(Canvas mCanvas, Paint mPaint) {
        mCanvas.drawRect(collider, mPaint);
    }

    // getters and setters
    void setReachedPosition(boolean state) {
        reachedPosition = state;
    }

    RectF getCollider() {
        return collider;
    }

    float getHeight(){ return height; }

}
