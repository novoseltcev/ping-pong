package com.ping_pong.utils;

public class Utils {
    public static double map(double value, double currentRangeStart, double currentRangeStop, double targetRangeStart, double targetRangeStop) {
        return targetRangeStart
                + (targetRangeStop - targetRangeStart)
                * ((value -currentRangeStart) / (currentRangeStop - currentRangeStart));
    }
}
