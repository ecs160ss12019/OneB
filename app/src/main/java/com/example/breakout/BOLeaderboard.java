package com.example.breakout;

import android.graphics.RectF;
import android.util.Log;

public class BOLeaderboard extends BOObject {

    float leaderHeight;

    BOLeaderboard(int screenWidth, int screenHeight) {
        float leaderWidth = screenWidth / (float) 1.75;
        this.leaderHeight = screenHeight / (float) 1.75;

        Log.d("Leader: ", "" + leaderWidth);
        Log.d("Leader: ", "" + screenWidth);
        Log.d("Leader: ", "" + screenWidth);
        collider = new RectF((screenWidth / 2) - (leaderWidth / 3), (screenHeight / 2) - ((float) (leaderHeight / 1.5)), (screenWidth / 2) + (leaderWidth / 3), (screenHeight / 2) + ((float) (leaderHeight / 1.5)));

    }
}
