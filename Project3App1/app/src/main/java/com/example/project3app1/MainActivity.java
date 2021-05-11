package com.example.project3app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Broadcast to be caught by both app 1 and 2
    private final static String VIEW_WEBSITE = "edu.uic.cs478.s19.showTVWebsite";

    //This is the intent filter declared in app 2's manifest file
    private final static String START_2_ACTION = "edu.uic.cs478.s19.start2";

    private final static String KABOOM_PERMISSION = "edu.uic.cs478.s19.kaboom";
    private final static int KABOOM_REQUEST_CODE = 0;

    private Receiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set listener on the button
        Button startReceiverButton = findViewById(R.id.startReceiverButton);
        startReceiverButton.setOnClickListener(v -> checkPermAndStartReceiver());
    }

    //Permission handler
    public void checkPermAndStartReceiver() {
        // Check self for permission
        if (checkSelfPermission(KABOOM_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            if (receiver == null) {
                // Permission granted, start the receiver
                receiver = new Receiver();
                registerReceiver(receiver, new IntentFilter(VIEW_WEBSITE));

                // Update the status TextView
                TextView receiverStatusText = findViewById(R.id.receiverStatus);
                receiverStatusText.setText("Receiver for application 1 is started.");

                // Show toast
                Toast.makeText(this, "Receiver for application 1 is started.", Toast.LENGTH_SHORT).show();
            }

            // Start app 2
            Intent intent = new Intent();
            intent.setAction(START_2_ACTION);
            startActivity(intent);
        } else {
            // Permission not yet granted, prompt user to provide permission defined from app 3
            requestPermissions(new String[]{KABOOM_PERMISSION}, KABOOM_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Check whether permission has been granted
        if (requestCode == KABOOM_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            checkPermAndStartReceiver();
        } else {
            Toast.makeText(this, "Permission not granted, terminating.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister receiver
        if (receiver != null)
            unregisterReceiver(receiver);
    }
}