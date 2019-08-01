package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class BOLeaderboard extends BOObject {

    public float leaderHeight;
    public float leaderWidth;
    private BOGameController gc;

    BOLeaderboard(int screenWidth, int screenHeight, BOGameController gc) {
        this.gc = gc;
        this.leaderWidth = screenWidth / (float) 1.75;
        this.leaderHeight = screenHeight / (float) 1.75;

        collider = new RectF((screenWidth / 2) - (leaderWidth / 3), (screenHeight / 2) - ((float) (leaderHeight / 1.5)), (screenWidth / 2) + (leaderWidth / 3), (screenHeight / 2) + ((float) (leaderHeight / 1.5)));

    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        mPaint.setColor(Color.argb(255,222,113,144));
        mCanvas.drawBitmap(sprite,null, gc.leaderboard.collider, mPaint);

        mPaint.setColor(Color.argb(255,222,113,144));
        mCanvas.drawText("Leaderboard",(int)collider.left + collider.width() / (float)6.5, collider.top+100, mPaint);
    }

}
