package ru.busoptimizer.backend.service;

import static java.lang.Math.*;
import static java.lang.Math.cos;

public class Distance {
    public static double calcDistance(double NLat1, double ELong1, double NLat2, double ELong2) {
        double N1 = toRadians(NLat1);
        double E1 = toRadians(ELong1);
        double N2 = toRadians(NLat2);
        double E2 = toRadians(ELong2);

        double u = sin((N2 - N1) / 2.0);
        double v = sin((E2 - E1) / 2.0);
        return 6371.0 * 2.0 * asin(sqrt(u * u + cos(N1) * cos(N2) * v * v));
    }

}
