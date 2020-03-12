package com.example.bolaksepak;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class autoService extends BroadcastReceiver {

    public void onReceive(Context context, Intent arg1)
    {
        Intent intent = new Intent(context,StepCountService.class);
        context.startService(intent);
    }
}
