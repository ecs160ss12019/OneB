package com.example.breakout.States;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Size;
import android.view.MotionEvent;

import com.example.breakout.BOGameController;
import com.example.breakout.BOLeaderboardItem;

import java.util.ArrayList;
import java.util.List;

public class GameLBState extends State {
    private static final int SIZE = 5;
    private List<BOLeaderboardItem> places = new ArrayList<>(SIZE);

    public GameLBState(BOGameController gc) {
        super(gc);
        places.add(new BOLeaderboardItem(gc));
    }

    public void draw(Canvas mCanvas, Paint mPaint) {

    }

    public void run(){

    }

    public void update() {

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

}
