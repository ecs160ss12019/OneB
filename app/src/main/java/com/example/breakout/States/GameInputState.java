package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.breakout.BOGameController;
import com.example.breakout.BOKeyboard;
import com.example.breakout.BOUser;
import com.example.breakout.Point;
import com.example.breakout.R;


public class GameInputState extends State {
    private BOKeyboard keyboard;
    private String name = "";

    public GameInputState(BOGameController gc) {
        super(gc);
        LinearLayout layout = new LinearLayout(gc);
        EditText editText = new EditText(gc);

        layout.addView(editText);
        keyboard = new BOKeyboard(gc);
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        gc.myLayout.draw(mCanvas, mPaint);

        Point p = gc.getMeta().getDim();

        mPaint.setTextSize(p.x / 20);

        mPaint.setColor(Color.argb(255,255,255,255));
        mCanvas.drawText("Please enter your nickname!",(p.x/5) ,p.y / 3, mPaint);
        mCanvas.drawText(name,(p.x/5) ,p.y / 2, mPaint);

        keyboard.draw(mCanvas, mPaint);


    }

    public void run() {

    }

    public void update() {

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) { // get an action

            case MotionEvent.ACTION_DOWN: //placed finger on screen

                RectF touched  = new RectF(motionEvent.getX(), motionEvent.getY(), motionEvent.getX(), motionEvent.getY());
                char ret = keyboard.returnTouched(touched);

                if(ret == '!')
                {
                    gc.context = new GameTransitionState(gc);
                }
                if(ret != '-')
                {
                    name += ret;
                }


                break;
        }
        return true;
    }

    public int getScoreFromDatabase(String name) {
        // if user exists in DB, get score corresponding to their name
        // else, return 0

        return 0;
    }



}
