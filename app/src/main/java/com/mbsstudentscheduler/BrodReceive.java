package com.mbsstudentscheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BrodReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
    if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
        Intent serviceIntent = new Intent(context,serviceClass.class);
        context.startForegroundService(serviceIntent);
    }
    }
}
