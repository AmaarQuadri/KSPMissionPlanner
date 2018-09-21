package com.gmail.amaarquadri.kspmissionplanner;

import static com.gmail.amaarquadri.kspmissionplanner.Config.body;

/**
 * Created by Amaar on 2017-04-09.
 */
public class LaunchUtils {
    public static double getRotationalVelocity(double latitude) {
        return Math.cos(latitude) * 2 * Math.PI * body.RADIUS / body.SIDEREAL_DAY_LENGTH;
    }

    public static double getLaunchAzimuth(double latitude, double targetInclination, double targetAltitude) {
        double inertialLaunchAzimuth = Math.asin(
                Math.cos(Math.toRadians(targetInclination)) / Math.cos(Math.toRadians(latitude)));
        double orbitalVelocity = new Orbit(body, targetAltitude).getPeriapsisVelocity();
        return Math.toDegrees(Math.atan(
                (orbitalVelocity * Math.sin(inertialLaunchAzimuth) - getRotationalVelocity(latitude)) /
                (orbitalVelocity * Math.cos(inertialLaunchAzimuth))));
    }
}
