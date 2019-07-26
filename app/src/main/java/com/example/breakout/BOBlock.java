package com.example.breakout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

public class BOBlock extends BOObject{
    /*
    This will handle the blocks and the block logic
    including collisions and deletions
     */

    public boolean isDead; // check to see if the block has been destroyed
    //TODO: change me back to private later

    public boolean hasPowerup;

    private BOGameController gc;
//    public BODoubleBall doubleBall;

    BOBlock(Point dim, float x, float y, BOGameController gameController) {

        super(dim.x / 10, dim.y / 15, new Point(x,y));


        gc = gameController;

        collider = new RectF(getPos().x, getPos().y, getPos().x + getLength(), getPos().y + getHeight());
        isDead = false;
        hasPowerup = false;

    }

    // This is if it collides with a regular block. In the future, add tougher blocks.
    public void update(BOBall ball) {
//        doubleBall = new BODoubleBall(gc);
        if(collided(ball))
        {
            // We're going to lazy delete this block. Since this app isn't super memory
            // intensive if a block is 'hit' it'll just change its collider elements to negative numbers
            // effectively removing it from the screen
            collider = new RectF(-1,-1,-1,-1);
            // check if the block hit has a power-up
            if (hasPowerup) {
//               doubleBall.apply(gc);
               Log.d("PowerUp", "Activated");
            }
            gc.score += 10;
        }

    }

    public boolean collided(BOBall ball) {
        /*
        check to see if the ball has collided with this object
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
    public boolean getDeadStatus() {
        return isDead;
    }

}


