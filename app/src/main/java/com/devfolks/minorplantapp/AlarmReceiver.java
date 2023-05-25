package com.devfolks.minorplantapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    int notificationId = 1;
    String channelId="channel1";
    String titleExtra= "titleExtra";
    String messageExtra="messageExtra";

    
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i= new Intent(context, ShowEventDetails.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent= PendingIntent.getActivity(context,0,i,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle(intent.getStringExtra(titleExtra));
        builder.setContentText(intent.getStringExtra(messageExtra));
        builder.setContentIntent(pendingIntent);
        builder.build();
        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notificationId, builder.build());

    }
}
