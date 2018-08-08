package com.afeka.homie;

import android.app.Application;
import android.content.Context;

/**
 * Created by ben on 6/9/17.
 */

public class MyApplication extends Application {


    static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

    }

    public static  Context getMyContext () {

        return context;
    }
}
