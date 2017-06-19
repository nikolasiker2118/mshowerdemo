package com.example.kovac94.meteorshower.game;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * Created by kovacmarko168 on 10/17/2016.
 */

public class GameObject {

	protected double x;
    protected double y;
    protected double radius;
    protected double speed;
    protected boolean moving;
    protected double destinationX;
    protected double destinationY;

    public GameObject(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(double destinationX) {
        this.destinationX = destinationX;
    }

    public double getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(double destinationY) {
        this.destinationY = destinationY;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void move() {

        double disX=getDestinationX()-getX();
        double disY=getDestinationY()-getY();

        if (sqrt(disY*disY+disX*disX)<getSpeed()){
            setX(getDestinationX()); 
            setY(getDestinationY());
        }
        else {
            double radian = atan2(disY,disX);
            setX(getX()+cos(radian)*getSpeed());
            setY(getY()+sin(radian)*getSpeed());
        }
    }
}
