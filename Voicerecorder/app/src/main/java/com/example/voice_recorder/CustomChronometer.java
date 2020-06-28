package com.example.voice_recorder;

import android.app.Activity;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

public class CustomChronometer {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private long timerMax = (60 * 60 * 1000);

    public CustomChronometer(Activity activity, int resId) {
        chronometer = (Chronometer)activity.findViewById(resId);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= timerMax) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                }
            }
        });
    }

    public void startChronometer(View v) {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer(View v) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronometer(View v) {
        pauseChronometer(v);
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
}
