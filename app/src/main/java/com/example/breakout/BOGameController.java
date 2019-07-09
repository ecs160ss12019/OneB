package com.example.breakout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

public class BOGameController extends Activity {
    /*
    The game controller class controls the state machine of the game
    Ultimately this class should store anything that modifies or changes
    how the game is played (i.e NewGame() -> starts a new game, Pause() ->
    pauses game. For the most part, booleans that control modifiers or
    power-ups should also be stored here.
     */

    /*
        Here is what the basic state diagram of our game looks like
        _______________               _____________
        |             |    ALWAYS    |             |       ALWAYS
        |   Create()  |  ----------> |  newGame()  | ---------
        |_____________|              |_____________|          |
                                 ALWAYS______|                |
                          ____________|                       v
                         |            |                  ___________               _____________
                         | GameOver() |     lives = 0   |           |numBlocks =0 |             |
                         |____________| <-------------- |   play()  |---------->  |  nextLevel()|
                                                        |___________|             |_____________|

        The Arrow transitions means that from one state, it should proceed
        to another state under a certain condition.
        ALWAYS means that after the game reaches this state, it should
        always transition to the following one. Ignore the fact I'm missing
        some states.
     */


    /*
        The reasoning behind this state machine is that they are
        actually incredibly powerful for video games and allows for
        code to more easily interact with one another.

        Take for example we had in this class 1 bool

        public boolean turnOnDoubleBalls;

        Our new state diagram could look something like this

        _______________               _____________                                _____________
        |             |    ALWAYS    |             |   turnOnDoubleBalls = true   |             |
        |   Create()  |  ----------> |  newGame()  | ---------------------------> | numBalls = 2|
        |_____________|              |_____________|          |                   |_____________|
                                 ALWAYS______|     |          |
                          ____________|            |          v
                         |            |            --->  ___________               _____________
                         | GameOver() |     lives = 0   |           |numBlocks =0 |             |
                         |____________| <-------------- |   play()  |---------->  |  nextLevel()|
                                                        |___________|             |_____________|
        Now we essentially introduced a state to our machine that controls
        another aspect of the game! Think about how nice and organized
        this will be rather than just shoving bools throughout all of our code.
        Furthermore, the end goal of doing something like this is
        to help make more power-ups / modifiers as independent as possible, like
        say we had 2 bools turnOnDoubleBalls and invertControls. The end goal is to
        be able to turn on both these modifiers and have them apply to the game
        WITHOUT impacting one another. Although this sounds trivial, trust me
        as someones whose done this, features mess with each other all the time.
        Anyways this is my long ass spheel about state machines. When doing your
        coding I highly encourage you to think about the game in terms like this.
        It'll help organization / style alot (and looks a lot more professional!!)

     */


    // Actual member declarations
    private BOGame mBOGame; // Let's keep the book's style and if a variable
                            // shares it's name with a class, add an 'm' in front
                            // of it

    // STATES
    public boolean createState = true;
    public boolean newGameState = true;
    public volatile boolean gameRunningState = false; // Volatile implies the variable can be changed in threads
    public boolean pauseState = true;
    public boolean gameOverState = false;



    //TODO: Think about how many states we really need

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Code adapted from our java text.
        // Comment here just to please Gabby ;)

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mBOGame = new BOGame(this, this, size.x, size.y);
        setContentView(mBOGame);
        createState = false;
        Log.d("DEBUG: ", "CREATE");

    }

    @Override
    protected void onResume() {
        super.onResume();
        mBOGame.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DEBUG: ", "CREATE");
        mBOGame.pause();
    }
}
