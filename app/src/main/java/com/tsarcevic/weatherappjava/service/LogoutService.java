package com.tsarcevic.weatherappjava.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tsarcevic.weatherappjava.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LogoutService extends Service {

    private static int counter;
    Timer timer;
    private static boolean isUserLoggedOut = false;

    public LogoutService() {
        super();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void resetTimer() {
        counter = 0;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counter++;
                if (counter > 10) {
                    resetTimer();
                    returnToLoginScreen();
                }
            }
        }, 1000, 1000);

        return super.onStartCommand(intent, flags, startId);
    }

    private void returnToLoginScreen() {
        Intent intent = new Intent(Constants.STARTING_ACTIVITY);
        intent.putExtra(Constants.STARTING_ACTIVITY, Constants.STARTING_ACTIVITY_RESTART);
        sendBroadcast(intent);
    }
}
