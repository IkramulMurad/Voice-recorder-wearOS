package com.example.voice_recorder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class MainActivity extends WearableActivity {
    private String LOG_TAG = "Voice-recorder";

    private static MainActivity mainActivity;

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

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

        mainActivity = this;
        initView();
        setOnClickListeners();
        checkRecorderPermission();
    }

    private void checkRecorderPermission() {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }

        if (!permissionToRecordAccepted) {
            finish();
        }
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

    public static void resetActivity(View view) {
        mainActivity.recordingState = RecordingState.STATE_NONE;
        mainActivity.recordingTimer.resetChronometer(view);

        mainActivity.stopRecording.setVisibility(View.GONE);
        mainActivity.cancelRecording.setVisibility(View.GONE);
        mainActivity.startRecording.setImageResource(R.drawable.start_recording);
    }

    private void setOnClickListeners() {
        startRecording.setOnClickListener(onStartRecordingClick);
        stopRecording.setOnClickListener(onRecordingStopClick);
        cancelRecording.setOnClickListener(onRecordingCancelClick);
    }

    private View.OnClickListener onStartRecordingClick = new View.OnClickListener() {
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
    };

    private View.OnClickListener onRecordingStopClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            recordingTimer.resetChronometer(view);
            launchRecordingDetailActivity();
        }
    };

    private View.OnClickListener onRecordingCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecordingCancelDialog recordingCancelDialog = new RecordingCancelDialog(MainActivity.this, view);
            recordingCancelDialog.showDialog();
        }
    };

    private void launchRecordingDetailActivity() {
        Log.d(LOG_TAG, "launching recording detail activity");
    }
}