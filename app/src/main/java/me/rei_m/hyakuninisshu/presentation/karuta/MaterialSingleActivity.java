/*
 * Copyright (c) 2017. Rei Matsushita
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package me.rei_m.hyakuninisshu.presentation.karuta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdView;

import javax.inject.Inject;

import dagger.Binds;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.multibindings.IntoMap;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.ActivityMaterialSingleBinding;
import me.rei_m.hyakuninisshu.di.ForActivity;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.presentation.ad.AdViewFactory;
import me.rei_m.hyakuninisshu.presentation.ad.AdViewHelper;
import me.rei_m.hyakuninisshu.presentation.di.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialDetailFragment;

public class MaterialSingleActivity extends DaggerAppCompatActivity {

    private static final String ARG_KARUTA_ID = "karutaId";

    public static Intent createIntent(@NonNull Context context,
                                      @NonNull KarutaIdentifier karutaId) {
        Intent intent = new Intent(context, MaterialSingleActivity.class);
        intent.putExtra(ARG_KARUTA_ID, karutaId);
        return intent;
    }

    @Inject
    AdViewFactory adViewFactory;

    private ActivityMaterialSingleBinding binding;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_material_single);

        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        KarutaIdentifier karutaId = getIntent().getParcelableExtra(ARG_KARUTA_ID);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, MaterialDetailFragment.newInstance(karutaId), MaterialDetailFragment.TAG)
                    .commit();
        }

        adView = adViewFactory.create();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, adView.getId());
        adView.setLayoutParams(params);
        binding.root.addView(adView);

        AdViewHelper.loadAd(adView);
    }

    @Override
    protected void onDestroy() {
        AdViewHelper.release(adView);

        adView.destroy();
        adView = null;

        adViewFactory = null;
        binding = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adView.resume();
    }

    @Override
    protected void onPause() {
        adView.pause();
        super.onPause();
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

    @ForActivity
    @dagger.Subcomponent(modules = {
            ActivityModule.class,
            MaterialDetailFragment.Module.class
    })
    public interface Subcomponent extends AndroidInjector<MaterialSingleActivity> {

        @dagger.Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<MaterialSingleActivity> {

            @SuppressWarnings("UnusedReturnValue")
            public abstract Builder activityModule(ActivityModule module);

            @Override
            public void seedInstance(MaterialSingleActivity instance) {
                activityModule(new ActivityModule(instance));
            }
        }
    }

    @dagger.Module(subcomponents = Subcomponent.class)
    public abstract class Module {
        @SuppressWarnings("unused")
        @Binds
        @IntoMap
        @ActivityKey(MaterialSingleActivity.class)
        abstract AndroidInjector.Factory<? extends Activity> bind(Subcomponent.Builder builder);
    }
}
