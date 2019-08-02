package com.example.breakout;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class BODoubleBall extends BOPowerUp {
    public BOGameController gc;
    private BOBall doubleBall;

    public BODoubleBall(BOGameController gc){
        this.gc = gc;
        if (gc.currentLevel == 3 || gc.currentLevel == 5){
            doubleBall = new BOBall((int)(gc.getMeta().getDim().x * 3),BitmapFactory.decodeResource(gc.resources.getResources(), R.drawable.daryl) );
            doubleBall.sprite = BitmapFactory.decodeResource(gc.getResources(), R.drawable.daryl);
        }
        else {
            doubleBall = new BOBall((int) gc.getMeta().getDim().x, gc.ball.sprite);
            doubleBall.sprite = BitmapFactory.decodeResource(gc.getResources(), R.drawable.ball);
            doubleBall.collider = new RectF(1, 1, 10, 10);
        }
        doubleBall.reset(gc.paddle); // puts the ball at the paddle
        doubleBall.reverseXVelocity();

    }

    // Code the logic of our power-up.
    public void apply(BOGameController gc) {
        Log.d("doubleBall:", ""+doubleBall);
        Log.d("doubleBall:", ""+doubleBall.sprite);
        Log.d("doubleBall:", ""+gc.mBOGame.mCanvas);
        Log.d("doubleBall:", ""+gc.mBOGame.mPaint);

        doubleBall.draw(gc.mBOGame.mCanvas, gc.mBOGame.mPaint);
        doubleBall.update(gc.getMeta().getFPS());

        Log.d("In Apply Function", "True");
        Log.d("Doubleball's Collider", ""+ doubleBall.collider);

        detectCollisions(doubleBall);
    }

    public void draw(Canvas mCanvas, Paint mPaint)
    {
        System.out.println("Original Ball Speed: " + gc.ball.getSpeed());
        System.out.println("Double Ball Speed: " + doubleBall.getSpeed());
        Point dim = gc.getMeta().getDim();
        mCanvas.drawText(this.type(), dim.x / 55,dim.y / 4, mPaint);
    }


    private void detectCollisions(BOBall ball) {

        for(int i = 0; i < gc.blocks.size(); i++)
        {
            gc.blocks.get(i).update(doubleBall); // if collided with ball
        }
        // Has our ball hit the paddle?
        if(RectF.intersects(gc.paddle.collider, ball.getCollider())) {
            // realistic bounce
            ball.getCollider().bottom = gc.paddle.collider.top + (float).01; // shhhhh. We're making it so the ball isn't constantly colliding
            ball.blockBounce(gc.paddle.collider);
            ball.incrementSpeed(10);
        }

        // handle walls
        Point dim = gc.getMeta().getDim();

        // bottom wall
        if(ball.getCollider().bottom >= dim.y) {

            gc.powerUp = new BONoPowerUp(gc);
        }

        // Top wall
        if(ball.getCollider().top < 0) {
            ball.getCollider().top = 0;
            ball.reverseYVelocity();
        }

        // Left Wall
        if(ball.getCollider().left < 0) {
            ball.getCollider().left = 0;
            ball.reverseXVelocity();
        }

        // Right wall
        if(ball.getCollider().right > dim.x) {
            ball.getCollider().left = dim.x - ball.getCollider().width();
            ball.getCollider().right = dim.x;
            ball.reverseXVelocity();
        }
    }

    public String type() {
        return "Double Ball";
    }

    public BOBall getMember() {
        return doubleBall;
    }

    @Override
    public void time() {
        gc.powerUp = new BONoPowerUp(gc);

    }
}

