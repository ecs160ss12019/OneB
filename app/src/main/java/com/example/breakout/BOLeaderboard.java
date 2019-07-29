package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class BOLeaderboard extends BOObject {

    float leaderBHeight;

    BOLeaderboard(int screenWidth, int screenHeight, BOGameController gc, Canvas mCanvas, Paint mPaint) {
        float leaderBWidth = screenWidth / (float) 1.75;
        this.leaderBHeight = screenHeight / (float) 1.75;

        Log.d("MENU: ", "" + leaderBWidth);
        Log.d("MENU: ", "" + screenWidth);
        Log.d("MENU: ", "" + screenWidth);
        collider = new RectF((screenWidth / 2) - (leaderBWidth / 3), (screenHeight / 2) - ((float) (leaderBHeight / 1.5)), (screenWidth / 2) + (leaderBWidth / 3), (screenHeight / 2) + ((float) (leaderBHeight / 1.5)));

    }
}
