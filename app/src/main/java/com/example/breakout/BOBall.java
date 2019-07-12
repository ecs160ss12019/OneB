package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;


// ADAPTED FROM TEXTBOOK CODE: Chapter 9

// TODO: Add randomness to the ball's bounces. Right now the ball bounces in a completely
// predictable manner, maybe change its angle of bounce very slightly every time it hits something?

public class BOBall {
    /*
    This class will handle the ball logic.
    Functions that go here directly involve the properties of the
    ball such as collisions and such. Tbh, I don't think this class
    will need to be edited much, but maybe you can think of something I can't
     */

    private RectF collider;
    /*
    Note: Really what a RectF is in game development is what we call a
    'collider'. A collider essentially is a representation of our
    object using a geometric shape (in unity you can have circle colliders too!)
    They are used to interact with the level, you can think of the
    collider as the actual 'body' of our game object. The coordinates
    (top, left, right, and bottom) represent where our game object's 'body'
    is locationed in the game space.
     */

    private float xVelocity;
    private float speed;
    private float yVelocity;
    private float ballWidth;
    private float ballHeight;

    private BOPaddle paddle; // pass a reference to the paddle so we can reset the ball to its position

    BOBall(int screenX, BOPaddle p) {
        // standard constructor that always makes that ball
        // 1% of screen width
        ballWidth = screenX / 100;
        ballHeight = ballWidth; // it's a perfect square ball

        collider = new RectF();
        paddle = p;


    }


    void draw(Canvas mCanvas, Paint mPaint) {
        // Draw the ball
        mCanvas.drawRect(collider, mPaint);
    }


    // Update
    void update(long fps) {
        /*
         update() will be called repeatedly every frame
         usually update functions handle movement, and I don't suppose
         this'll be any different
         */

        //move that ball based on current fps

        collider.left += (xVelocity / fps);
        collider.top += (yVelocity / fps);

        // keep the collider the same size by matching up
        // the other two corners
        collider.right = collider.left + ballWidth;
        collider.bottom = collider.top + ballHeight;

    }

    // handling the bounce logic
    void bounce(RectF paddleCollider) {
        float paddleCenter = paddleCollider.left + (paddleCollider.width() / 2);
        float ballCenter = collider.left + (ballWidth / 2);

//        float relativeIntersect = (paddleCenter - ballCenter); // where did the ball hit on the paddle?

         // go back up the screen
        reverseYVelocity();
    }


    void blockBounce(RectF blockCollider) {
        /* handles collisions for blocks

         */
        float left_right_difference = Math.abs(blockCollider.left - collider.right);
        float right_left_difference = Math.abs(blockCollider.right - collider.left);
        float top_bottom_difference = Math.abs(blockCollider.top - collider.bottom);
        float bottom_top_difference = Math.abs(blockCollider.bottom - collider.top);


        // This is what I feel to be 'realistic' bounces
        if((left_right_difference < top_bottom_difference) && (left_right_difference < bottom_top_difference)) { // hit the left side
            reverseXVelocity();
        }

        else if((right_left_difference < top_bottom_difference) && (right_left_difference < bottom_top_difference)) { // hit the right side
            reverseXVelocity();

        }
        else // didn't hit any side
        {
            reverseYVelocity();
        }



    }


    // reverse direction of travel due to collision
    void reverseYVelocity() {
        yVelocity = -yVelocity;
    }

    void reverseXVelocity() {
        xVelocity = -xVelocity;
    }

    void reset(int x, int y){
        // Initialise the four points of
        // the rectangle which defines the ball
        // Initialized so that it will always be ~ the center of our paddle.
        collider.left = paddle.getCollider().left + ( paddle.getCollider().width() / 2);
        collider.top = paddle.getCollider().top - paddle.getHeight();
        collider.right = paddle.getCollider().left + ( paddle.getCollider().width() / 2) + ballWidth;
        collider.bottom = paddle.getCollider().top + ballHeight - paddle.getHeight();

        // How fast will the ball travel
        // You could vary this to suit
        // You could even increase it as the game progresses
        // to make it harder

        // Magnitude of the velocity
        speed = 1000;
        Random random = new Random();
        // Choose a random yVelocity component between appropriate bounds
        yVelocity = (float) -1 * (random.nextInt((int)Math.round(0.7*speed)) + ((float)0.3*speed));
        // Compute teh xVelocity based on desired magnitude
        xVelocity = (float) (Math.sqrt(speed*speed - yVelocity*yVelocity));
        // Randomly make xVelocity negative or positive
        if (random.nextInt(100) > 50) {
            xVelocity *= -1;
        }
    }

    // Getters and Setters
    RectF getCollider() {
        return collider;
    }


}
