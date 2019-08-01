package com.example.breakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;


// ADAPTED FROM TEXTBOOK CODE: Chapter 9


public class BOBall extends BOObject{
    /*
    This class will handle the ball logic.
    Functions that go here directly involve the properties of the
    ball such as collisions and such. Tbh, I don't think this class
    will need to be edited much, but maybe you can think of something I can't
     */

    private float xVelocity;
    private float speed;
    private float yVelocity;
    private float angle;

    public boolean fakeBounce = false; // used to see fake bounces



    public BOBall(int screenX, Bitmap sprite) {
        // standard constructor that always makes that ball
        // 1% of screen width
        super(screenX / 50, screenX / 50, new Point(0,0));

        collider = new RectF();
        this.sprite = sprite;

    }

    // Update
    public void update(long fps) {
        /*
         update() will be called repeatedly every frame
         usually update functions handle movement, and I don't suppose
         this'll be any different
         */

        // move that ball based on current fps

        collider.left += (xVelocity / fps);
        collider.top += (yVelocity / fps);

        // keep the collider the same size by matching up
        // the other two corners
        collider.right = collider.left + getLength();
        collider.bottom = collider.top + getHeight();

    }


    public void paddleBounce() // the paddle
    {
        reverseYVelocity();
    }

    public void blockBounce(RectF blockCollider) {
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
    public void reverseYVelocity() {
        yVelocity = -yVelocity;
    }

    public void reverseXVelocity() {
        xVelocity = -xVelocity;
    }

    public void reset(BOPaddle paddle){


        // Initialise the four points of
        // the rectangle which defines the ball
        // Initialized so that it will always be ~ the center of our paddle.

        collider.left = paddle.collider.left + ( paddle.collider.width() / 2) - getLength()/2;
        collider.top = paddle.collider.top - paddle.getHeight() - getHeight();
        collider.right = paddle.collider.left + ( paddle.collider.width() / 2) + getLength() - getLength()/2;
        collider.bottom = paddle.collider.top + getHeight() - paddle.getHeight() - getHeight();


        // How fast will the ball travel
        // You could vary this to suit
        // You could even increase it as the game progresses
        // to make it harder

        setSpeed(500); // if you put this at 310 or lower the ball can get stuck at the top also this is a magic number
    }

    // Getters and Setters
    public RectF getCollider() {
        return collider;
    }

    // Function to set the speed of the ball. Normally used once per game, at the beginning
    private void setSpeed(float s) {
        this.speed = s;

        // Magnitude of the velocity
        Random random = new Random();
        // Choose a random yVelocity component between appropriate bounds
        yVelocity = (float) -1 * (random.nextInt((int) Math.round(0.7 * speed)) + ((float) 0.3 * speed));
        // Compute the xVelocity based on desired magnitude
        xVelocity = (float) (Math.sqrt(speed * speed - yVelocity * yVelocity));
        // Randomly make xVelocity negative or positive
        if (random.nextInt(100) > 50) {
            xVelocity *= -1;
        }
        // Compute the angle between the xComponent and yComponent
        angle = (float)Math.atan(Math.abs(yVelocity)/Math.abs(xVelocity));

    }

    // Getter to return current speed
    public float getSpeed() {
        return speed;
    }

    // Function to increment speed but maintain pre-existing direction
    public void incrementSpeed(float num) {
        // Ups the speed by an indicated number
        this.speed += num;

        // Properly recomputes the components based on the initial angle
        this.yVelocity = -speed * (float)Math.sin(angle);
        if (xVelocity < 0) {
            this.xVelocity = -speed * (float)Math.cos(angle);
        } else {
            this.xVelocity = speed * (float)Math.cos(angle);
        }


    }

    public void setxVelocity(float newVelocity) {
        xVelocity = newVelocity;
    }

}
