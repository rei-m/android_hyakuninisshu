package me.rei_m.hyakuninisshu.presentation.karuta;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.ActivityEntranceBinding;
import me.rei_m.hyakuninisshu.presentation.BaseActivity;

public class EntranceActivity extends BaseActivity {

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, EntranceActivity.class);
    }

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
}
