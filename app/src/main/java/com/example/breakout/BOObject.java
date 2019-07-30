package com.example.breakout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class BOObject {
    /*
    The BOObject is an abstraction of our game objects.
    Each game object should inherit from BOObject if it is an object that needs a position
    on the canvas

    Each BOObject should have a length(float), height(float), a position on the coordinate field (Point), a collider (RectF), and a sprite(Bitmap)

     */
    // public members
    public RectF collider;
    public Bitmap sprite;



    // private members
    private float length;
    private float height;
    private Point pos; // Note, here we are using our own custom Point object that can handle floats


    BOObject() {
          /*
        Constructor that takes in a length (float) , a height (float) and a position coordination (Point obj)
        and initializes it's members accordingly
         */
        length = 0;
        height = 0;
        pos = new Point(0,0);
        sprite = null;
    }

   BOObject(float len, float hei, Point p) {
        /*
        Constructor that takes in a length (float) , a height (float) and a position coordination (Point obj)
        and initializes it's members accordingly
         */
        length = len;
        height = hei;
        pos = p;
        sprite = null;
    }

    // getters and setters
    float getLength() {
        return length;
    }

    float getHeight (){
        return height;
    }

    public Point getPos() {
        return pos;
    }

    void translateX(float value) {
        /*
            translates (moves) across the X axis by 'value' (float)
            essentially this will change the xposition of our object
         */
        pos.x += value;
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        if(sprite == null)
        {
            Log.e("ERR","Failed to decode resource - Sprite Block " );
        }else {
            mCanvas.drawBitmap(sprite, null, collider, null);
        }
    }

    public void setPos(Point p) {
        pos = p;
    }


}
