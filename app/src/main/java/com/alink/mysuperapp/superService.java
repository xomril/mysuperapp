package com.alink.mysuperapp;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class superService extends Service {
    public static final String ACTION_START_TIMER = "start.timer";
    public static final String ACTION_STOP_TIMER = "stop timer";
    public static final String BROADCAST_ACTION_TIMER_CHANGED = "timer.changed";
    public static final String BROADCAST_ACTION_TIMER_FINISHED = "timer.finished";
    public static final String EXTRA_TIME_REMAINING = "time.remaining";
    private boolean isTimerOn = false;
    private long timeRemaining;

    CountDownTimer countDownTimer;
    private void startTimer() {
        isTimerOn = true;
        Log.d("TAG", "timer start: " + timeRemaining);
        countDownTimer = new CountDownTimer(30_000, 1_000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished/1000;
                sendBroadcast(
                        new Intent(BROADCAST_ACTION_TIMER_CHANGED)
                                .putExtra(EXTRA_TIME_REMAINING,timeRemaining)
                );
                Log.d("TAG", "tick: " + timeRemaining);
            }

            @Override
            public void onFinish() {
                sendBroadcast(
                        new Intent(BROADCAST_ACTION_TIMER_FINISHED)
                );
                stopTimer();
            }
        };
        countDownTimer.start();
    }

    private void stopTimer() {
        Log.d("TAG", "btnStopTimer1233: " + timeRemaining);
        if(!isTimerOn)  return;
        isTimerOn = false;
        countDownTimer.cancel();
    }


    public superService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "startCommand: " +  intent.getAction());
        switch (intent.getAction()) {
            case ACTION_START_TIMER:
                startTimer();
                break;
            case ACTION_STOP_TIMER:
                stopTimer();
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}