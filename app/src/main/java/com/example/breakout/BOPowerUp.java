package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public abstract class BOPowerUp {

    public abstract void apply(BOGameController gc);
    public abstract String type();
    public abstract BOObject getMember();
    public abstract void time();

    public BOPowerUp randomPowerUp(BOGameController gc) {
        Random random = new Random();
        int choice = random.nextInt(4);

        if(choice == 0 || choice == 1 || choice == 2 || choice == 3) {
            return(new BODoubleBall(gc));
        }
        else if(choice == 1) {
            return(new BOExtendPaddle(gc));
        }
        else if(choice == 2) {
            return(new BOExtraLife(gc));
        }
        else if(choice == 3){
            return(new BODestroyBlocks(gc));
        }

        return null; // crash

    }

    public abstract void draw(Canvas mCanvas, Paint mPaint);
}
