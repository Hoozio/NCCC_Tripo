package com.exam.administrator.nccc_trip;

import android.os.StrictMode;

/**
 * Created by user on 2017-07-17.
 */

public class ThreadPolicy {

    public ThreadPolicy() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

}
