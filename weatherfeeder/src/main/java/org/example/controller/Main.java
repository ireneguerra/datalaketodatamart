package org.example.controller;

import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        String apiKey = args[0];
        Timer timer = new Timer();
        FeederTimerTask task = new FeederTimerTask(apiKey);
        timer.scheduleAtFixedRate(task, 0, 3600000);
    }
}
