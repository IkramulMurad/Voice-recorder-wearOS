package com.example.voice_recorder;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends WearableActivity {
    private CustomChronometer recordingTimer;
    private ImageButton cancelRecording;
    private ImageButton startRecording;
    private ImageButton stopRecording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enables Always-on
        setAmbientEnabled();

        initView();
    }

    private void initView() {
        recordingTimer = new CustomChronometer(this, R.id.recording_timer);

        startRecording = findViewById(R.id.start_recording);
        stopRecording = findViewById(R.id.stop_recording);
        cancelRecording = findViewById(R.id.cancel_recording);

        stopRecording.setVisibility(View.GONE);
        cancelRecording.setVisibility(View.GONE);
    }
}