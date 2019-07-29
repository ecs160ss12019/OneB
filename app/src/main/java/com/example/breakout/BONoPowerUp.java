package com.example.breakout;

public class BONoPowerUp extends BOPowerUp {

    @Override
    public void apply(BOGameController gc) {
        // does nothing.
    }
    public String type(){
        return "No PowerUp";
    }

    public BOObject getMember() {
        return null;
    }
}

