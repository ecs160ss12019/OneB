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

public class Level1State extends Level{

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

        if(gc.doubleBallPowerUp)
            gc.ball2.draw(mCanvas, mPaint);

        mPaint.setTextSize(gc.getMeta().getFontSize());
        checkWon();

        int scoreSize = gc.getMeta().getFontSize() / 2;
        mPaint.setTextSize(scoreSize);


        Point dim = gc.getMeta().getDim();

        mCanvas.drawText("Level: " + gc.level,dim.x / 55,dim.y / 6, mPaint);
        mCanvas.drawText("Score: " + gc.score,dim.x / 55,dim.y / 9, mPaint); // TODO: move this to UI class?
        mCanvas.drawText("Lives: " + gc.lives,dim.x / 55,dim.y / 20, mPaint);
    }

    public void run() {
            super.run();
//        update();
//        gc.media.start();
//        gc.media.setLooping(true);
//
//        // Now the bat and ball are in
//        // their new positions
//        // we can see if there have
//        // been any collisions
//        detectCollisions(gc.ball);
//        if(gc.ball2 != null) {
//            detectCollisions(gc.ball2);
//        }
    }

    public void update() {

        super.update();

//        long FPS = gc.getMeta().getFPS();
//
//        gc.ball.update(FPS);
//        gc.paddle.update(FPS);
//
//        if(gc.doubleBallPowerUp)
//            gc.ball2.update(FPS);
//
//
//        for(int i = 0; i < gc.blocks.size(); i++)
//        {
//            gc.blocks.get(i).update(gc.ball); // if collided with ball
//            if(gc.doubleBallPowerUp) // double ball power up
//                gc.blocks.get(i).update(gc.ball2);
//        }
//        gc.won = wonGame();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);

//        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) { // get an action
//
//            case MotionEvent.ACTION_DOWN: //placed finger on screen
//
//                if(motionEvent.getX() > gc.pauseButton.collider.left && motionEvent.getX() < gc.pauseButton.collider.right && motionEvent.getY() < gc.pauseButton.collider.bottom
//                        && motionEvent.getY() > gc.pauseButton.collider.top) {
//                    gc.context = new GamePauseState(gc);
//
//                }
//                else {
//
//                    gc.paddle.setReachedPosition(false); // tell the paddle
//                    // we have a new movement
//                    // command
//                    gc.paddle.touched = motionEvent.getX();
//
//                    // PowerUp Debugging Method
//                    //gc.context = new GameWonState(gc);
//
//                }
//
//                break;
//        }
//        return true;
    }



    public void checkWon() {
        /*
        checks if all the blocks have been destroyed and plays the sound effects appropreately
         */
        // Check to see if the player won
//        if(gc.won) {
//            gc.context = new GameWonState(gc);
//            gc.context = new GameWonState(gc);
//            gc.won = false;
//        }
        super.checkWon();
    }

    public void drawGameObjects(Canvas mCanvas, Paint mPaint) {
        // Draw in our Game Objects
        gc.ball.draw(mCanvas, mPaint);
        gc.paddle.draw(mCanvas, mPaint);


        // Draw the blocks
        for(int i = 0; i < gc.blocks.size(); i++) {
            gc.blocks.get(i).draw(mCanvas, mPaint);

        }
    }

    //TODO: make sure we need this method
    public void drawGameOver(Canvas mCanvas, Paint mPaint){
        gc.gameOver.draw(mCanvas, mPaint);
    }

//    private boolean wonGame() {
////        for(int i = 0; i < gc.blocks.size(); i++)
////        {
////            if(gc.blocks.get(i).getDeadStatus() == false)
////                return false;
////        }
////        return true;
//        return super.wonGame();
//    }


    //TODO: Add a side hitbox to the paddle. Also refractor me
    public void detectCollisions(BOBall ball) {
        // Has our ball hit the paddle?
        if(RectF.intersects(gc.paddle.collider, ball.getCollider())) {
            // realistic bounce
            ball.getCollider().bottom = gc.paddle.collider.top + (float).01; // shhhhh. We're making it so the ball isn't constantly colliding
            ball.blockBounce(gc.paddle.collider);
            ball.incrementSpeed(10);
        }

        //handle walls

        Point dim = gc.getMeta().getDim();

        // bottom wall
        if(ball.getCollider().bottom >= dim.y) {

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

