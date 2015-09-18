package com.rut0.kurisumessenger.helper;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by Winston on 9/17/2015.
 */
public class Extensions {

    public static void runOnUiThread(Runnable run) {
        runOnUiThread(run, true);
    }

    public static void runOnUiThread(final Runnable run, boolean check) {
        if (check && Looper.getMainLooper().equals(Looper.myLooper())) {
            run.run();
        } else {
            new Handler(Looper.getMainLooper()).post(run);
        }
    }

}
