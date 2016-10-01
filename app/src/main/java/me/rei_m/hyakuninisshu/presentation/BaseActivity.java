package me.rei_m.hyakuninisshu.presentation;

import android.support.v7.app.AppCompatActivity;

import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.di.ActivityComponent;

public class BaseActivity extends AppCompatActivity implements GraphActivity {

    private ActivityComponent component;

    @Override
    public ActivityComponent getComponent() {
        if (component == null) {
            component = ((App) getApplication()).getComponent().activityComponent();
        }
        return component;
    }
}
