package com.gmail.amaarquadri.kspmissionplanner;

import java.util.Map;
import java.util.TreeMap;

import static com.gmail.amaarquadri.kspmissionplanner.Config.body;
import static com.gmail.amaarquadri.kspmissionplanner.TimeUtils.getReadableTime;

import static com.gmail.amaarquadri.kspmissionplanner.Body.*;

/**
 * Created by Amaar on 2017-03-23.
 */
public class PhaseUtils {
    /**
     * Calculates the phase angle when a spacecraft should perform a holman transfer
     * in order to reach a desired phase angle with a target body, in the target body's orbit.
     *
     * @param radius The radius of the starting orbit.
     * @param targetRadius The radius of the target body's orbit.
     * @param targetPhaseAngle The desired phase angle of the target body once the final orbit is reached.
     * @return The phase angle when the spacecraft should perform a holman transfer to the target orbit.
     */
    public static double getLaunchPhaseAngle(double radius, double targetRadius, double targetPhaseAngle) {
        //phaseAngle is defined by: activeCraftAngle + phaseAngle = targetCraftAngle
        //ex. if targetCraft is "in front of" activeCraft by 10 degrees then phaseAngle = 10
        double transitTime = Orbit.getPeriod(body, (radius + targetRadius)/2) / 2;
        double targetPeriod = Orbit.getPeriod(body, targetRadius);
        double phaseAngleChange = 360 * transitTime / targetPeriod - 180;
        return (targetPhaseAngle - phaseAngleChange) % 360;
    }

    /**
     * Calculates the phase angle when a spacecraft should perform a holman transfer to rendezvous with a target body.
     *
     * @param radius The radius of the starting orbit.
     * @param targetRadius The radius of the target body's orbit.
     * @return The phase angle when the spacecraft should perform a holman transfer to the target orbit.
     */
    public static double getLaunchPhaseAngle(double radius, double targetRadius) {
        return getLaunchPhaseAngle(radius, targetRadius, 0);
    }

    /**
     * Calculates the number of seconds until the phase angle between two bodies reaches a desired value.
     *
     * @param radius The first body's orbital radius.
     * @param targetRadius The second body's orbital radius.
     * @param currentPhaseAngle The current phase angle between the 2 bodies.
     * @param targetPhaseAngle The desired phase angle between the two bodies.
     * @return The number of seconds until the desired phase angle is reached.
     */
    public static double getTimeUntilPhaseAngle(double radius, double targetRadius,
                                                double currentPhaseAngle, double targetPhaseAngle) {
        double changeInPhaseAnglePerSecond = 360 / Orbit.getPeriod(body, targetRadius) - 360 / Orbit.getPeriod(body, radius);
        //currentPhaseAngle + t * changeInPhaseAnglePerSecond = targetPhaseAngle
        return (targetPhaseAngle - currentPhaseAngle) / changeInPhaseAnglePerSecond;
    }

    /**
     *
     * @param radius
     * @param phaseChange The amount to shift the phase angle forwards by.
     * @return A TreeMap<Double, Double> containing the delta v required from the maneuver along with the total time taken
     */
    public static TreeMap<Double, Double> getPhaseChangeOptions(double radius, double phaseChange) {
        TreeMap<Double, Double> options = new TreeMap<>();
        double phaseDelay = 360. - phaseChange;
        double period = Orbit.getPeriod(body, radius);
        int i = 1;
        int j = 1;
        while (i * period + phaseDelay < 10 * period){
            double targetPeriod = (i * period + phaseDelay) / j;
            while (targetPeriod > period){
                double deltaV = 2 * new Orbit(body, radius).hohmanTransfer(true, Orbit.getApsis(body, targetPeriod, radius));
                options.put(deltaV, targetPeriod);
                if (deltaV < 41) {
                    System.out.println(i + ", " + j);
                    System.out.println(Orbit.getApsis(body, targetPeriod, radius) - Body.EARTH.RADIUS);
                }
                j++;
                targetPeriod = (i * period + phaseDelay) / j;
            }
            i++;
            j = 1;
        }
        return options;
    }

    public static void printPhaseChangeOptions(double radius, double phaseChange) {
        for (Map.Entry<Double, Double> entry :
                getPhaseChangeOptions(radius, phaseChange).entrySet())
            System.out.println("Delta-V: " + entry.getKey() + ", Wait Time: " + getReadableTime(entry.getValue()));
    }

    public static void main(String[] args) {
        double transferSemiMajorAxis = (EARTH.ORBIT.getSemiMajorAxis() + MARS.ORBIT.getSemiMajorAxis()) / 2;

        double transferTime = Orbit.getPeriod(SUN, transferSemiMajorAxis) / 2;
        System.out.println("Transfer Time: " + TimeUtils.getReadableTime(transferTime));

        double marsTranferRotation = 360 * transferTime / MARS.ORBIT.getPeriod();
        System.out.println("Mars Transfer Rotation: " + marsTranferRotation);
        System.out.println(180-marsTranferRotation);

        System.out.println("LMO: " + MARS.LOW_ORBIT_ALTITUDE);
    }
}
