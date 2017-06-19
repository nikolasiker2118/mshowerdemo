package com.example.kovac94.meteorshower.game;

/**
 * Created by kovacmarko168 on 10/17/2016.
 */

public class Rocket extends GameObject {

    public Rocket(double x, double y, double radius, double speed, boolean moving) {
        super(x, y, radius);

        this.setDestinationX(this.x);
        this.setDestinationY(this.y);

        this.speed = speed;
        this.moving = moving;

    }
}
