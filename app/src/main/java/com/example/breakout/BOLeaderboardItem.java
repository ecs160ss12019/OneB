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
    private int rank;
    private String name;
    private BOGameController gc;
    private int score;

    public BOLeaderboardItem(int screenWidth, int screenHeight, BOUser user, int rank, BOGameController gc, float top) {
        this.gc = gc;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        leaderWidth = this.screenWidth / (float)1.75;
        leaderHeight = this.screenHeight / (float)7.5;

        this.rank = rank;
        name = user.nickname;
        score = user.score;

        collider = new RectF( gc.menu.collider.left, top + gc.menu.menuHeight/4, gc.menu.collider.right, (top + gc.menu.menuHeight/4) + leaderHeight);
    }


    public void drawText(Canvas mCanvas, Paint mPaint) {

        Rect bounds = new Rect();
        mPaint.getTextBounds(name, 0, name.length(), bounds);
        float x = (collider.left + collider.width() / (float)3.50);
        float y = ((collider.bottom - collider.height() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2)) ; // code stolen from : https://stackoverflow.com/questions/3630086/how-to-get-string-width-on-android

        mPaint.setTextSize(leaderHeight/(float)1.5);
        mPaint.setColor(Color.argb(255,255,255,255));
        mCanvas.drawText(name, x , y, mPaint);
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        mPaint.setColor(Color.argb(255,222,113,144));
        mCanvas.drawBitmap(sprite,null,gc.menu.collider, mPaint);
        drawText(mCanvas, mPaint);

        //leaderBoard = new BOMenuButton((int)dim.x, (int)dim.y, "Leaderboard", gc, );
        mPaint.setColor(Color.argb(255,222,113,144));
        Point dim = gc.getMeta().getDim();
        mCanvas.drawText("Leaderboard",(int)collider.left + collider.width() / (float)6.5, collider.top - 50, mPaint);

    }

}
