package com.example.breakout;

import java.util.Random;

public abstract class BOPowerUp {

    public abstract void apply(BOGameController gc);
    public abstract String type();
    public abstract BOObject getMember();
    public abstract void time();

    public BOPowerUp randomPowerUp(BOGameController gc) {
        Random random = new Random();
        int choice = random.nextInt(4);

        if(choice == 0) {
            return(new BODoubleBall(gc));
        }
        else if(choice == 1) {
            return(new BOExtendPaddle(gc));
        }
        else if(choice == 2) {
            return(new BOExtraLife(gc));
        }
        else if(choice == 3){
            return(new BODestroyBlocks(gc));
        }

        return null; // crash

    }
}
