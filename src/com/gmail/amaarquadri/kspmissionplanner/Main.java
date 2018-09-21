package com.gmail.amaarquadri.kspmissionplanner;

/**
 * Created by Amaar on 2017-03-20.
 * T^2=a^3
 * (xT)^2=a^3
 * (x^2)(T^2)=a^3
 * (T^2)=(a^3)(x^-2)
 * (T^2)=(a(x^-2/3))^3
 */
public class Main {
    public static void main(String[] args) {
        double totalDv = 0;

        Orbit o = new Orbit();
        o.addLaunchDeltaV();
        o.hohmanTransfer(true, Double.POSITIVE_INFINITY);
        System.out.println(totalDv += o.getTotalDeltaV());

        Orbit o2 = new Orbit(Body.EARTH.ORBIT);
        o2.hohmanTransfer(true, Body.MARS.ORBIT.getApoapsis());
        System.out.println(totalDv += o2.getTotalDeltaV());

        Orbit o3 = new Orbit(Body.MARS, Body.MARS.LOW_ORBIT_RADIUS, Double.POSITIVE_INFINITY);
        o3.hohmanTransfer(true, Body.MARS.LOW_ORBIT_RADIUS);
        System.out.println(totalDv += o3.getTotalDeltaV());
    }
}
