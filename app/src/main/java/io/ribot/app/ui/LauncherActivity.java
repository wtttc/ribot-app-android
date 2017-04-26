package io.ribot.app.ui;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import io.ribot.app.data.DataManager;
import io.ribot.app.ui.base.BaseActivity;

public class LauncherActivity extends BaseActivity {

    @Inject
    DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        finish();
    }
}
