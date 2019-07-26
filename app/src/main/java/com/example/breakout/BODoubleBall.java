//package com.example.breakout;
//
//import android.graphics.BitmapFactory;
//import android.graphics.RectF;
//import android.util.Log;
//
//import com.example.breakout.States.GameOverState;
//import com.example.breakout.States.GameWaitingState;
//
//public class BODoubleBall extends BOPowerUp {
//    public BOGameController gc;
//    private BOBall doubleBall;
//
//    public BODoubleBall(BOGameController gc){
//        this.gc = gc;
//        doubleBall = new BOBall((int) gc.getMeta().getDim().x, gc.ball.sprite);
//        doubleBall.sprite = BitmapFactory.decodeResource(gc.getResources(), R.drawable.ball);
//        doubleBall.collider = new RectF(1, 1, 1, 1);
//    }
//
//    // Code the logic of our power-up.
//    public void apply(BOGameController gc) {
//        doubleBall.reset(gc.paddle); // puts the ball at the paddle
//        doubleBall.draw(gc.mBOGame.mCanvas, gc.mBOGame.mPaint);
//        doubleBall.update(gc.getMeta().getFPS());
//        Log.d("In Apply Function", "True");
//        Log.d("Doubleball's Collider", ""+ doubleBall.collider);
//        detectCollisions(doubleBall);
//
//    }
//
//    private void detectCollisions(BOBall ball) {
//        // Has our ball hit the paddle?
//        if(RectF.intersects(gc.paddle.collider, ball.getCollider())) {
//            // realistic bounce
//            ball.getCollider().bottom = gc.paddle.collider.top + (float).01; // shhhhh. We're making it so the ball isn't constantly colliding
//            ball.blockBounce(gc.paddle.collider);
//            ball.incrementSpeed(10);
//        }
//
//        // handle walls
//        Point dim = gc.getMeta().getDim();
//
//        // bottom wall
//        if(ball.getCollider().bottom >= dim.y) {
//
//            if (gc.lives > 0) {
//                gc.lives--;
//                Log.d("Lives:", "" + gc.lives);
//
//                gc.context = new GameWaitingState(gc);
//                // user just lost a life and the game isn't over in this part of the statement
//                // pause the game so player can get their bearings
//
//            }
//            if(gc.lives == 0){ // Fun scoping issue.
//                gc.context = new GameOverState(gc);
//
//            }
//        }
//
//        // Top wall
//        if(ball.getCollider().top < 0) {
//            ball.getCollider().top = 0;
//            ball.reverseYVelocity();
//        }
//
//        // Left Wall
//        if(ball.getCollider().left < 0) {
//            ball.getCollider().left = 0;
//            ball.reverseXVelocity();
//        }
//
//        // Right wall
//        if(ball.getCollider().right > dim.x) {
//            ball.getCollider().left = dim.x - ball.getCollider().width();
//            ball.getCollider().right = dim.x;
//            ball.reverseXVelocity();
//        }
//    }
//}
//
