package com.step.lifehuck.di.components;

import com.step.lifehuck.di.modules.MvpModule;
import com.step.lifehuck.mvp.presenters.MainPresenter;
import com.step.lifehuck.mvp.views.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MvpModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(MainPresenter presenter);
}
