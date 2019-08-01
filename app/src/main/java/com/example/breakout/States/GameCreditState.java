package com.example.breakout.States;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.breakout.BOGameController;
import com.example.breakout.BOTimer;
import com.example.breakout.GameObjectBuilder;
import com.example.breakout.Point;
import com.example.breakout.R;

public class GameCreditState extends State{

    /*
    Our credits screen :^) Mostly just still images being display on tap
     */

    public GameCreditState(BOGameController gc) {
        super(gc);
    }

    public void draw(Canvas mCanvas, Paint mPaint)
    {
        Point dim  = gc.getMeta().getDim();
        gc.myLayout.draw(mCanvas, mPaint);
        mCanvas.drawText("Credits:", dim.x/(float)2.5, dim.y/15, mPaint);
        mCanvas.drawText("Phillip   Tran   -   Degenerate   Leader" , dim.x/(float)15, dim.y/5, mPaint);
        mCanvas.drawText("Michel   Eter   -   Spells   'Michael'   Incorrectly" , dim.x/(float)15, (dim.y/5)*2, mPaint);
        mCanvas.drawText("Kira   Bender   -   Making   Bad    Puns" , dim.x/(float)15, (dim.y/5)*3, mPaint);
        mCanvas.drawText("Zain   Munad   -   Consistently   Tardy" , dim.x/(float)15, (dim.y/5)*4, mPaint);
        mCanvas.drawText("Gabriella   Quattrone   -   Weeb" , dim.x/(float)15, (dim.y/5)*5, mPaint);
    }

    public void update() {  // stubby boiz

    }

    public void run() {

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {

        return false;
    }
}
