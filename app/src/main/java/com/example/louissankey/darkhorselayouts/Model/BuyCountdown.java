package com.example.louissankey.darkhorselayouts.Model;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by louissankey on 6/7/16.
 */
public class BuyCountdown {

    private long mBuyTimeRemaining;
    private long mTimeIn;

    public void setBuyTimeRemaining(long buyTimeRemaining) {

        new CountDownTimer(buyTimeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                mBuyTimeRemaining = millisUntilFinished;
                setTimeIn(System.currentTimeMillis());
            }

            @Override
            public void onFinish() {
                mBuyTimeRemaining = 0;

            }
        }.start();

    }

    public long getBuyTimeRemaining() {
        return mBuyTimeRemaining;
    }


    public long getTimeIn() {
        return mTimeIn;
    }

    public void setTimeIn(long timeIn) {
        mTimeIn = timeIn;
    }

}
