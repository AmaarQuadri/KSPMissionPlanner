package com.gmail.amaarquadri.kspmissionplanner;

/**
 * Created by Amaar on 2017-03-23.
 */
public class Config {
    /**
     * The Body that the spacecraft is orbiting. All calculations will be based on its properties.
     * Set to Body.EARTH by default.
     */
    public static Body body = Body.EARTH;

    public static class Constants {
        private Constants() {
            throw new AssertionError();
        }

        public static final double KILO = 1000;
        public static final double MEGA = 1000000;
        public static final double GIGA = 1000000000;
        public static final double TERA = 1000000000000.;
    }
}
