package net.majorkernelpanic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import net.majorkernelpanic.spydroid.ui.SpydroidActivity;

public class AppReceiver extends BroadcastReceiver {
    //    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TestSpy", "onReceive");
        if (intent.getAction().equals(ACTION)) {
            Log.d("TestSpy", "BroadcastReceiver");
            Intent spIntent = new Intent(context, SpydroidActivity.class);
            spIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(spIntent);
        }
    }
}