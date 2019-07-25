package com.example.breakout;

public class BOUser {
    public final String nickname;
    private int score;

    public BOUser(String name, int score) {
        nickname = name;
        this.score = score;
    }

    public boolean shouldChangeScore(int newScore) {
        if (newScore > this.score) {
            this.score = newScore;
            return true;
        }
        return false;
    }
}
