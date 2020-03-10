package life.corals.merchant.fcm;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

import life.corals.merchant.R;
import life.corals.merchant.activity.SendNotificationActivity;
import life.corals.merchant.activity.SplashScreen;
import life.corals.merchant.activity.ViewNotificationActivity;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static life.corals.merchant.fcm.FcmConstants.IMAGE;
import static life.corals.merchant.fcm.FcmConstants.MERCHANT_NAME;
import static life.corals.merchant.fcm.FcmConstants.MESSAGE_BODY;
import static life.corals.merchant.fcm.FcmConstants.NOTIFICATION_TYPE;
import static life.corals.merchant.fcm.FcmConstants.NOTIFY_NOTIFICATION;
import static life.corals.merchant.fcm.FcmConstants.TITLE;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final int REQUEST_CODE = 1;
    private static final String CHANNEL_ID = "corals";

    private LocalBroadcastManager broadcaster;

    public MyFirebaseMessagingService() {
        super();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        super.onMessageReceived(message);
        try {
            String notType = message.getData().get(NOTIFICATION_TYPE);
            if (isAppOnForeground()) {
                if (NOTIFY_NOTIFICATION.equals(notType)) {
                    showNotification(message);
                } else {
                    showNotification(message);
                }
            } else {
                showNormalNotification(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNormalNotification(RemoteMessage message) {
        Intent intent = new Intent(getBaseContext(), SplashScreen.class);
        intent.putExtra(TITLE, message.getNotification().getTitle());
        intent.putExtra(MESSAGE_BODY, message.getNotification().getBody());
        intent.putExtra(IMAGE, message.getNotification().getImageUrl());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "corals")

                .setContentIntent(pendingIntent)
                .setContentTitle(message.getNotification().getTitle())
                .setContentText(message.getNotification().getBody())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message.getNotification().getBody()))
                .setPriority(message.getPriority())
                .setColor(getResources().getColor(R.color.logo_color))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.ic_splash_new)
                .setAutoCancel(true)
                .setVibrate(new long[]{100, 100, 100, 100});

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int NOTIFICATION_ID = (int) (System.currentTimeMillis() / 4);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        }

    }

    private void showNotification(RemoteMessage message) {
        if (createNotificationChannel() == 0) {
            Intent intent = new Intent(getApplicationContext(), ViewNotificationActivity.class);
            intent.putExtra(TITLE, message.getNotification().getTitle());
            intent.putExtra(MESSAGE_BODY, message.getNotification().getBody());
            intent.putExtra(IMAGE, message.getNotification().getImageUrl());
            if (message.getData().containsKey(MERCHANT_NAME) && message.getData().get(MERCHANT_NAME) != null) {
                intent.putExtra(MERCHANT_NAME, message.getData().get(MERCHANT_NAME));
            }
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)

                    .setContentIntent(pendingIntent)
                    .setContentTitle(message.getNotification().getTitle())
                    .setContentText(message.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setColor(getResources().getColor(R.color.logo_color))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setSmallIcon(R.drawable.ic_splash_new)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{100, 100, 100, 100})
                    .setChannelId(CHANNEL_ID);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int NOTIFICATION_ID = (int) (System.currentTimeMillis() / 4);
            if (notificationManager != null) {
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
            }
        }
    }

    private int createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            CharSequence channelName = "Corals";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 100, 100, 100});

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
                return 0;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    private boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

}