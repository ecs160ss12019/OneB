package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.breakout.BOGameController;
import com.example.breakout.BOUser;
import com.example.breakout.Point;
import com.example.breakout.R;


public class GameInputState extends State {
    LinearLayout layout;
    EditText editText;

    public GameInputState(BOGameController gc) {
        super(gc);
        layout = new LinearLayout(gc);
        editText = new EditText(gc);

        layout.addView(editText);
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        gc.myLayout.draw(mCanvas, mPaint);

        Point p = gc.getMeta().getDim();

        mPaint.setTextSize(p.x / 20);

        mCanvas.drawText("Please enter your nickname!",(p.x/5) ,p.y / 3, mPaint);

        layout.measure(mCanvas.getWidth(), mCanvas.getHeight());
        layout.layout(0, 0, mCanvas.getWidth(), mCanvas.getHeight());

//        To place the text view somewhere specific:
        mCanvas.translate(p.x/2 - editText.getWidth()/2, p.y/2 - editText.getHeight()/2);
        layout.bringChildToFront(editText);
        layout.draw(mCanvas);
        editText.bringToFront();


    }

    public void run() {

    }

    public void update() {

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        String name = editText.getText().toString();
        if (name.matches("")) {
            Log.d("USER","You must enter a username");
        } else {
            // review this shit
            gc.user = new BOUser(name, getScoreFromDatabase(name));
            gc.context = new GameTransitionState(gc);
        }
        return false;
    }

    public int getScoreFromDatabase(String name) {
        // if user exists in DB, get score corresponding to their name
        // else, return 0

        return 0;
    }
}
