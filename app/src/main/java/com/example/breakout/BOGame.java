package com.example.breakout;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Collections;
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
        - Keeping track of lives (although alternatively the gameController can do that)

        Just to give you some kind of idea on what should go here.
    */

    // declaring something 'final
                                             // means it can be read, but not modified

    public BOGameController gc; // stores a reference to our gameController
                                            // it's important this is accessible
                                            // by every class!!
    // Drawing Objects
    private SurfaceHolder holder; // So I didn't add 'm' here because
                                    // the name of the variable
                                  // is not the same as the class. take note
    public Canvas mCanvas;
    public Paint mPaint;

    // Thread and Thread Handling
    private Thread gameThread = null;

    // check if paused
    public boolean isPaused = true;

    // GameObject builder
    GameObjectBuilder factory;


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

        gc = controller;
        factory = new GameObjectBuilder();



        // Initialize the drawing objects
        holder = getHolder();
        mPaint = new Paint();


        // Initialize our game objects
        Point dim = gc.getMeta().getDim();

        // building the paddle
        factory.setPos(dim);
        factory.setResources(gc.resources);
        gc.paddle = factory.buildPaddle();

        // building the ball
        factory.setSprite(BitmapFactory.decodeResource(getResources(), R.drawable.ball));
        gc.ball = factory.buildBall();

        //initialize blocks ArrayList for later use
        gc.blocks = new ArrayList<>();

        // Initialize the layout  TODO: Move this later

        factory.setSprite(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        factory.setCollider(new RectF(0,0,dim.x,dim.y));
        gc.myLayout = factory.buildLayout();

        factory.setSprite(BitmapFactory.decodeResource(getResources(), R.drawable.game_over));
        factory.setCollider(new RectF((dim.x/2 - dim.x/4),(dim.y/2 - dim.y / 4), (dim.x/2 + dim.x/4) , (dim.y/2 + dim.y / 4))); // this should place it ~ center of screen.);
        gc.gameOver = factory.buildLayout();


        /* Our factory probably shouldn't build the BOMenu even though it inherits from gameobject..Think more about this later!! */
        gc.menu = new BOMenu((int)dim.x, (int)dim.y, gc, mCanvas, mPaint);
        gc.menu.sprite = BitmapFactory.decodeResource(getResources(), R.drawable.menu);

        gc.pauseButton = new BOPauseButton((int)dim.x , (int)dim.y);
        gc.pauseButton.sprite = BitmapFactory.decodeResource(getResources(), R.drawable.pause);


        // Start the game!
        startNewGame();
        Log.d("DEBUG: ", "BOGAME");

        // set up the fonts
        Typeface tf = ResourcesCompat.getFont(context, R.font.arcade);
        mPaint.setTypeface(tf);

        factory.clear(); // reset our factory so other methods don't accidently call something with wrong hold-over dimensions. Instead it'll just crash :^)

    }

    @Override
    public void run() {
        while (!isPaused) {
            // What time is it now at the start of the loop?
            long frameStartTime = System.currentTimeMillis();
            // Provided the game isn't paused
            // call the update method
            gc.context.run();

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
                gc.getMeta().updateFPS(timeThisFrame);
            }
            if(gc.lives == 0) { // listen for lives == 0
                startNewGame();
            }
        }

    }

    // handle touch controls
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        return gc.context.onTouchEvent(motionEvent);
    }

    public void startNewGame() {
        // Reset our game objects
        gc.blocks.clear();
        gc.score = 0;
        gc.lives = 3;
        gc.powerups = 0;

        //only reset the score if we started back on the first level
//        if(gc.currentLevel != 1) {
//            // Reset the score
//            gc.score = 0;
//        }

        // Set the state to gameRunning
        gc.ball.reset(gc.paddle);
        initializeBlocks(); // create the block objects
        randomlyAssignBlocks(); // give them random sprites
        //Collections.shuffle(gc.blocks); // shuffle the blocks!
    }


    private void draw() {
        /*
            This function takes nothing as a parameter and returns nothing.
            all it does is modify our mCanvas object and draws our playing
            area.
         */
        if(holder.getSurface().isValid()) {
            mCanvas = holder.lockCanvas();

            gc.context.draw(mCanvas, mPaint);

            // Actual Members
            // in case you cared / wondered
            boolean DEBUGGING = false;
            if(DEBUGGING) {
                printDebuggingText();
            }

            holder.unlockCanvasAndPost(mCanvas);

        }
    }

    private void printDebuggingText(){
        int debugSize = gc.getMeta().getFontSize()/ 2;
        int debugStart = 150;
        mPaint.setTextSize(debugSize);

      //  mCanvas.drawText("ballPos: " + ball.collider.top + " " + ball.collider.left + " " + ball.getCollider().bottom + " " + ball.getCollider().right ,
        //        15, debugStart + debugSize * 14, mPaint);
        mCanvas.drawText("speed: " + gc.ball.getSpeed(),
                15, debugStart + debugSize * 14, mPaint);

        mCanvas.drawText("Completed: " + gc.timer.completed,
                16, debugStart + debugSize * 15, mPaint);
        mCanvas.drawText("Won: " + gc.won,
                17, debugStart + debugSize * 16, mPaint);
    }

    public void pause() {
        // set the playing state to false
        // and attempt to stop the thread

        // Make sure the game is running before pausing. Maybe
        // it doesn't make sense to pause during a game_over screen?

        if(!isPaused) {

            isPaused = true; // leave the running state

            try {
               gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error: ", "joining thread");
            }
        }

    }

    public void resume() {
        if(isPaused) {
            isPaused = false;

            // Initialize instance of the thread
            gameThread = new Thread(this);

            gameThread.start();

        }
    }

    public void randomlyAssignBlocks() {
        // Creating RNG for purpose of randomizing block color
        Random blockGen = new Random();

        // Initializing the sprites of the blocks
        for(int i = 0; i < gc.blocks.size(); i++) {

            if (gc.blocks.get(i).hasPowerUp) {
                continue;
            }
            int choice = blockGen.nextInt();
            choice = Math.abs(choice); // make sure choice is always positive

            int mod = choice % 4;

            if (mod == 0)
                gc.blocks.get(i).sprite = BitmapFactory.decodeResource(getResources(), R.drawable.choco_brown);
            else if (mod == 1)
                gc.blocks.get(i).sprite = BitmapFactory.decodeResource(getResources(), R.drawable.strawberry_choco);
            else
                gc.blocks.get(i).sprite = BitmapFactory.decodeResource(getResources(), R.drawable.matcha_choco);
        }
    }

    private void initializeBlocks() { // TODO: make this smaller
        /*
        This function seeks to make 4 rows of blocks in a relatively symmetric manner
        by first creating a block to the left, then creating a block to the right.
        It will return nothing and take nothing as a parameter, but it modifies the blocks member.
         */

        gc.blocks.clear(); // reset our array-list;

        Point dim = gc.getMeta().getDim();

        // distance between blocks
        float xMargin = (float)(dim.x * .05); //TODO: These are magic numbers. See if we can find better ones
        float yMargin = (float)(dim.y * .05);

        // padding for screen
        float padding = (float)(dim.x * .1); // magic number.

        float curX = padding;
        float curY = 0;
        int num_blocks = 0;

        int numRows = 4; //controls the number of rows of blocks we want


        while (numRows > 0) {
            boolean canAddMore = true;
            float height = 0; // awkward scoping issue. We want to access the height of the block, but we dont declare a block object until the for loop.
            // fortunately the block height is always constant so we can just repeatly update the height value.
            // its low-key dumb and repetition of code, but hey we do what we gotta do.

            while (canAddMore) {
                BOBlock temp = new BOBlock(dim, curX + xMargin, curY + yMargin, gc);
                gc.blocks.add(temp);
                num_blocks += 1;
                height = temp.getHeight();
                curX += (temp.getLength() + xMargin);

                if (curX + xMargin + temp.getLength() + padding > dim.x) {
                    canAddMore = false;
                }
            }
            curY += (height + yMargin);
            curX = padding;
            numRows--;
        }

        Log.d("Amount of Power Ups: ", "" + gc.powerups);
        Log.d("Level Number: ", "" + gc.level);
        while (gc.powerups < gc.level){
            Random chance = new Random();
            int pickedBlock = chance.nextInt(num_blocks);

            while (gc.blocks.get(pickedBlock).hasPowerUp){
                pickedBlock = chance.nextInt(num_blocks);
                Log.d("Duplicate. New Block: ", "" + pickedBlock);
            } // checking for duplicates

            Log.d("Selected Block: ", "" + pickedBlock);
            gc.blocks.get(pickedBlock).hasPowerUp = true;
            gc.powerups++;
            gc.blocks.get(pickedBlock).sprite = BitmapFactory.decodeResource(gc.resources.getResources(), R.drawable.vanilla_caramel_choco);
        }
    }
}
