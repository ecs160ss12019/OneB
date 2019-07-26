package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.breakout.BOGameController;
import com.example.breakout.GameObjectBuilder;
import com.example.breakout.Point;

public class GameTransitionState extends State{
    /*
    This state is mostly to meme. I thought it'd give the game
    more personality if there was some text before each level and this state is responsible for implimenting that.
     Be careful when calling this state, because as of now I cannot think of a way to set the
     level text. So if you try calling this function and we're not a game transition state, u in trouble.
     */

    String levelDesc;
    public GameTransitionState(BOGameController gc){
        super(gc);
    }

    public void draw(Canvas mCanvas, Paint mPaint)
    {
        Point dim = gc.getMeta().getDim();

        mPaint.setColor(Color.argb(255,0,0,0));
        mCanvas.drawRect(new RectF(0,0,gc.getMeta().getDim('x'), gc.getMeta().getDim('y')), mPaint);
        mPaint.setColor(Color.argb(255,255,255,255));
        mPaint.setTextSize(dim.y/5);
        mCanvas.drawText("Level " + (gc.currentLevel), dim.x/(float)3, dim.y/5 , mPaint);
        mPaint.setTextSize(dim.y/7);
        mCanvas.drawText(gc.levelDesc[gc.currentLevel-1], 100, dim.y/(float)2 , mPaint);
        mCanvas.drawText("(Tap  To  Start)", dim.x/(float)4, dim.y , mPaint);


    }

    public void update() {  // stubby boiz

    }

    public void run() {

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: //placed finger on screen
                gc.mediaPlayer.restartSoundtrack(); // this will make it so the song plays from the begining.
                 // restart the music
                gc.context = gc.levels[gc.currentLevel - 1];
        }
        return true;
    }

    public void setText(String s) {
        levelDesc = s;
    }

}
