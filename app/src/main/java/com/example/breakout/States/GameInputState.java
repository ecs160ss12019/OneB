package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.breakout.BOGameController;
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
    }

    public void run() {

    }

    public void update() {

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
//        gc.context = new GameWaitingState(gc);
        return false;
    }
}
