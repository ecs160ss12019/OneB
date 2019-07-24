package com.example.breakout.States;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.breakout.BOGameController;

public class GameInputState extends State {
    EditText input;

    public GameInputState(BOGameController gc) {
        super(gc);
        input = new EditText(gc);
        input.setText("TEST");
        input.setWidth(180);
        input.setBackgroundColor(Color.WHITE);
    }

    public void draw(Canvas mCanvas, Paint mPaint) {

        Log.d("Canvas Status:", ""+mCanvas);

        input.setDrawingCacheEnabled(true);
        Bitmap b = input.getDrawingCache();
        Log.d("Bitmap Status:", ""+b);
        mCanvas.drawBitmap(b, (float)gc.mScreenX/2, (float)gc.mScreenY/2, null);
    }

    public void run() {

    }

    public void update() {

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }
}
