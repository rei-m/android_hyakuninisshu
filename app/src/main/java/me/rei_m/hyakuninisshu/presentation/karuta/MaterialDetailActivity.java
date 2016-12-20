package me.rei_m.hyakuninisshu.presentation.karuta;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.ActivityMaterialDetailBinding;
import me.rei_m.hyakuninisshu.presentation.BaseActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.component.MaterialDetailActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.module.MaterialDetailActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter.MaterialDetailPagerAdapter;
import me.rei_m.hyakuninisshu.presentation.utilitty.ViewUtil;

public class MaterialDetailActivity extends BaseActivity implements HasComponent<MaterialDetailActivityComponent> {

    private static final String ARG_KARUTA_NO = "karutaNo";

    private static final int UNKNOWN_KARUTA_NO = -1;

    public static Intent createIntent(@NonNull Context context,
                                      int karutaNo) {
        Intent intent = new Intent(context, MaterialDetailActivity.class);
        intent.putExtra(ARG_KARUTA_NO, karutaNo);
        return intent;
    }

    private MaterialDetailActivityComponent component;

    private ActivityMaterialDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_material_detail);

        setSupportActionBar(binding.toolbar);

        // TODO エラーチェック.
        int initialDisplayKarutaNo = getIntent().getIntExtra(ARG_KARUTA_NO, UNKNOWN_KARUTA_NO);

        binding.pager.setAdapter(new MaterialDetailPagerAdapter(getSupportFragmentManager()));
        binding.pager.setCurrentItem(initialDisplayKarutaNo - 1);
        ViewUtil.loadAd(binding.adView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        component = null;
        binding = null;
    }

    @Override
    protected void setupActivityComponent() {
        component = ((App) getApplication()).getComponent().plus(new MaterialDetailActivityModule(this));
        component.inject(this);
    }

    @Override
    public MaterialDetailActivityComponent getComponent() {
        if (component == null) {
            setupActivityComponent();
        }
        return component;
    }
}
