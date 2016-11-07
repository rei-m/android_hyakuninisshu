package me.rei_m.hyakuninisshu.presentation.karuta;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.ActivityEntranceBinding;
import me.rei_m.hyakuninisshu.di.HasComponent;
import me.rei_m.hyakuninisshu.presentation.BaseActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.component.EntranceActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.module.EntranceActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuFragment;

public class EntranceActivity extends BaseActivity implements HasComponent<EntranceActivityComponent> {

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, EntranceActivity.class);
    }

    private EntranceActivityComponent component;

    private ActivityEntranceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_entrance);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            return false;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, TrainingMenuFragment.newInstance(), TrainingMenuFragment.TAG)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    protected void setupActivityComponent() {
        component = ((App) getApplication()).getComponent().plus(new EntranceActivityModule(this));
        component.inject(this);
    }

    @Override
    public EntranceActivityComponent getComponent() {
        if (component == null) {
            setupActivityComponent();
        }
        return component;
    }
}
