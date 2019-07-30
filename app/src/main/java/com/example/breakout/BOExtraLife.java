package com.example.breakout;

public class BOExtraLife extends BOPowerUp {

    public BOGameController gc;

    public BOExtraLife (BOGameController gc){
        this.gc = gc;
        gc.lives++;
    }

    @Override
    public void apply(BOGameController gc) {
        return;
    }
    public String type(){
        return "Extra Life";
    }

    public BOObject getMember() {
        return null;
    }

    @Override
    public void time() {
        // don't do anything
    }
}

