package com.gmail.amaarquadri.kspmissionplanner;

/**
 * Created by Amaar on 2017-03-23.
 */
public enum LaunchSite {
    CAPE_CANAVERAL(28.608389), VANDENBERG(34.5813), BAIKONUR(45.920278), PLESETSK(62.957222), FRENCH_GUIANA(5.239380),
    XICHANG(28.24646), JIUQUAN(41.11803);

    public final double LATITUDE;
    public final double SPEED;

    LaunchSite(double LATITUDE) {
        this.LATITUDE = LATITUDE;
        SPEED = Math.cos(Math.toRadians(LATITUDE)) *
                2 * Math.PI * Body.EARTH.RADIUS / Body.EARTH.SIDEREAL_DAY_LENGTH;
    }
}
