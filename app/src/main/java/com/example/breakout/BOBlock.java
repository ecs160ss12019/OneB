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

    Random random = new Random();
    int speed = random.nextInt(15);

    private boolean isDead; // check to see if the block has been destroyed

    public boolean hasPowerUp;
    public boolean reverse = false;
    private BOGameController gc;

    public boolean isFalling = false;

//    public BODoubleBall doubleBall;

    BOBlock(Point dim, float x, float y, BOGameController gameController) {

        super(dim.x / 10, dim.y / 15, new Point(x,y));


        gc = gameController;

        collider = new RectF(getPos().x, getPos().y, getPos().x + getLength(), getPos().y + getHeight());
        isDead = false;
        hasPowerUp = false;

    }

    // This is if it collides with a regular block. In the future, add tougher blocks.
    public void update(BOBall ball) {
//        doubleBall = new BODoubleBall(gc);
        if(collided(ball))
        {
            // We're going to lazy delete this block. Since this app isn't super memory
            // intensive if a block is 'hit' it'll just change its collider elements to negative numbers
            // effectively removing it from the screen

            if(ball.fakeBounce)
            {
                ball.fakeBounce = false;
                return;
            }

            collider = new RectF(-1, -1, -1, -1);
            isDead = true;
            // check if the block hit has a power-up
            if (hasPowerUp) {
                gc.powerUp = gc.powerUp.randomPowerUp(gc);
                Log.d("" + gc.powerUp + " PowerUp", "Activated");
            }
            gc.score += 10;
        }

    }

    public void level9Update(BOBall ball) {

        if(collider.right >= gc.getMeta().getDim('x'))
        {
            reverse = true;
        }
        else if(collider.left <= 0){
            reverse = false;
        }


        if(!reverse) {
            collider.left += speed;
            collider.right += speed;
        }
        else
        {
            collider.left -= speed;
            collider.right -= speed ;
        }
        if(collided(ball))
        {
            // We're going to lazy delete this block. Since this app isn't super memory
            // intensive if a block is 'hit' it'll just change its collider elements to negative numbers
            // effectively removing it from the screen

            if(ball.fakeBounce)
            {
                ball.fakeBounce = false;
                return;
            }

            collider = new RectF(-1,-1,-1,-1);
            isDead = true;
            // check if the block hit has a power-up
            if (hasPowerUp) {
                gc.powerUp = gc.powerUp.randomPowerUp(gc);
                Log.d("" + gc.powerUp + " PowerUp", "Activated");
            }
            gc.score += 10;
        }

    }

    public void level10Update(BOBall ball)
    {
        if(isFalling)
        {
            collider.top += 20;
            collider.bottom += 20;
        }
        if(collided(ball))
        {
            // We're going to lazy delete this block. Since this app isn't super memory
            // intensive if a block is 'hit' it'll just change its collider elements to negative numbers
            // effectively removing it from the screen

            isFalling = true;
            if(ball.fakeBounce)
            {
                ball.fakeBounce = false;
                return;
            }




            if(collider.bottom >= gc.getMeta().getDim('y')) {
                collider = new RectF(-1, -1, -1, -1);
                isDead = true;
                // check if the block hit has a power-up
                if (hasPowerUp) {
                    //gc.powerUp = gc.powerUp.randomPowerUp(gc);
                    gc.powerUp = new BODoubleBall(gc);
                    Log.d("" + gc.powerUp + " PowerUp", "Activated");
                }
                gc.score += 10;
            }
        }
    }

    private boolean collided(BOBall ball) {
        /*
        check to see if the ball has collided with this object
        */
        if(RectF.intersects(collider, ball.getCollider())){
            // realistic bounce
            ball.blockBounce(collider);
            return true;
        }
        return false;
    }

    // Getters and Setters
    public boolean getDeadStatus() {
        return isDead;
    }

    public void setDeadStatus() { isDead = true; }

}


