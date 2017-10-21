package me.rei_m.hyakuninisshu.presentation.karuta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import dagger.Binds;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.multibindings.IntoMap;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.ActivityMaterialEditBinding;
import me.rei_m.hyakuninisshu.di.ForActivity;
import me.rei_m.hyakuninisshu.presentation.AlertDialogFragment;
import me.rei_m.hyakuninisshu.presentation.di.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.dialog.ConfirmMaterialEditDialogFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialEditFragment;
import me.rei_m.hyakuninisshu.presentation.utility.ViewUtil;

public class MaterialEditActivity extends DaggerAppCompatActivity implements MaterialEditFragment.OnFragmentInteractionListener,
        AlertDialogFragment.OnDialogInteractionListener {

    private static final String ARG_KARUTA_NO = "karutaNo";

    private static final int UNKNOWN_KARUTA_NO = -1;

    public static Intent createIntent(@NonNull Context context,
                                      int karutaNo) {
        Intent intent = new Intent(context, MaterialEditActivity.class);
        intent.putExtra(ARG_KARUTA_NO, karutaNo);
        return intent;
    }

    @ForActivity
    @dagger.Subcomponent(modules = {
            ActivityModule.class,
            MaterialEditFragment.Module.class,
            ConfirmMaterialEditDialogFragment.Module.class
    })
    public interface Subcomponent extends AndroidInjector<MaterialEditActivity> {

        @dagger.Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<MaterialEditActivity> {

            public abstract Builder activityModule(ActivityModule module);

            @Override
            public void seedInstance(MaterialEditActivity instance) {
                activityModule(new ActivityModule(instance));
            }
        }
    }

    @dagger.Module(subcomponents = Subcomponent.class)
    public abstract class Module {
        @Binds
        @IntoMap
        @ActivityKey(MaterialEditActivity.class)
        abstract AndroidInjector.Factory<? extends Activity> bind(Subcomponent.Builder builder);
    }

    private ActivityMaterialEditBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_material_edit);

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
                    .add(R.id.content, MaterialEditFragment.newInstance(karutaNo), MaterialEditFragment.TAG)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
