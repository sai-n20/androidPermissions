package com.example.project3app1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Start WebActivity
        Intent websiteIntent = new Intent(context, WebActivity.class);
        websiteIntent.putExtra("tvURL", intent.getStringExtra("tvURL"));
        context.startActivity(websiteIntent);
    }
}
