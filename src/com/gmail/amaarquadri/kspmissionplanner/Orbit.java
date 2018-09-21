package com.gmail.amaarquadri.kspmissionplanner;

/**
 * Created by Amaar on 2017-03-23.
 */
public class Orbit {
    //static methods

    public static double getSemiMajorAxis(Body body, double period) {
        double thing = period / (2 * Math.PI);
        return Math.cbrt(body.MU * thing * thing);
    }

    public static double getSemiMajorAxis(double period) {
        return getSemiMajorAxis(Body.EARTH, period);
    }

    public static double getPeriod(Body body, double semiMajorAxis) {
        return 2 * Math.PI * Math.sqrt(semiMajorAxis * semiMajorAxis * semiMajorAxis / body.MU);
    }

    public static double getPeriod(double semiMajorAxis) {
        return getPeriod(Body.EARTH, semiMajorAxis);
    }

    public static double getApsis(Body body, double period, double otherApsis) {
        return 2 * getSemiMajorAxis(body, period) - otherApsis;
    }

    public static double getApsis(double period, double otherApsis) {
        return getApsis(Body.EARTH, period, otherApsis);
    }

    //instance variables

    private Body parentBody;
    private double periapsis;
    private double apoapsis;
    private double inclination;
    private double totalDeltaV;

    //constructors

    public Orbit(Body parentBody, double periapsis, double apoapsis, double inclination) {
        this.parentBody = parentBody;
        this.periapsis = periapsis;
        this.apoapsis = apoapsis;
        this.inclination = inclination;
        totalDeltaV = 0;
    }

    public Orbit(Body parentBody, double periapsis, double apoapsis) {
        this(parentBody, periapsis, apoapsis, 0);
    }

    public Orbit(Body parentBody, double radius) {
        this(parentBody, radius, radius);
    }

    public Orbit(Body parentBody) {
        this(parentBody, parentBody.RADIUS + parentBody.LOW_ORBIT_ALTITUDE);
    }

    public Orbit(double periapsis, double apoapsis, double inclination) {
        this(Body.EARTH, periapsis, apoapsis, inclination);
    }

    public Orbit(double periapsis, double apoapsis) {
        this(periapsis, apoapsis, 0);
    }

    public Orbit(double radius) {
        this(radius, radius);
    }

    public Orbit() {
        this(Body.EARTH.RADIUS + Body.EARTH.LOW_ORBIT_ALTITUDE);
    }

    public Orbit(Orbit src) {
        this(src.parentBody, src.periapsis, src.apoapsis, src.inclination);
    }

    //getters

    public Body getParentBody() {
        return parentBody;
    }

    public double getPeriapsis() {
        return periapsis;
    }

    public double getApoapsis() {
        return apoapsis;
    }

    public double getInclination() {
        return inclination;
    }

    public double getTotalDeltaV() {
        return totalDeltaV;
    }

    //special getter methods

    public double getPeriapsisVelocity() {
        return Math.sqrt(parentBody.MU * (2/periapsis - 2/(periapsis + apoapsis)));
    }

    public double getApoapsisVelocity() {
        return Math.sqrt(parentBody.MU * (2/apoapsis - 2/(periapsis + apoapsis)));
    }

    public double getSemiMajorAxis() {
        return (periapsis + apoapsis) / 2;
    }

    public double getPeriod() {
        double semiMajorAxis = getSemiMajorAxis();
        return 2 * Math.PI * Math.sqrt(semiMajorAxis * semiMajorAxis * semiMajorAxis / parentBody.MU);
    }

    public double getEccentricity() {
        return (apoapsis - periapsis) / (apoapsis + periapsis);
    }

    //special methods that alter the Orbit's state

    public double addLaunchDeltaV() {
        totalDeltaV += parentBody.LAUNCH_DELTA_V;
        return parentBody.LAUNCH_DELTA_V;
    }

    public double hohmanTransfer(boolean burnAtPeriapsis, double targetApsis) {
        double initialVelocity, finalVelocity;
        if (burnAtPeriapsis) {
            initialVelocity = getPeriapsisVelocity();
            apoapsis = targetApsis;
            finalVelocity = getPeriapsisVelocity();
        }
        else {
            initialVelocity = getApoapsisVelocity();
            periapsis = targetApsis;
            finalVelocity = getApoapsisVelocity();
        }
        double deltaV = Math.abs(finalVelocity - initialVelocity);
        totalDeltaV += deltaV;
        return deltaV;
    }

    public double circularize(boolean burnAtPeriapsis) {
        return hohmanTransfer(burnAtPeriapsis, burnAtPeriapsis ? periapsis : apoapsis);
    }

    public double circularize(double targetRadius) {
        double deltaV = 0;
        if (targetRadius > periapsis) {
            deltaV += hohmanTransfer(true, targetRadius);
            deltaV += hohmanTransfer(false, targetRadius);
        }
        else {
            deltaV += hohmanTransfer(false, targetRadius);
            deltaV += hohmanTransfer(true, targetRadius);
        }
        return deltaV;
    }

    /**
     * It is assumed that the burn occurs at apoapsis, and that either the ascending or descending node is there.
     */
    public double planeChange(double targetInclination) {
        double deltaV = getApoapsisVelocity() *
                Math.sqrt(2 - 2 * Math.cos(Math.toRadians(Math.abs(targetInclination - inclination))));
        inclination = targetInclination;
        totalDeltaV += deltaV;
        return deltaV;
    }

    /**
     * It is assumed that the burn occurs at apoapsis, and that either the ascending or descending node is there.
     */
    public double circularizeAndPlaneChange(double targetInclination) {
        double initialVelocity = getApoapsisVelocity();
        periapsis = apoapsis;
        double deltaV = Math.hypot(getApoapsisVelocity() - initialVelocity, initialVelocity *
                Math.sqrt(2 - 2 * Math.cos(Math.toRadians(Math.abs(targetInclination - inclination)))));
        totalDeltaV += deltaV;
        return deltaV;
    }
}
