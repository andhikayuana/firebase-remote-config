package io.github.andhikayuana.remoteconfigdemo;

import android.app.Application;
import android.content.Context;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 6/7/17
 */

public class App extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;


    }
}
