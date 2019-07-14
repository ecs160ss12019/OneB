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

    // Framerate calculations
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

    // For the music
    private MediaPlayer media;

    // Thread and Thread Handling
    private Thread gameThread = null;


    // TODO: ALLOW USER TO PAUSE THE GAME. Maybe though a swipe?
    // TODO: Remove all of my Logs lol.
    // TODO: -> |PRIORITY| You can't actually lose the game yet lol.
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
        paddle.sprite = BitmapFactory.decodeResource(getResources(), R.drawable.pic); // initalize the sprit
        ball = new BOBall(mScreenX, paddle);
        ball.sprite = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        blocks = new ArrayList<>();

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

        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: //placed finger on screen

                if(gameController.gameWonState) {
                    gameController.gameWonState = false;
                    startNewGame();
                }

                // un-pause the game
                gameController.pauseState = false;

                paddle.setReachedPosition(false); // tell the paddle
                                                  // we have a new movement
                                                  // command
                paddle.touched = motionEvent.getX();
                if(fuckThisShit) {
                    blocks.get(blocks.size() - 1).isDead = true;
                    blocks.get(blocks.size() - 1).setCollider(new RectF(-1,-1,-1,-1));
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


        // distance between blocks
        float xMargin = (float)(mScreenX * .05); //TODO: These are magic numbers. See if we can find better ones
        float yMargin = (float)(mScreenY * .05);

        // padding for screen
        float padding = (float)(mScreenX* .1); // magic number.

        float curX = padding;
        float curY = 0;


        int numRows = 4; //controls the number of rows of blocks we want


        while(numRows > 0) {
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
            numRows --;
        }


    }

    private void startNewGame() {
        // Reset our game objects

        // Reset the score
        gameController.score = 0;

        //reset lives
        gameController.lives = 3;

        // Set the state to gameRunning
        blocks.clear();
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

        /*
            if (mod == 0) {
                blocks.get(i).sprite = BitmapFactory.decodeResource(getResources(), R.drawable.vanilla_caramel_choco);
            }else if (mod == 1)
                blocks.get(i).sprite = BitmapFactory.decodeResource(getResources(), R.drawable.strawberry_choco);
            else if (mod == 2)
                blocks.get(i).sprite = BitmapFactory.decodeResource(getResources(), R.drawable.matcha_choco);
            else if (mod == 4)
                blocks.get(i).sprite = BitmapFactory.decodeResource(getResources(), R.drawable.choco_brown);
        */
            blocks.get(i).sprite = BitmapFactory.decodeResource(getResources(), R.drawable.vanilla_caramel_choco);

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

            /*
                A sidebar and allow me to once again sell you on state machines.
                Lets say you wanted to at the very start of a new game you
                wanted to draw a message like "HEY Good luck!!".

                We can't just have it draw that message every single time the draw
                method is called, because we only want to do it once at the begiining!

                A state machine is really handy in this regards because we have a state
                called newGameState, therefore we could do something like

                if(BOGameController.newGameState) {
                    Draw our message!
                }

                I note that this is probably a solution most people would
                probably have thought about, using a boolean and referencing it,
                however think about where you personally would've put the
                boolean, if you're anything like me you would've put it in this
                class, however in doing so you also made a unique boolean variable
                that you used only once (in this class), furthermore you've
                made interaction with other code a bit more difficult! Just a sidebar.
             */

            // fill screen with solid color
            mCanvas.drawColor(Color.argb(255,26,128,182));

            // Choose a color to paint with
            mPaint.setColor(Color.argb
                    (255, 255, 255, 255));

            // Draw in our Game Objects
            ball.draw(mCanvas, mPaint);
            paddle.draw(mCanvas, mPaint);

            // draw the blocks
            for(int i = 0; i < blocks.size(); i++) {
                blocks.get(i).draw(mCanvas, mPaint);

            }

            mPaint.setTextSize(fontSize);

            //Draw the HUD
            //TODO: Draw the hud.

            mPaint.setColor(Color.argb
                    (255, 0, 255, 0));

            // Check to see if the player won
            boolean won = wonGame();
            if(won) {
                gameController.gameWonState = true;
                gameController.pauseState = true;
            }

            if(gameController.pauseState) {
                if (won) {
                    mCanvas.drawText("You won!!!",
                            mScreenX / 3, mScreenY / 2, mPaint);
                    Log.v("tag", "won");
                } else {
                    mCanvas.drawText("Tap To Resume!",
                            mScreenX / 3, mScreenY / 2, mPaint);
                    Log.v("tag", "resume");

                }
            }
            int scoreSize = fontSize / 2;
            mPaint.setTextSize(scoreSize);
            mCanvas.drawText("Score: " + gameController.score,mScreenX / 55,mScreenY / 9, mPaint);

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
      //  mCanvas.drawText("ballPos: " + ball.getCollider().top + " " + ball.getCollider().left + " " + ball.getCollider().bottom + " " + ball.getCollider().right ,
        //        15, debugStart + debugSize * 14, mPaint);
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
        // ELSE? Maybe have tapping the screen do something else if at an alternative
        // screen. DAMN LOOK AT THAT FLEXIBILITY :^)
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

                // This was a concurrancy bug that caused the ball to run off before the thread had time to draw it in and process it.
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

    }


    //TODO: Add a side hitbox to the paddle.
    private void detectCollisions() {
        // Has our ball hit the paddle?
        if(RectF.intersects(paddle.getCollider(), ball.getCollider())) {
            // realistic bounce
            ball.getCollider().bottom = paddle.getCollider().top + (float).01; // shhhhh. We're making it so the ball isn't constantly colliding
            ball.bounce(paddle.getCollider());
        }

        //handle walls

        // bottom wall
        if(ball.getCollider().bottom >= mScreenY) {
            ball.reset(mScreenX, mScreenY);

            gameController.lives--;
            Log.d("Lives:", "" + gameController.lives);
            if( gameController.lives == 0){
                //gameController.lives--;
                //out of lives, start the new game
                startNewGame();
                gameController.pauseState = true;
                //TODO: Game over screen needs to be displayed after this commment
            }
            else{
                //user just lost a life and the game isnt over in this part of the statement
                // pause the game so player can get their bearings
                gameController.pauseState = true;
            }


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
        if(ball.getCollider().right > mScreenX - 1) {
            ball.getCollider().right = mScreenX - 1;
            ball.reverseXVelocity();
        }
    }

    private boolean wonGame() {
        for(int i = 0; i < blocks.size(); i++)
        {
            if(blocks.get(i).getDeadStatus() == false)
                return false;
        }
        return true;
    }
}
