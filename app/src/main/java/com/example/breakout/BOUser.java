package com.example.breakout;

public class BOUser {
    public String nickname;
    public int score;
    BOGameController gc;

    public BOUser(String name, int score, BOGameController gc) {
        this.gc = gc;
        nickname = name;
        this.score = score;
    }

    public void changeScore(int newScore) {
        if (newScore > this.score) {
            this.score = newScore;
        }
    }
}
