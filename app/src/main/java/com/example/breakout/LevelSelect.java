package com.example.breakout;

import com.example.breakout.States.Level1State;
import com.example.breakout.States.Level2State;
import com.example.breakout.States.Level3State;
import com.example.breakout.States.Level4State;
import com.example.breakout.States.Level5State;
import com.example.breakout.States.Level6State;
import com.example.breakout.States.Level7State;
import com.example.breakout.States.Level8State;
import com.example.breakout.States.Level9State;

public class LevelSelect {
    /*
        We need a level select class because we do work in
        our constructor which is problematic. So we have this class as a
        factory class to constructor levels on when we need them.
     */

    BOGameController gc;

    LevelSelect(BOGameController gc){
        this.gc = gc;
    }

    public void selectLevel(int level) {
        switch(level) {
            case 1:
                gc.context = new Level1State(gc);
                break;
            case 2:
                gc.context = new Level2State(gc);
                break;
            case 3:
                gc.context = new Level3State(gc);
                break;
            case 4:
                gc.context = new Level4State(gc);
                break;
            case 5:
                gc.context = new Level5State(gc);
                break;
            case 6:
                gc.context = new Level6State(gc);
                break;
            case 7:
                gc.context = new Level7State(gc);
                break;
            case 8:
                gc.context = new Level8State(gc);
                break;
            case 9:
                gc.context = new Level9State(gc);
                break;


        }
    }
}
