package me.rei_m.hyakuninisshu.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.di.ActivityComponent;

public class BaseActivity extends AppCompatActivity implements GraphActivity {

    private ActivityComponent component;

    @Override
    public ActivityComponent getComponent() {
        return component;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component = ((App) getApplication()).getComponent().activityComponent();
    }
}
