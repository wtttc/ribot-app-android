package io.ribot.app.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.ribot.app.data.local.DatabaseHelper;
import io.ribot.app.data.local.PreferencesHelper;
import io.ribot.app.data.remote.RibotService;
import io.ribot.app.util.EventPosterHelper;
import rx.Observable;
import rx.functions.Action0;

@Singleton
public class DataManager {

    private final RibotService mRibotService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final EventPosterHelper mEventPoster;

    @Inject
    public DataManager(RibotService ribotService,
                       DatabaseHelper databaseHelper,
                       PreferencesHelper preferencesHelper,
                       EventPosterHelper eventPosterHelper) {
        mRibotService = ribotService;
        mDatabaseHelper = databaseHelper;
        mPreferencesHelper = preferencesHelper;
        mEventPoster = eventPosterHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Void> signOut() {
        return mDatabaseHelper.clearTables()
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        mPreferencesHelper.clear();
                        mEventPoster.postEventSafely(new BusEvent.UserSignedOut());
                    }
                });
    }


    //  Helper method to post events from doOnCompleted.
    private Action0 postEventSafelyAction(final Object event) {
        return new Action0() {
            @Override
            public void call() {
                mEventPoster.postEventSafely(event);
            }
        };
    }


}
