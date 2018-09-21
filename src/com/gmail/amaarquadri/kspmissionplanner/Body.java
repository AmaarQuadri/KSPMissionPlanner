package com.gmail.amaarquadri.kspmissionplanner;

/**
 * Created by Amaar on 2017-03-20.
 */
public enum Body {
    SUN(1.32712E20, 696342000, Double.NaN, Double.NaN, Double.NaN, null),
    //MU from Wikipedia
    MERCURY(2.20329E13, 2439700, 5067031.68, Double.NaN, Double.NaN,
            new Orbit(SUN, 4.600180422425907E10, 6.981614306751697E10, 28.60252108855048)),
    VENUS(3.24859E14, 6049000, 20996797.016381, Double.NaN, 145000,
            new Orbit(SUN, 1.0747260500973967E11, 1.0894649257119455E11, 24.46397633556437)),
    EARTH(3.986004418E14, 6371000, 86164.098903691, 9400, 140000,
            new Orbit(SUN, 147190273443.47726, 152006248857.40778, 23.44603795469773)),
    //MU taken from wikipedia
    MOON(4.90486959E12, 1737000, 2360584.68479999, Double.NaN, Double.NaN,
            new Orbit(EARTH, 363831910.2274791, 404784965.3139341, 28.36267790798491)),
    MARS(4.282831E13, 3375800, 88642.6848, Double.NaN, 125000,
            new Orbit(SUN, 2.0669085956441473E11, 2.4920854035953784E11, 24.69272426910055)),
    JUPITER(1.26686534E17, 69373000, 35730, Double.POSITIVE_INFINITY, 69373000+5000,
            new Orbit(SUN, 740270432426.4369, 816107444893.07389, 23.25313306947884));

    //e=(a-p)/(a+p), sm = (a+p)/2
    //sm*(1-e)=p
    //sm(1+e)=a
    ///

    public final double MU; //meters ^3 seconds ^ -2
    public final double RADIUS; //meters
    public final double SIDEREAL_DAY_LENGTH; //seconds
    public final double LAUNCH_DELTA_V; //meters per second
    public final double LOW_ORBIT_ALTITUDE; //meters
    public final double LOW_ORBIT_RADIUS; //meters
    public final double SYNCHRONOUS_ORBIT_RADIUS; //meters
    public final double SYNCHRONOUS_ORBIT_ALTITUDE; //meters
    public final Orbit ORBIT;

    Body(double MU, double RADIUS, double SIDEREAL_DAY_LENGTH, double LAUNCH_DELTA_V,
         double LOW_ORBIT_ALTITUDE, Orbit ORBIT) {
        this.MU = MU;
        this.RADIUS = RADIUS;
        this.SIDEREAL_DAY_LENGTH = SIDEREAL_DAY_LENGTH;
        this.LAUNCH_DELTA_V = LAUNCH_DELTA_V;
        this.LOW_ORBIT_ALTITUDE = LOW_ORBIT_ALTITUDE;
        LOW_ORBIT_RADIUS = RADIUS + LOW_ORBIT_ALTITUDE;
        SYNCHRONOUS_ORBIT_RADIUS = Orbit.getSemiMajorAxis(this, SIDEREAL_DAY_LENGTH);
        SYNCHRONOUS_ORBIT_ALTITUDE = SYNCHRONOUS_ORBIT_RADIUS - RADIUS;
        this.ORBIT = ORBIT;
    }
}
