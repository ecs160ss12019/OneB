package com.example.breakout;

public abstract class BOPowerUp {
    public abstract void apply(BOGameController gc);
    public abstract String type();
    public abstract BOObject getMember();
    public abstract void time();
}
