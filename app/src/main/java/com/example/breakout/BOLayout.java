package com.example.breakout;

import android.graphics.Bitmap;
import android.graphics.RectF;

public class BOLayout extends BOObject {


    public BOLayout(Point pos, Bitmap sprite, RectF c)
    {
        super(pos.x , pos.y, new Point(0,0));
        collider = c;
        this.sprite = sprite;
    }

}
