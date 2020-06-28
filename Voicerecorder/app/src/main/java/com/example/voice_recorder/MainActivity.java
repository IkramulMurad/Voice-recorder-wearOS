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

    enum RecordingState {
        STATE_NONE,
        STATE_RECORDING,
        STATE_PAUSE
    }
    private RecordingState recordingState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enables Always-on
        setAmbientEnabled();

        initView();
        setOnClickListeners();
    }

    private void initView() {
        recordingState = RecordingState.STATE_NONE;
        recordingTimer = new CustomChronometer(this, R.id.recording_timer);

        startRecording = findViewById(R.id.start_recording);
        stopRecording = findViewById(R.id.stop_recording);
        cancelRecording = findViewById(R.id.cancel_recording);

        stopRecording.setVisibility(View.GONE);
        cancelRecording.setVisibility(View.GONE);
    }

    private void setOnClickListeners() {
        startRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recordingState == RecordingState.STATE_NONE) {
                    recordingState = RecordingState.STATE_RECORDING;
                    recordingTimer.startChronometer(view);

                    startRecording.setImageResource(R.drawable.pause_recording);
                    stopRecording.setVisibility(View.VISIBLE);
                    cancelRecording.setVisibility(View.VISIBLE);
                } else if (recordingState == RecordingState.STATE_RECORDING) {
                    recordingState = RecordingState.STATE_PAUSE;
                    recordingTimer.pauseChronometer(view);

                    startRecording.setImageResource(R.drawable.resume_recording);
                } else if (recordingState == RecordingState.STATE_PAUSE) {
                    recordingState = RecordingState.STATE_RECORDING;
                    recordingTimer.startChronometer(view);

                    startRecording.setImageResource(R.drawable.pause_recording);
                }
            }
        });

        stopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cancelRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}