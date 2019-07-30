package com.example.breakout;

import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.util.Log;

public class BOExtendPaddle extends BOPowerUp {

    public BOGameController gc;
    private BOPaddle paddle;
    private BOPaddle oldPaddle;

    public BOExtendPaddle (BOGameController gc){
        this.gc = gc;
        paddle = new BOPaddle((int) gc.getMeta().getDim('x') + 1000, gc.getMeta().getDim('y') ,gc.resources);

        paddle.collider = new RectF(paddle.getPos().x , paddle.getPos().y - 30, paddle.getPos().x + paddle.getLength(), paddle.getPos().y + paddle.getHeight() - 10);

        while(paddle.collider.left > gc.paddle.collider.left) {
            paddle.collider.left -= 1;
            paddle.collider.right -= 1;

        }

        paddle.setPos(gc.paddle.getPos());

        oldPaddle = gc.paddle;
        gc.paddle = paddle;
        gc.timer.run(11000); // 10,000 is about 5 seconds
    }

    @Override
    public void apply(BOGameController gc) {

        if (gc.timer.completed) {
            time();
        }
    }
    public String type(){
      return "Long Paddle";
    }

    public BOObject getMember() {
        return paddle;
    }

    @Override
    public void time() {
        gc.paddle = oldPaddle;
    }
}
