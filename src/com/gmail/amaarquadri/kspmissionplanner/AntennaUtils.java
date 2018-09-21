package com.gmail.amaarquadri.kspmissionplanner;

import static com.gmail.amaarquadri.kspmissionplanner.Config.Constants.*;

/**
 * Created by Amaar on 2017-06-23.
 */
public class AntennaUtils {
    public static final double DSN_ANTENNA_RANGE = 114 * TERA;

    public static double getRange(double antenna1Range, double antenna2Range) {
        return Math.min(antenna1Range, antenna2Range) + Math.sqrt(antenna1Range * antenna2Range);
    }

    public static double getRange(double antennaRange) {
        return getRange(antennaRange, DSN_ANTENNA_RANGE);
    }


    /**
     * The target antenna is weaker.
     *
     * @param range
     * @param weakerAntenna
     * @return
     */
    public static double getNeededStrengthStronger(double range, double weakerAntenna) {
        double thing = range - weakerAntenna;
        return thing * thing / weakerAntenna;
    }

    /**
     * Target antenna is stronger.
     *
     * @param range
     * @param strongerAntennaRange
     * @return
     */
    public static double getNeededStrengthWeaker(double range, double strongerAntennaRange) {
        double d = Math.sqrt(range + strongerAntennaRange/4) - Math.sqrt(strongerAntennaRange)/2;
        return d * d;
    }

    public static void main(String[] args) {
        System.out.println("Mars req: " + (Body.MARS.ORBIT.getApoapsis() + Body.EARTH.ORBIT.getApoapsis()));
        System.out.println("Range: " + getRange(DSN_ANTENNA_RANGE, 2 * GIGA));
    }
}
