package io.ribot.app.ui.main;

import javax.inject.Inject;

import io.ribot.app.data.DataManager;
import io.ribot.app.ui.base.Presenter;
import io.ribot.app.util.LogUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter implements Presenter<MainMvpView> {

    private final DataManager mDataManager;
    private MainMvpView mMvpView;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void signOut() {
        mSubscription = mDataManager.signOut()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i("Sign out successful!");
                        mMvpView.onSignedOut();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("Error signing out: " + e);
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }
}
