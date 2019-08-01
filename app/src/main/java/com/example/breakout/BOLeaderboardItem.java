package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class BOLeaderboardItem extends BOObject {

    float leaderWidth;
    float leaderHeight;
    private int screenWidth;
    private int screenHeight;

    private BOGameController gc;

    private int rank;
    public String name;
    private int score;

    public BOLeaderboardItem(int screenWidth, int screenHeight, BOGameController gc, BORecord record, float top) {
        this.gc = gc;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        leaderWidth = this.screenWidth / (float)1.75;
        leaderHeight = this.screenHeight / (float)10;

        this.rank = record.rank;
        name = record.name;
        score = record.highScore;

        collider = new RectF( gc.leaderboard.collider.left, top + gc.leaderboard.leaderHeight/20, gc.leaderboard.collider.right, (top + gc.leaderboard.leaderHeight/20) + leaderHeight);
    }


    public void drawText(Canvas mCanvas, Paint mPaint) {

        Rect bounds = new Rect();
        String text = rank + ". \t\t\t" + name + "\t\t\t" + score;

        mPaint.getTextBounds(text, 0, text.length(), bounds);
        float x = (collider.left + collider.width()/10);
        float y = ((collider.bottom - collider.height() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2)) ; // code stolen from : https://stackoverflow.com/questions/3630086/how-to-get-string-width-on-android

        mPaint.setTextSize(leaderHeight/(float)1.5);
        mPaint.setColor(Color.argb(255,255,255,255));
        mCanvas.drawText(text, x , y, mPaint);
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        mPaint.setColor(Color.argb(255,222,113,144));
        mCanvas.drawRect(collider, mPaint);
        drawText(mCanvas, mPaint);

        mPaint.setColor(Color.argb(255,222,113,144));
    }

}
