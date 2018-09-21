package com.gmail.amaarquadri.kspmissionplanner;

/**
 * Created by Amaar on 2017-03-23.
 */
public class TimeUtils {
    /**
     * Calculates the amount of time that the spacecraft will be in the parent body's shadow for each orbit.
     *
     * @param radius The radius of the circular orbit.
     * @return The number of seconds that are spent in the shadow per orbit.
     */
    public static double getTimeInShadow(Body body, double radius) {
        return Orbit.getPeriod(body, radius) * Math.asin(body.RADIUS / radius) / Math.PI;
    }

    /**
     * Converts the given number of seconds into a human readable format.
     *
     * @param seconds The number of seconds.
     * @return The number of seconds in a human readable format
     */
    public static String getReadableTime(double seconds) {
        String displayTime = "";

        int days = (int) (seconds / 24 / 3600);
        if (days != 0) {
            displayTime += days + "d ";
            seconds -= 24 * 3600 * days;
        }

        int hours = (int) (seconds / 3600);
        if (days != 0 || hours != 0) {
            displayTime += hours + "h ";
            seconds -= 3600 * hours;
        }

        int minutes = (int) (seconds / 60);
        if (days != 0 || hours != 0 || minutes != 0) {
            displayTime += minutes + "m ";
            seconds -= 60 * minutes;
        }

        displayTime += seconds + "s";
        return displayTime;
    }

    /**
     * Converts a human readable measure of time into the number of seconds.
     *
     * @param days The number of days.
     * @param hours The number of hours.
     * @param minutes The number of minutes.
     * @param seconds The number of seconds.
     * @return The number of seconds of the time entered.
     */
    public static double getTimeInSeconds(int days, int hours, int minutes, double seconds) {
        return 86400 * days + getTimeInSeconds(hours, minutes, seconds);
    }

    /**
     * Converts a human readable measure of time into the number of seconds.
     *
     * @param hours The number of hours.
     * @param minutes The number of minutes.
     * @param seconds The number of seconds.
     * @return The number of seconds of the time entered.
     */
    public static double getTimeInSeconds(int hours, int minutes, double seconds) {
        return 3600 * hours + getTimeInSeconds(minutes, seconds);
    }

    /**
     * Converts a human readable measure of time into the number of seconds.
     *
     * @param minutes The number of minutes.
     * @param seconds The number of seconds.
     * @return The number of seconds of the time entered.
     */
    public static double getTimeInSeconds(int minutes, double seconds) {
        return 60 * minutes + seconds;
    }
}
