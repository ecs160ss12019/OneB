package com.example.breakout;

public class BORecord implements Comparable<BORecord> {
    public int rank;
    public String name;
    public int highScore;

    public BORecord(int rank, String name, int score) {
        this.rank = rank;
        this.name = name;
        highScore = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public int compareTo(BORecord r) {
        if (getHighScore() == 0 || r.getHighScore() == 0) {
            return 0;
        }
        return Double.compare(getHighScore(), r.getHighScore());
    }
}
