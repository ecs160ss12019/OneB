package com.example.breakout;

import java.util.Timer;
import java.util.TimerTask;

public class BOTimer {
    Timer t;
    public boolean completed;

    BOTimer() {
        t = new Timer();
        completed = true;
    }

    public void run(long time) {
        /*
            Will start a timer that runs for -time- seconds. On completion, it will set
            completed (bool) to true
         */
        completed = false;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                completed = true;
            }
        };

        t.schedule(task, time);
    }
}
