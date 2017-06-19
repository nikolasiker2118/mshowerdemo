package com.example.kovac94.meteorshower.game;

/**
 * Created by kovacmarko168 on 10/13/2016.
 */

public class Meteor extends GameObject {

    public Meteor(double x, double y,double destinationX,
        double destinationY,double radius) {
        super(x, y, radius);
        this.speed = 3;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }
}