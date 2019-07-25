package com.example.breakout;

public class BOUser {
    private String nickname;
    private int score;

    public BOUser(String name, int score) {
        nickname = name;
        this.score = score;
    }

    public boolean shouldChangeScore(int score) {
        if (score > this.score) {
            this.score = score;
            return true;
        }
        return false;
    }
}
