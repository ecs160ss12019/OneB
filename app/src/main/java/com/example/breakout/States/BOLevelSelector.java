package com.example.breakout.States;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.breakout.BOBall;
import com.example.breakout.BOGameController;
import com.example.breakout.BOMenuButton;
import com.example.breakout.BONoPowerUp;
import com.example.breakout.BOObject;
import com.example.breakout.R;

public class BOLevelSelector extends State {

    float menuHeight;
    RectF collider;
    Bitmap sprite;
    BOMenuButton minusButton;
    BOMenuButton plusButton;
    BOMenuButton okButton;
    int Levels = gc.currentLevel;

    public BOLevelSelector(BOGameController gc) {
        super(gc);

        float screenWidth = gc.getMeta().getDim('x');
        float screenHeight = gc.getMeta().getDim('y');

        float menuWidth = screenWidth / (float) 1.75;
        this.menuHeight = screenHeight / (float) 1.75;


        collider = new RectF((screenWidth / 2) - (menuWidth / 3), (screenHeight / 2) - ((float) (menuHeight / 1.5)),
                (screenWidth / 2) + (menuWidth / 3), (screenHeight / 2) + ((float) (menuHeight / 1.5)));
        sprite = BitmapFactory.decodeResource(gc.resources.getResources(), R.drawable.leaderboard);

        minusButton = new BOMenuButton((int)screenWidth, (int)screenHeight, "-", gc, collider.height() / 2);

        plusButton = new BOMenuButton((int)screenWidth, (int)screenHeight, "+", gc, collider.height() / 2);
        okButton = new BOMenuButton((int)screenWidth, (int)screenHeight, "Ok!", gc, collider.height() / (float)1.5);

    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        // draw the pause menu if pause state and running
        gc.myLayout.draw(mCanvas, mPaint); // draw the background over the blocks.
        mCanvas.drawBitmap(sprite, null, collider, mPaint);
        mPaint.setColor(Color.argb(255,222,113,144));
        mCanvas.drawText("Levels",(int)collider.left + collider.width() / (float)4, collider.top + (collider.height() / (float)8) , mPaint);

        mPaint.setColor(Color.argb(255,255,255,255));
        plusButton.collider.right = collider.right - collider.width()/8;
        plusButton.collider.left = collider.right - collider.width()/4;

        minusButton.collider.left = collider.left + collider.width()/7;
        minusButton.collider.right = collider.left + collider.width()/5;
        okButton.collider.left = collider.left + collider.width()/(float)2.5;
        okButton.collider.right = okButton.collider.left + collider.width() / (float)4;

        minusButton.draw(mCanvas, mPaint);
        plusButton.draw(mCanvas, mPaint);
        okButton.draw(mCanvas, mPaint);

        mCanvas.drawText(""+Levels, collider.left+collider.width()/(float)2.5, collider.top+collider.height()/2,mPaint);


    }

    public void run() {
        // What should pause do while the game is running?
    }

    public void update() {
        // does nothing for now, but can be used as a 'listener' later
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: //placed finger on screen
                if (motionEvent.getX() > minusButton.collider.left && motionEvent.getX() < minusButton.collider.right && motionEvent.getY() < minusButton.collider.bottom
                        && motionEvent.getY() > minusButton.collider.top) {
                    if (Levels > 1) {
                        Levels--;
                    }
                }
                if (motionEvent.getX() > plusButton.collider.left && motionEvent.getX() < plusButton.collider.right && motionEvent.getY() < plusButton.collider.bottom
                        && motionEvent.getY() > plusButton.collider.top) {
                    if (Levels < 10) {
                        Levels++;
                    }
                }
                if (motionEvent.getX() > okButton.collider.left && motionEvent.getX() < okButton.collider.right && motionEvent.getY() < okButton.collider.bottom
                        && motionEvent.getY() > okButton.collider.top) {
                    gc.currentLevel = Levels;
                    gc.mBOGame.startNewGame();
                    gc.powerUp = new BONoPowerUp(gc);
                    gc.ball.sprite = BitmapFactory.decodeResource(gc.resources.getResources(), R.drawable.ball);
                    //reset the ball
                    gc.ball = new BOBall(gc.getMeta().getDim('x'), BitmapFactory.decodeResource(gc.resources.getResources(), R.drawable.ball) );
                    gc.ball.reset(gc.paddle);
                    gc.context = new GameTransitionState(gc);
                }

        }
        return false;
    }
}