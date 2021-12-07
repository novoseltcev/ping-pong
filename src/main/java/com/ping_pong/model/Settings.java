package com.ping_pong.model;


public abstract class Settings {
    public static final int FPS = 60;

    public static int milPerFrame;
    public static int nanosPerFrame;

    static {
        int delay = 1000 * 1000 / FPS;
        milPerFrame = delay / 1000;
        nanosPerFrame = delay % 1000;
        System.out.println("mils: " + milPerFrame);
        System.out.println("nanos: " + nanosPerFrame);
    }

    public static class Stage {
        public static int width  = 500;
        public static int height = 600;
    }

    public static class Entity {
        public static double height = 20;
        public static double width  = 100;

        public static double    speed  = 10.;

        public static double startX = 200;
        public static double startY = 0;
    }

    public static class Ball {
        public static double radius = 20;

        public static double    speed  = 8;

        public static double startX = 200;
        public static double startY = 200;
    }
}
