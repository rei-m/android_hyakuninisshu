package me.rei_m.hyakuninisshu.presentation.karuta;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.ActivityMaterialDetailBinding;
import me.rei_m.hyakuninisshu.presentation.AlertDialogFragment;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter.MaterialDetailPagerAdapter;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialDetailFragment;
import me.rei_m.hyakuninisshu.presentation.utility.ViewUtil;

public class MaterialDetailActivity extends AppCompatActivity implements HasSupportFragmentInjector,
        MaterialDetailFragment.OnFragmentInteractionListener,
        AlertDialogFragment.OnDialogInteractionListener {

    private static final String ARG_KARUTA_NO = "karutaNo";

    private static final int UNKNOWN_KARUTA_NO = -1;

    public static Intent createIntent(@NonNull Context context,
                                      int karutaNo) {
        Intent intent = new Intent(context, MaterialDetailActivity.class);
        intent.putExtra(ARG_KARUTA_NO, karutaNo);
        return intent;
    }

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Inject
    Navigator navigator;

    private ActivityMaterialDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_material_detail);

        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        int initialDisplayKarutaNo = getIntent().getIntExtra(ARG_KARUTA_NO, UNKNOWN_KARUTA_NO);

        if (initialDisplayKarutaNo == UNKNOWN_KARUTA_NO) {
            onReceiveIllegalArguments();
            return;
        }

        binding.pager.setAdapter(new MaterialDetailPagerAdapter(getSupportFragmentManager()));
        binding.pager.setCurrentItem(initialDisplayKarutaNo - 1);
        ViewUtil.loadAd(binding.adView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentInjector = null;
        binding = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_material_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.activity_material_detail_edit:
                navigator.navigateToMaterialEdit(binding.pager.getCurrentItem() + 1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public void onReceiveIllegalArguments() {
        DialogFragment newFragment = AlertDialogFragment.newInstance(
                R.string.text_title_error,
                R.string.text_message_illegal_arguments,
                true,
                false);
        newFragment.show(getSupportFragmentManager(), AlertDialogFragment.TAG);
    }

    @Override
    public void onDialogPositiveClick() {
        finish();
    }

    @Override
    public void onDialogNegativeClick() {

    }
}
