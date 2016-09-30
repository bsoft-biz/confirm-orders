package biz.bsoft.confirmorders;

import android.app.Application;
import android.content.Context;

/**
 * Created by vbabin on 26.08.2016.
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
