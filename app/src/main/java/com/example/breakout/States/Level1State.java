package com.example.breakout.States;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.breakout.BOBall;
import com.example.breakout.BOGameController;
import com.example.breakout.Point;

public class Level1State extends State{

    public Level1State(BOGameController gc) {
        super(gc);
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        // draw nothing while game is running?
        // lock the canvas and ready to draw

        // Order matters. This should be drawn before the ball and paddle.
        gc.myLayout.draw(mCanvas, mPaint);
        // Choose a color to paint with
        mPaint.setColor(Color.argb
                (255, 255, 255, 255));

        gc.pauseButton.draw(mCanvas, mPaint);
        drawGameObjects(mCanvas, mPaint);

        mPaint.setTextSize(gc.getMeta().getFontSize());
        checkWon();

        int scoreSize = gc.getMeta().getFontSize() / 2;
        mPaint.setTextSize(scoreSize);

        drawUI(mCanvas, mPaint);


    }

    public void run() {


        update();
        gc.mediaPlayer.playSoundtrack();

        // Now the bat and ball are in
        // their new positions
        // we can see if there have
        // been any collisions
        detectCollisions(gc.ball);
    }

    public void update() {

        long FPS = gc.getMeta().getFPS();

        gc.ball.update(FPS);
        gc.paddle.update(FPS);


        for(int i = 0; i < gc.blocks.size(); i++)
        {
            gc.blocks.get(i).update(gc.ball); // if collided with ball
        }
        gc.won = wonGame();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) { // get an action

            case MotionEvent.ACTION_DOWN: //placed finger on screen

                if(motionEvent.getX() > gc.pauseButton.collider.left && motionEvent.getX() < gc.pauseButton.collider.right && motionEvent.getY() < gc.pauseButton.collider.bottom
                        && motionEvent.getY() > gc.pauseButton.collider.top) {
                    gc.context = new GamePauseState(gc);

                }
                else {

                    gc.paddle.setReachedPosition(false); // tell the paddle
                    // we have a new movement
                    // command
                    gc.paddle.touched = motionEvent.getX();

                    // PowerUp Debugging Method
                    //gc.context = new GameWonState(gc);

                }

                break;
        }
        return true;
    }



    private void checkWon() {
        /*
        checks if all the blocks have been destroyed and plays the sound effects appropriately
         */
        // Check to see if the player won
        if(gc.won) {
            gc.context = new GameWonState(gc);
            gc.context = new GameWonState(gc);
            gc.won = false;
        }
    }

    private void drawGameObjects(Canvas mCanvas, Paint mPaint) {
        // Draw in our Game Objects
        gc.ball.draw(mCanvas, mPaint);
        gc.paddle.draw(mCanvas, mPaint);


        // Draw the blocks
        for(int i = 0; i < gc.blocks.size(); i++) {
            gc.blocks.get(i).draw(mCanvas, mPaint);

        }
    }

    public void drawGameOver(Canvas mCanvas, Paint mPaint){
        gc.gameOver.draw(mCanvas, mPaint);
    }

    private boolean wonGame() {
        for(int i = 0; i < gc.blocks.size(); i++)
        {
            if(!gc.blocks.get(i).getDeadStatus())
                return false;
        }
        return true;
    }


    //TODO: Add a side hit-box to the paddle. Also refractor me
    private void detectCollisions(BOBall ball) {
        // Has our ball hit the paddle?
        if(RectF.intersects(gc.paddle.collider, ball.getCollider())) {
            // realistic bounce
            ball.getCollider().bottom = ball.collider.bottom - (float)1; // shhhhh. We're making it so the ball isn't constantly colliding
            ball.getCollider().top = ball.collider.top - (float)1;

            ball.paddleBounce();
            ball.incrementSpeed(10);


        }

        //handle walls

        Point dim = gc.getMeta().getDim();

        // bottom wall
        if(ball.getCollider().bottom >= dim.y) {

            if(gc.powerUp.type() == "Double Ball")
            {
                gc.ball = (BOBall)gc.powerUp.getMember();
                return;
            }

            if (gc.lives > 0) {
                gc.lives--;
                Log.d("Lives:", "" + gc.lives);

                gc.context = new GameWaitingState(gc);
                // user just lost a life and the game isn't over in this part of the statement
                // pause the game so player can get their bearings

                // Phillip Note: I moved the code here into the draw() method in order to
                // display game over text so that draw didn't just immediately overwrite it

            }
            if(gc.lives == 0){ // Fun scoping issue.
                gc.context = new GameOverState(gc);

            }
        }

        // Top wall
        if(ball.getCollider().top < 0) {
            ball.getCollider().top = 0;
            ball.reverseYVelocity();


        }


        // Left Wall
        if(ball.getCollider().left < 0) {
            ball.getCollider().left = 0;
            ball.reverseXVelocity();

        }

        // Right wall
        if(ball.getCollider().right > dim.x) {
            ball.getCollider().left = dim.x - ball.getCollider().width();
            ball.getCollider().right = dim.x;
            ball.reverseXVelocity();


        }
    }
}

