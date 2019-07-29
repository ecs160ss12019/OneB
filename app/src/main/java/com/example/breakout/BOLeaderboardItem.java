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
    public String text;

    public BOLeaderboardItem(int screenWidth, int screenHeight, String text, BOGameController gc, float top) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.leaderWidth = this.screenWidth / (float)1.75;
        this.leaderHeight = this.screenHeight / (float)7.5;
        this.text = text;

        collider = new RectF( gc.menu.collider.left, top + gc.menu.menuHeight/4, gc.menu.collider.right, (top + gc.menu.menuHeight/4) + leaderHeight);

    }

    public void drawText(Canvas mCanvas, Paint mPaint) {
    }

}
