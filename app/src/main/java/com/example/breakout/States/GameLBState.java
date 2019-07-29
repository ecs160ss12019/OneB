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
    private BOLeaderboardItem rank1;
//    private List<BOLeaderboardItem> places = new ArrayList<>(SIZE);

    public GameLBState(BOGameController gc) {
        super(gc);
        rank1 = new BOLeaderboardItem((int)gc.getMeta().getDim().x, (int)gc.getMeta().getDim().y, gc.user, 1, gc, gc.menu.collider.top);
//        places.add(new BOLeaderboardItem((int)gc.getMeta().getDim().x, (int)gc.getMeta().getDim().y, places.get(0).text, gc, 5));
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        gc.myLayout.draw(mCanvas, mPaint); // draw the background over the blocks.
        gc.leaderboard.draw(mCanvas, mPaint);

        rank1.draw(mCanvas, mPaint);
    }

    public void run(){

    }

    public void update() {

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: //placed finger on screen
                if (motionEvent.getX() < gc.menu.collider.left || motionEvent.getX() > gc.menu.collider.right || motionEvent.getY() > gc.menu.collider.bottom
                        || motionEvent.getY() < gc.menu.collider.top) {
                    gc.context = new GamePauseState(gc);

                }


        }
        return true;
    }
}
