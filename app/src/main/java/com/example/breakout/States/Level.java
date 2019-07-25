package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.breakout.BOBall;
import com.example.breakout.BOGameController;

public abstract class Level extends State {

    public Level(BOGameController gc) {
        super(gc);
    }

    @Override
    public void draw(Canvas mCanvas, Paint mPaint) {

    }

    @Override
    public void run() {
        update();
        gc.media.start();
        gc.media.setLooping(true);

        // Now the bat and ball are in
        // their new positions
        // we can see if there have
        // been any collisions
        detectCollisions(gc.ball);
        if(gc.ball2 != null) {
            detectCollisions(gc.ball2);
        }
    }

    @Override
    public void update() {

        long FPS = gc.getMeta().getFPS();

        gc.ball.update(FPS);
        gc.paddle.update(FPS);

        if(gc.doubleBallPowerUp)
            gc.ball2.update(FPS);


        for(int i = 0; i < gc.blocks.size(); i++)
        {
            gc.blocks.get(i).update(gc.ball); // if collided with ball
            if(gc.doubleBallPowerUp) // double ball power up
                gc.blocks.get(i).update(gc.ball2);
        }
        gc.won = wonGame();

    }

    @Override
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

    public void checkWon() {
        /*
        checks if all the blocks have been destroyed and plays the sound effects appropreately
         */
        // Check to see if the player won
        if(gc.won) {
            gc.context = new GameWonState(gc);
            gc.context = new GameWonState(gc);
            gc.won = false;
        }
    }

    private boolean wonGame() {
        for(int i = 0; i < gc.blocks.size(); i++)
        {
            if(gc.blocks.get(i).getDeadStatus() == false)
                return false;
        }
        return true;
    }

    public abstract void detectCollisions(BOBall ball);
    public abstract void drawGameObjects(Canvas mCanvas, Paint mPaint);
}
