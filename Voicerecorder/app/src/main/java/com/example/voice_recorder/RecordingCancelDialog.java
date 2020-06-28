package com.example.voice_recorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

public class RecordingCancelDialog {
    private String LOG_TAG = "Voice-recorder";

    AlertDialog.Builder cancelRecordingDialog;

    public RecordingCancelDialog(final Activity activity, final View view) {
        cancelRecordingDialog = new AlertDialog.Builder(activity);

        cancelRecordingDialog.setTitle("Alert");
        cancelRecordingDialog.setMessage("Do you want to cancel recording?");
        cancelRecordingDialog.setCancelable(false);

        cancelRecordingDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(LOG_TAG, "resetting activity");
                MainActivity.resetActivity(view);
            }
        });

        cancelRecordingDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    public void showDialog() {
        AlertDialog alertDialog = cancelRecordingDialog.create();
        alertDialog.show();
    }
}
