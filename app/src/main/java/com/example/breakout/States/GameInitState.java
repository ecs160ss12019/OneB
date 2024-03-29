package com.example.breakout.States;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import com.example.breakout.BOGameController;
import com.example.breakout.BOLayout;
import com.example.breakout.BOTimer;
import com.example.breakout.GameObjectBuilder;
import com.example.breakout.R;

public class GameInitState extends State {
    /*
    This state is only entered once when the user boots up the game.
    Right now it's only functionality is to display our team logo for a few seconds
    before transitioning us into the Title Menu State.
     */



    /*
    WARNING: Abandon all hope yee who enter here.
    This code was written form the machinations of a man who does not know
    how an animation framework works in android studio and instead of google
    decided the he should just code his own.
    The follow is a mess of timers, and background objects, loops,
    and conditions that do not make any sense.
    Do not attempt to understand or edit any of the code that follows, I garentee
    you it is magic and defies all the rules of good software engineering.
    Only enter forward if you wish to rewrite this trash code and know how to
    do animations in android studio.
    This is your final warning.

    o Phillip Tran - Degenerate Coder.




     */


    public BOLayout myLayout; // Holds out title screen
    private RectF background; // BEHOLD, the most ghetto animation you will ever see.
    private int alphaVal;
    private BOTimer t2;
    private BOTimer t3;
    private boolean fadeIn = false;
    private boolean fadeOut = false;

    public GameInitState(BOGameController gc) {
        super(gc);

        //gc.timer.run(5000L);
        t2 = new BOTimer();

        t2.run(10L);
        t3 = new BOTimer();
        GameObjectBuilder factory = new GameObjectBuilder();
        factory.setPos(gc.getMeta().getDim());
        factory.setSprite(BitmapFactory.decodeResource(gc.resources.getResources(), R.drawable.logo));
        factory.setCollider(new RectF(0, 0, gc.getMeta().getDim('x'), gc.getMeta().getDim('y')));
        myLayout = factory.buildLayout();
        background = new RectF(0, 0, gc.getMeta().getDim('x'), gc.getMeta().getDim('y'));
        alphaVal = 255;
    }

    public void draw(Canvas mCanvas, Paint mPaint)
    {
        mPaint.setColor(Color.argb(alphaVal,0,0,0));
        myLayout.draw(mCanvas, mPaint);
        mCanvas.drawRect(background, mPaint);
    }

    public void update() {  // stubby boiz

    }

    public void run() {
        if(t3.completed && fadeOut)
            gc.context = new GameInputState(gc);

        if(fadeIn && gc.timer.completed && !fadeOut) {
            if(t2.completed) {
                if(alphaVal < 255)
                    alphaVal += 15;
                else {
                    t3.run(1000L);
                    fadeOut = true;
                }
                t2.run(5);
            }
        }
        else {
            if (t2.completed && !fadeOut) {
                if (alphaVal > 0)
                    alphaVal -= 15;
                else {
                    fadeIn = true;
                    gc.timer.run(3000L);
                }
                t2.run(10);
            }
        }

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {

        return false;
    }

}
