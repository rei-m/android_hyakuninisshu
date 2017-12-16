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
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.Menu;
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
import me.rei_m.hyakuninisshu.databinding.ActivityMaterialDetailBinding;
import me.rei_m.hyakuninisshu.di.ForActivity;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.presentation.ad.AdViewFactory;
import me.rei_m.hyakuninisshu.presentation.ad.AdViewHelper;
import me.rei_m.hyakuninisshu.presentation.di.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter.MaterialDetailPagerAdapter;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialDetailFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.MaterialDetailActivityViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.di.MaterialDetailActivityViewModelModule;

public class MaterialDetailActivity extends DaggerAppCompatActivity {

    private static final String ARG_LIST_POSITION = "listPosition";
    private static final String ARG_COLOR_FILTER = "colorFilter";

    public static Intent createIntent(@NonNull Context context,
                                      int position,
                                      @NonNull ColorFilter colorFilter) {
        Intent intent = new Intent(context, MaterialDetailActivity.class);
        intent.putExtra(ARG_LIST_POSITION, position);
        intent.putExtra(ARG_COLOR_FILTER, colorFilter.ordinal());
        return intent;
    }

    @Inject
    AdViewFactory adViewFactory;

    @Inject
    Navigator navigator;

    @Inject
    MaterialDetailActivityViewModel.Factory viewModelFactory;

    private ActivityMaterialDetailBinding binding;

    private MaterialDetailActivityViewModel viewModel;

    private MaterialDetailPagerAdapter adapter;

    private AdView adView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MaterialDetailActivityViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_material_detail);

        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        adapter = new MaterialDetailPagerAdapter(getSupportFragmentManager(), viewModel.karutaList);
        binding.pager.setAdapter(adapter);
        binding.pager.setCurrentItem(getIntent().getIntExtra(ARG_LIST_POSITION, 0));
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

        adapter.releaseCallback();
        adapter = null;

        adViewFactory = null;
        viewModel = null;
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
                Karuta karuta = viewModel.karutaList.get(binding.pager.getCurrentItem());
                navigator.navigateToMaterialEdit(karuta);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @ForActivity
    @dagger.Subcomponent(modules = {
            ActivityModule.class,
            MaterialDetailActivityViewModelModule.class,
            MaterialDetailFragment.Module.class
    })
    public interface Subcomponent extends AndroidInjector<MaterialDetailActivity> {

        @dagger.Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<MaterialDetailActivity> {

            @SuppressWarnings("UnusedReturnValue")
            public abstract Builder activityModule(ActivityModule module);

            @SuppressWarnings("UnusedReturnValue")
            public abstract Builder viewModelModule(MaterialDetailActivityViewModelModule module);

            @Override
            public void seedInstance(MaterialDetailActivity instance) {
                activityModule(new ActivityModule(instance));

                ColorFilter colorFilter = ColorFilter.get(instance.getIntent().getIntExtra(ARG_COLOR_FILTER, ColorFilter.ALL.ordinal()));

                viewModelModule(new MaterialDetailActivityViewModelModule(colorFilter));
            }
        }
    }

    @dagger.Module(subcomponents = Subcomponent.class)
    public abstract class Module {
        @SuppressWarnings("unused")
        @Binds
        @IntoMap
        @ActivityKey(MaterialDetailActivity.class)
        abstract AndroidInjector.Factory<? extends Activity> bind(Subcomponent.Builder builder);
    }
}
