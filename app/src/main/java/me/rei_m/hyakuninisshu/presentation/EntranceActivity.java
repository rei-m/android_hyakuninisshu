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

package me.rei_m.hyakuninisshu.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdView;

import javax.inject.Inject;

import dagger.Binds;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.multibindings.IntoMap;
import hotchemi.android.rate.AppRate;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.ActivityEntranceBinding;
import me.rei_m.hyakuninisshu.di.ForActivity;
import me.rei_m.hyakuninisshu.presentation.ad.AdViewFactory;
import me.rei_m.hyakuninisshu.presentation.ad.AdViewHelper;
import me.rei_m.hyakuninisshu.presentation.di.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.ExamFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuFragment;
import me.rei_m.hyakuninisshu.presentation.support.widget.fragment.SupportFragment;

public class EntranceActivity extends DaggerAppCompatActivity {

    private static final String KEY_PAGE_INDEX = "pageIndex";
    @Inject
    AdViewFactory adViewFactory;
    private ActivityEntranceBinding binding;
    private AdView adView;
    private int currentPageIndex = 0;

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, EntranceActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_entrance);

        setSupportActionBar(binding.toolbar);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            Page page = Page.forBottomNavigationId(item.getItemId());
            if (currentPageIndex == page.ordinal()) {
                return false;
            }
            currentPageIndex = page.ordinal();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.content, page.newInstance(), page.getTag())
                    .commit();
            return true;
        });

        if (savedInstanceState == null) {
            Page initialPage = Page.forBottomNavigationId(R.id.bottom_navigation_training);
            currentPageIndex = initialPage.ordinal();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, initialPage.newInstance(), initialPage.getTag())
                    .commit();
        } else {
            currentPageIndex = savedInstanceState.getInt(KEY_PAGE_INDEX, 0);
            binding.bottomNavigation.getMenu().getItem(currentPageIndex).setChecked(true);
        }

        adView = adViewFactory.create();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.ABOVE, R.id.bottom_navigation);
        adView.setLayoutParams(params);
        binding.root.addView(adView);

        AdViewHelper.loadAd(adView);

        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(10)
                .setShowLaterButton(true)
                .setDebug(false)
                .monitor();

        AppRate.showRateDialogIfMeetsConditions(this);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_PAGE_INDEX, currentPageIndex);
    }

    enum Page {
        TRAINING(R.id.bottom_navigation_training) {
            @Override
            Fragment newInstance() {
                return TrainingMenuFragment.newInstance();
            }

            @Override
            String getTag() {
                return TrainingMenuFragment.TAG;
            }
        },
        EXAM(R.id.bottom_navigation_exam) {
            @Override
            Fragment newInstance() {
                return ExamFragment.newInstance();
            }

            @Override
            String getTag() {
                return ExamFragment.TAG;
            }
        },
        MATERIAL(R.id.bottom_navigation_material) {
            @Override
            Fragment newInstance() {
                return MaterialFragment.newInstance();
            }

            @Override
            String getTag() {
                return MaterialFragment.TAG;
            }
        },
        SUPPORT(R.id.bottom_navigation_support) {
            @Override
            Fragment newInstance() {
                return SupportFragment.newInstance();
            }

            @Override
            String getTag() {
                return SupportFragment.TAG;
            }
        };

        private final int bottomNavigationId;

        Page(int bottomNavigationId) {
            this.bottomNavigationId = bottomNavigationId;
        }

        public static Page forBottomNavigationId(int bottomNavigationId) {
            for (Page page : values()) {
                if (page.bottomNavigationId == bottomNavigationId) {
                    return page;
                }
            }

            throw new AssertionError("no navigation enum found for the id. you forgot to implement?");
        }

        abstract Fragment newInstance();

        abstract String getTag();
    }

    @ForActivity
    @dagger.Subcomponent(modules = {
            ActivityModule.class,
            TrainingMenuFragment.Module.class,
            ExamFragment.Module.class,
            MaterialFragment.Module.class,
            SupportFragment.Module.class
    })
    public interface Subcomponent extends AndroidInjector<EntranceActivity> {
        @dagger.Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<EntranceActivity> {

            @SuppressWarnings("UnusedReturnValue")
            public abstract Builder activityModule(ActivityModule module);

            @Override
            public void seedInstance(EntranceActivity instance) {
                activityModule(new ActivityModule(instance));
            }
        }
    }

    @dagger.Module(subcomponents = Subcomponent.class)
    public abstract class Module {
        @SuppressWarnings("unused")
        @Binds
        @IntoMap
        @ActivityKey(EntranceActivity.class)
        abstract AndroidInjector.Factory<? extends Activity> bind(Subcomponent.Builder builder);
    }
}
