package io.ribot.app;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import io.ribot.app.data.DataManager;
import io.ribot.app.injection.component.ApplicationComponent;
import io.ribot.app.injection.component.DaggerApplicationComponent;
import io.ribot.app.injection.module.ApplicationModule;

public class RibotApplication extends Application {

    @Inject
    Bus mEventBus;
    @Inject
    DataManager mDataManager;
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();


        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
        mEventBus.register(this);
    }

    public static RibotApplication get(Context context) {
        return (RibotApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}

