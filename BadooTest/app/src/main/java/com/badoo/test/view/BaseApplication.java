package com.badoo.test.view;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    private static Context context = null;
    private static BaseApplication application = null;

    public static Context getContext() {
        return context;
    }

    static public BaseApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        application = this;
    }

}
