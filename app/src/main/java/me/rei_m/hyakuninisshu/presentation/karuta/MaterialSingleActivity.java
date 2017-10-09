package me.rei_m.hyakuninisshu.presentation.karuta;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.ActivityMaterialSingleBinding;
import me.rei_m.hyakuninisshu.presentation.AlertDialogFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialDetailFragment;
import me.rei_m.hyakuninisshu.presentation.utility.ViewUtil;

public class MaterialSingleActivity extends AppCompatActivity implements HasSupportFragmentInjector,
        MaterialDetailFragment.OnFragmentInteractionListener,
        AlertDialogFragment.OnDialogInteractionListener {

    private static final String ARG_KARUTA_NO = "karutaNo";

    private static final int UNKNOWN_KARUTA_NO = -1;

    public static Intent createIntent(@NonNull Context context,
                                      int karutaNo) {
        Intent intent = new Intent(context, MaterialSingleActivity.class);
        intent.putExtra(ARG_KARUTA_NO, karutaNo);
        return intent;
    }

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    private ActivityMaterialSingleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_material_single);

        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ViewUtil.loadAd(binding.adView);

        int karutaNo = getIntent().getIntExtra(ARG_KARUTA_NO, UNKNOWN_KARUTA_NO);

        if (karutaNo == UNKNOWN_KARUTA_NO) {
            onReceiveIllegalArguments();
            return;
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, MaterialDetailFragment.newInstance(karutaNo), MaterialDetailFragment.TAG)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentInjector = null;
        binding = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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
