package com.example.kovac94.meteorshower.game;

/**
 * Created by kovacmarko168 on 10/12/2016.
 */

public class Player {

    protected Rocket rocket;
    protected int score;
    protected int life;
    
    public Player(Rocket rocket) {
        
        this.rocket = rocket;
        this.score = 0;
        this.life = 3;

    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

