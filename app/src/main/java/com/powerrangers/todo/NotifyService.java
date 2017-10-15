package com.powerrangers.todo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class NotifyService extends Service {
  NotificationCompat.Builder notificationBuilder;

  private void setupNotificationOverhead () {
    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    notificationBuilder = new NotificationCompat.Builder(this)
      .setSmallIcon(R.drawable.notification_icon)
      .setContentTitle("My notification")
      .setContentText("Hello World!")
      .setVibrate(new long[] { 1000, 1000 })
      .setSound(alarmSound);
  }

  private void sendMockNotification () {
    int notificationId = 101;
    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    notificationManager.notify(notificationId, notificationBuilder.build());
  }

  public void onCreate () {
    setupNotificationOverhead();
    sendMockNotification();
  }

  @Override
  public IBinder onBind(Intent intent) {
      // TODO: Return the communication channel to the service.
      throw new UnsupportedOperationException("Not yet implemented");
  }
}
