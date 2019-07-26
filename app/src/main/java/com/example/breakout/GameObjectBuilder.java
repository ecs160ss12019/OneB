package com.example.breakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;

public class GameObjectBuilder {

    /*
    Main Factory for all our GameObjects

     */

    private RectF collider;
    private Bitmap sprite;

    // private members
    private float length;
    private float height;
    private Point pos; // Note, here we are using our own custom Point object that can handle floats
    private Context resources;

    public GameObjectBuilder() // default constructor initializes everything to null / 0
    {
        collider = null;
        sprite = null;
        length = 0;
        height = 0;
        pos = null;
    }

    public BOPaddle buildPaddle() {
        return new BOPaddle((int)pos.x, (int)pos.y, resources);
    }

    public BOBall buildBall() {
        return new BOBall((int)pos.x, sprite);
    }

    public BOLayout buildLayout() {
        return new BOLayout(pos,  sprite, collider);
    }

    public void setCollider(RectF c){
        collider = c;
    }

    public void setSprite(Bitmap s) {
        sprite = s;
    }

    public void setHeight(float h) {
        height = h;
    }


    public void setPos(int x, int y) {
        pos = new Point(x,y);
    }
    public void setPos(Point p) {
        pos = p;
    }


    public void setResources(Context resources) {
        this.resources = resources;
    }

    public void clear() { // resets our builder back to default parameters in case we need to reset some whatever reason.
        collider = null;
        sprite = null;
        length = 0;
        height = 0;
        pos = null;
    }


}
