package raymond.liang.ilovezappos.jobmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import androidx.core.app.NotificationCompat;

import raymond.liang.ilovezappos.MainActivity;
import raymond.liang.ilovezappos.R;
import raymond.liang.ilovezappos.models.PriceAlert;
import raymond.liang.ilovezappos.requests.PriceAlertApi;
import raymond.liang.ilovezappos.requests.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceAlertJobService extends JobService {

    public static final String ALERT_KEY = "price_alert_float";
    @Override
    public boolean onStartJob(JobParameters params) {
        final float alertPrice = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getFloat(ALERT_KEY, Float.MIN_VALUE);
        String alertPriceString = alertPrice == Float.MIN_VALUE ? null : String.valueOf(alertPrice);
        if (alertPriceString != null) {
            PriceAlertApi priceAlertApi = ServiceGenerator.getPriceAlertApi();
            Call<PriceAlert> call = priceAlertApi.getPriceAlert(alertPriceString);
            call.enqueue(new Callback<PriceAlert>() {
                @Override
                public void onResponse(Call<PriceAlert> call, Response<PriceAlert> response) {
                    showNotification();
                }

                @Override
                public void onFailure(Call<PriceAlert> call, Throwable t) {

                }
            });

        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private void showNotification() {

        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.download)
                .setContentTitle("Price has dropped your desired price range")
                .setContentText("")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
