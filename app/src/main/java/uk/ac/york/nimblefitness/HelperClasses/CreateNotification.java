package uk.ac.york.nimblefitness.HelperClasses;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * A handler to ease the creation of notifications. The main use of notifications is when the
 * user reaches their weekly goal.
 */

public class CreateNotification {
    Intent intent;
    PendingIntent pendingIntent;
    NotificationCompat.Builder notificationBuilder;
    NotificationManagerCompat notificationManagerCompat;
    CharSequence channelName;
    String channelDescription;
    int importance;
    NotificationChannel channel;
    NotificationManager notificationManager;
    String channelID;
    int notificationID;

    int notificationIcon;
    String notificationTitle;
    String notificationContent;
    Context context;
    Class aClass;

    public CreateNotification(int notificationIcon, String notificationTitle,
                              String notificationContent, Class aClass, String channelID,
                              int notificationID, Context context) {
        this.notificationIcon = notificationIcon;
        this.notificationTitle = notificationTitle;
        this.notificationContent = notificationContent;
        this.context = context;
        this.aClass = aClass;
        this.channelID = channelID;
        this.notificationID = notificationID;
        buildNotification();
    }

    public void buildNotification() {
        createNotificationChannel();
        intent = new Intent(context, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder =
                new NotificationCompat.Builder(context, channelID).setSmallIcon(notificationIcon)
                        .setContentTitle(notificationTitle).setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent).setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationContent));

        notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notificationID, notificationBuilder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelName = "NotificationChannel";
            channelDescription = "NotificationChannelDescription";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            channel = new NotificationChannel(channelID, channelName, importance);
            channel.setDescription(channelDescription);

            notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
