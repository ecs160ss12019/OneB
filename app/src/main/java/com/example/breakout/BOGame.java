package com.example.breakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class BOGame extends SurfaceView implements Runnable {
    /*
        This class will handle anything and everything to do with actually
        controlling the game. It will also handle collisions and overall
        anything that would be involved in the 'play' state.

        This includes but is not limited to:
        - Moving the paddle
        - Handling block breaking logic
        - Moving the ball
        - Keeping track of lives (although alternativly the gameContrller can do that)

        Just to give you some kind of idea on what should go here.
    */

    // Actual Members
    private final boolean DEBUGGING = true;  // in case you cared / wondered
                                             // declaring something 'final
                                             // means it can be read, but not modified

    private final boolean fuckThisShit = false; // If this is true, a touch will just delete blocks one by one :^). Useful when u dont want to actually play the gam

    public BOGameController gameController; // stores a reference to our gameController
                                            // it's important this is accessible
                                            // by every class!!
    // Drawing Objects
    private SurfaceHolder holder; // So I didn't add 'm' here because
                                    // the name of the variable
                                  // is not the same as the class. take note
    private Canvas mCanvas;
    private Paint mPaint;

    // Frame-rate calculations
    private long FPS;
    private final int MILLIS_IN_SECONDS = 1000;

    // Hold resolution of the screen
    private int mScreenX;
    private int mScreenY;

    // Holds text size
    private int fontSize;
    private int fontMargin;

    // GameObjects
    private BOPaddle paddle;
    private BOBall ball;
    private ArrayList<BOBlock> blocks;

    private BOLayout myLayout;

    // For the music
    public MediaPlayer media;

    // Thread and Thread Handling
    private Thread gameThread = null;

    // Testing the timer
    BOTimer timer = new BOTimer();
    boolean won;

    // TODO: ALLOW USER TO PAUSE THE GAME. Maybe though a swipe?
    // TODO: Remove all of my Logs lol.
    public BOGame(BOGameController controller, Context context, int x, int y) {
        /*
            Standard constructor that takes a context (read GameController),
            and x (int) and y (int) coordinate. It will then proceed to
            use those coordinates to draw our game space using the
            canvas and paint tools available to use.
         */
        super(context);

        // Starts playing the music over the gameplay and loops it
        // We should consider only putting music on menu screen
        media = MediaPlayer.create(context, R.raw.game_soundtrack);

        gameController = controller;

        // initialize our screen size
        mScreenX = x;
        mScreenY = y;


        //TODO: Maybe there is a better configeration for our game?
        //  Somebody play with these values

        // By default we will use 5%(1/20th) of screen width for font size
        fontSize = mScreenX / 20;
        // Margin will be 1.5% (1/75th) of screen width
        fontMargin = mScreenX / 75;

        // Initialize the drawing objects
        holder = getHolder();
        mPaint = new Paint();

        // Initialize our game objects
        paddle = new BOPaddle(mScreenX, mScreenY);
        paddle.sprite = BitmapFactory.decodeResource(getResources(), R.drawable.pic); // initialize the sprit
        ball = new BOBall(mScreenX, paddle);
        ball.sprite = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        blocks = new ArrayList<>();

        // Initialize the layout
        myLayout = new BOLayout(mScreenX, mScreenY);
        myLayout.sprite = BitmapFactory.decodeResource(getResources(), R.drawable.background);


        // Start the game!
        startNewGame();
        Log.d("DEBUG: ", "BOGAME");


    }

    @Override
    public void run() {
        while (gameController.gameRunningState) {
            // What time is it now at the start of the loop?
            long frameStartTime = System.currentTimeMillis();
            // Provided the game isn't paused
            // call the update method
            if(!gameController.pauseState){
                update();
                media.start();
                media.setLooping(true);
                // Now the bat and ball are in
                // their new positions
                // we can see if there have
                // been any collisions
                detectCollisions();
            }
            else if(gameController.waitingState){ // just in case.
                waitingUpdate();
            }
            // The movement has been handled and collisions
            // detected now we can draw the scene.
            draw();
            // How long did this frame/loop take?
            // Store the answer in timeThisFrame
            long timeThisFrame =
                    System.currentTimeMillis() - frameStartTime;
            // Make sure timeThisFrame is at least 1 millisecond
            // because accidentally dividing
            // by zero crashes the game
            if (timeThisFrame > 0) {
                // Store the current frame rate in mFPS
                // ready to pass to the update methods of
                // mBat and mBall next frame/loop
                FPS = MILLIS_IN_SECONDS / timeThisFrame;
            }
        }

    }

    // handle touch controls
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        if(!timer.completed) { //disable controls if we need to wait for a timer.
            return false;
        }

        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: //placed finger on screen

                if(gameController.gameWonState || gameController.gameOverState) {

                    gameController.gameWonState = false;
                    gameController.gameOverState = false;
                }

                // un-pause the game
                gameController.pauseState = false;

                paddle.setReachedPosition(false); // tell the paddle
                                                  // we have a new movement
                                                  // command
                paddle.touched = motionEvent.getX();
                if(fuckThisShit) {
                    blocks.get(blocks.size() - 1).isDead = true;
                    blocks.get(blocks.size() - 1).collider = (new RectF(-1,-1,-1,-1));
                    blocks.remove(blocks.size()-1);
                }
                break;

        }
        return true;
    }

    private void initializeBlocks() {
        /*
        This function seeks to make 4 rows of blocks in a relatively symmetric manner
        by first creating a block to the left, then creating a block to the right.
        It will return nothing and take nothing as a parameter, but it modifies the blocks member.
         */

        //TODO: -> |PRIORITY| Right now the blocks can possible be asymmetric depending on screen resolution
        // It will also just generate a bunch of blocks until it cannot go through the screen anymore
        // This means depending on the resolution we can have a different number of blocks
        // What we want is a fixed amount of blocks AND to have those blocks be a fixed distance away from
        // one another. Therefore we need a mathematical function to help us
        // Step 1: Determine how much 'padding' we want on the screen edges (meaning how much distance do we want from the left most of the screen to the first block
        // and how much distance we want from the right most of the screen to the last block. This value should be the same)
        // Step 2: within the space between the two paddings, what should the size of the blocks be in order to fit "x" blocks within our space. We must change the BOBlock constructor to
        // account for the size change.
        // Step 3: How much distance do we want for the height, since we must do this 'n' times where n is the number of rows we want

        blocks.clear(); // reset our array-list;


        // distance between blocks
        float xMargin = (float)(mScreenX * .05); //TODO: These are magic numbers. See if we can find better ones
        float yMargin = (float)(mScreenY * .05);

        // padding for screen
        float padding = (float)(mScreenX* .1); // magic number.

        float curX = padding;
        float curY = 0;


        int numRows = 4; //controls the number of rows of blocks we want


        while (numRows > 0) {
            boolean canAddMore = true;
            float height = 0; // awkward scoping issue. We want to access the height of the block, but we dont declare a block object until the for loop.
            // fortunately the block height is always constant so we can just repeatly update the height value.
            // its low-key dumb and repetition of code, but hey we do what we gotta do.

            while (canAddMore) {
                BOBlock temp = new BOBlock(mScreenX, mScreenY, curX + xMargin, curY + yMargin, gameController);
                blocks.add(temp);
                height = temp.getHeight();
                curX += (temp.getLength() + xMargin);

                if (curX + xMargin + temp.getLength() + padding > mScreenX) {
                    canAddMore = false;
                }
            }
            curY += (height + yMargin);
            curX = padding;
            numRows--;
        }


    }

    private void startNewGame() {
        // Reset our game objects

        // Reset the score
        gameController.score = 0;

        // Reset lives
        gameController.lives = 3;

        // Set the state to gameRunning
        ball.reset(mScreenX, mScreenY);
        gameController.gameRunningState = true;
        initializeBlocks();

        // Creating RNG for purpose of randomizing block color
        Random blockGen = new Random();

        // Initializing the sprites of the blocks
        for(int i = 0; i < blocks.size(); i++) {

            int choice = blockGen.nextInt();
            choice = Math.abs(choice); // make sure choice is always positive

            int mod = choice % 5;

            if (mod == 0)
                blocks.get(i).sprite = BitmapFactory.decodeResource(getResources(), R.drawable.vanilla_caramel_choco);
            else if (mod == 1)
                blocks.get(i).sprite = BitmapFactory.decodeResource(getResources(), R.drawable.strawberry_choco);
            else if (mod == 2)
                blocks.get(i).sprite = BitmapFactory.decodeResource(getResources(), R.drawable.matcha_choco);
            else
                blocks.get(i).sprite = BitmapFactory.decodeResource(getResources(), R.drawable.choco_brown);

        }

    }

    private void draw() {
        /*
            This function takes nothing as a parameter and returns nothing.
            all it does is modify our mCanvas object and draws our playing
            area.
         */
        if(holder.getSurface().isValid()) {
            // lock the canvas and ready to draw
            mCanvas = holder.lockCanvas();

            // Order matters. This should be drawn before the ball and paddle.
            myLayout.draw(mCanvas, mPaint);
            // Choose a color to paint with
            mPaint.setColor(Color.argb
                    (255, 255, 255, 255));

            // Draw in our Game Objects
            ball.draw(mCanvas, mPaint);
            paddle.draw(mCanvas, mPaint);


            // Draw the blocks
            for(int i = 0; i < blocks.size(); i++) {
                blocks.get(i).draw(mCanvas, mPaint);

            }

            mPaint.setTextSize(fontSize);

            //Draw the HUD
            //TODO: Draw the hud.

            mPaint.setColor(Color.argb
                    (255, 0, 255, 0));

            // Check to see if the player won
            if(won) {
                gameController.gameWonState = true;
                gameController.pauseState = true;
            }

            if(gameController.pauseState) {
                if (won) { // You won the game!
                    mCanvas.drawText("You won!!!",
                            mScreenX / 3, mScreenY / 2, mPaint);
                    Log.v("tag", "won");
                }
                else if(gameController.lives == 0){ // No more lives
                    gameController.gameOverState = true;
                    mCanvas.drawText("You lose and you suck! ;)",
                            mScreenX / 3, mScreenY / 2, mPaint);
                    startNewGame();
                    gameController.pauseState = true;
                }
                else { // generic pause
                    mCanvas.drawText("Tap To Resume!",
                            mScreenX / 3, mScreenY / 2, mPaint);
                    Log.v("tag", "resume");

                }
            }
            int scoreSize = fontSize / 2;
            mPaint.setTextSize(scoreSize);
            mCanvas.drawText("Score: " + gameController.score,mScreenX / 55,mScreenY / 9, mPaint);
            mCanvas.drawText("Lives: " + gameController.lives,mScreenX / 55,mScreenY / 20, mPaint);

            if(DEBUGGING) {
                printDebuggingText();
            }

            holder.unlockCanvasAndPost(mCanvas);

        }
    }

    private void printDebuggingText(){
        int debugSize = fontSize / 2;
        int debugStart = 150;
        mPaint.setTextSize(debugSize);
        mCanvas.drawText("FPS: " + FPS ,
                10, debugStart + debugSize, mPaint);
        mCanvas.drawText("createState: " + gameController.createState ,
                11, debugStart + debugSize * 10, mPaint);
        mCanvas.drawText("newGameState: " + gameController.newGameState ,
                12, debugStart + debugSize * 11, mPaint);
        mCanvas.drawText("gameRunningState: " + gameController.gameRunningState ,
                13, debugStart + debugSize * 12, mPaint);
        mCanvas.drawText("pauseState: " + gameController.pauseState ,
                14, debugStart + debugSize * 13, mPaint);
      //  mCanvas.drawText("ballPos: " + ball.collider.top + " " + ball.collider.left + " " + ball.getCollider().bottom + " " + ball.getCollider().right ,
        //        15, debugStart + debugSize * 14, mPaint);
        mCanvas.drawText("speed: " + ball.getSpeed(),
                15, debugStart + debugSize * 14, mPaint);

        mCanvas.drawText("Completed: " + timer.completed,
                16, debugStart + debugSize * 15, mPaint);
        mCanvas.drawText("WOn: " + won,
                17, debugStart + debugSize * 16, mPaint);
    }

    public void pause() {
        // set the playing state to false
        // and attempt to stop the thread

        // Make sure the game is running before pausing. Maybe
        // it doesn't make sense to pause during a game_over screen?



        if(gameController.gameRunningState) {

            gameController.gameRunningState = false; // leave the running state
            gameController.pauseState = true; // enter the pause state
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error: ", "joining thread");
            }
        }

    }

    public void resume() {
        if(gameController.pauseState) {
            gameController.pauseState = false;
            gameController.gameRunningState = true;

            // Initialize instance of the thread
            gameThread = new Thread(this);

            gameThread.start();
            if(gameController.newGameState) {
                gameController.pauseState = true; //immediately pause yourself and wait for input ... ONLY IF THE BEGINING OF THE GAME
                gameController.newGameState = false;

                // This was a concurrency bug that caused the ball to run off before the thread had time to draw it in and process it.
                // Also it messed up my attempts at a deliberate pause and resume since resuming the game unintentionally just paused it again right afterwards
            }


        }
    }

    private void update() {
        // update paddle and ball
        ball.update(FPS);
        paddle.update(FPS);

        for(int i = 0; i < blocks.size(); i++)
        {
            blocks.get(i).update(ball); // if collided with ball
        }
        won = wonGame();
    }

    private void waitingUpdate() { // use this function if you want things to be checked and updated during a game pause
        if(won && timer.completed) {
            gameController.pauseState = false; // unpause briefly so the code can update
            startNewGame();
            gameController.pauseState = true; // Pause immediately after
            Log.d("WON: ", "we Won!");
            gameController.waitingState = false;
            won = false;
        }
    }


    //TODO: Add a side hitbox to the paddle.
    private void detectCollisions() {
        // Has our ball hit the paddle?
        if(RectF.intersects(paddle.collider, ball.getCollider())) {
            // realistic bounce
            ball.getCollider().bottom = paddle.collider.top + (float).01; // shhhhh. We're making it so the ball isn't constantly colliding
            ball.blockBounce(paddle.collider);
            ball.incrementSpeed(50);
        }

        //handle walls

        // bottom wall
        if(ball.getCollider().bottom >= mScreenY) {
            ball.reset(mScreenX, mScreenY);

            gameController.lives--;
            Log.d("Lives:", "" + gameController.lives);

            // user just lost a life and the game isn't over in this part of the statement
            // pause the game so player can get their bearings
            gameController.pauseState = true;

            // Phillip Note: I moved the code here into the draw() method in order to
            // display game over text so that draw didn't just immediately overwrite it
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
        if(ball.getCollider().right > mScreenX) {
            ball.getCollider().right = mScreenX + 10;
            ball.reverseXVelocity();
        }
    }

    private boolean wonGame() {
        for(int i = 0; i < blocks.size(); i++)
        {
            if(blocks.get(i).getDeadStatus() == false)
                return false;
        }
        timer.run(5000L);
        gameController.waitingState = true; // make the game wait
        return true;
    }
}
