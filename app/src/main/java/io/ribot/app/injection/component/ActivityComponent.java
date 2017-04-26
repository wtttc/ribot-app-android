package io.ribot.app.injection.component;

import dagger.Component;
import io.ribot.app.injection.PerActivity;
import io.ribot.app.injection.module.ActivityModule;
import io.ribot.app.ui.LauncherActivity;
import io.ribot.app.ui.MainActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LauncherActivity launcherActivity);

    void inject(MainActivity mainActivity);
}

