package com.example.breakout;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class BOKeyboard {

    // Because it was hard to implement an actual keyboard.

    Key keys[] = new Key[30];

    public BOKeyboard(BOGameController gc) {

        Point dim = gc.getMeta().getDim();
        float keyWidth = dim.x / 15;
        float keySpace = dim.x / 400;


        float padding = (dim.x - (keyWidth * 10)) / 2;

        float LB = padding;
        float RB = padding + keyWidth;
        float TB = dim.y - keyWidth - keySpace;
        float BB = dim.y - keySpace;


        char letters[] = {'z','x','c','v','b','n','m',',','.','!', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', 'q','w','e','r','t','y','u','i','o','p'};
        for(int j = 0; j < 3; j++) {
            for (int i = 0; i < 10; i++) {
                keys[(10 * j) + i] = new Key(letters[(10 * j) + i], new RectF(LB, TB, RB, BB));

                LB += (keyWidth + keySpace);
                RB += (keyWidth + keySpace);

            }
            TB -= (keyWidth + keySpace);
            BB -= (keyWidth + keySpace);
            LB = padding;
            RB = padding + keyWidth;
        }


    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        for(int i = 0; i < 30; i++) {
            keys[i].draw(mCanvas, mPaint);
        }
    }

    public char returnTouched(RectF touched) {
        for(int i = 0; i < 30; i++)
        {

            if(RectF.intersects(keys[i].box, touched))
            {
                return keys[i].key;
            }
        }
        return '-';
    }


}
