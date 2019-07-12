package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class BOBlock {
    /*
    This will handle the blocks and the block logic
    including collisions and deletions
     */

    private RectF collider;
    private float length;
    private float height;
    private boolean isDead; // check to see if the block has been destroyed

    private BOGameController gameController;

    // we want the positions to be relative to the screen
    private int screenX;
    private int screenY;

    // positions
    private float xPos;
    private float yPos;


    BOBlock(int xScreen, int yScreen, float x, float y, BOGameController gameController) {
        screenX = xScreen;
        screenY = yScreen;
        length = xScreen / 10;
        height = yScreen / 20;

        xPos = x;
        yPos = y;

        this.gameController = gameController;

        collider = new RectF(xPos, yPos, xPos + length, yPos + height);
        isDead = false;

    }

    void draw(Canvas mCanvas, Paint mPaint) {
        mCanvas.drawRect(collider, mPaint);
    }

    // This is if it collides with a regular block. In the future, add tougher blocks.
    void update(BOBall ball) {
        if(collided(ball))
        {
            // We're going to lazy delete this block. Since this app isn't super memory
            // intensive if a block is 'hit' it'll just change its collider elements to negative numbers
            // effectively removing it from the screen
            collider = new RectF(-1,-1,-1,-1);
            gameController.score += 10;

        }
    }

    public boolean collided(BOBall ball) {
        /*
        check to see if the ball has colldied with this object
        */
        if(RectF.intersects(collider, ball.getCollider())){
            // realistic bounce
            ball.blockBounce(collider);
            isDead = true;
            return true;
        }
        return false;
    }

    // Getters and Setters
    float getLength() {
        return length;
    }

    float getHeight() {
        return height;
    }

    RectF getCollider() {
        return collider;
    }

    boolean getDeadStatus() {
        return isDead;
    }

}


