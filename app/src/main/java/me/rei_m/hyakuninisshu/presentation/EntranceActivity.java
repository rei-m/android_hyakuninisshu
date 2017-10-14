package me.rei_m.hyakuninisshu.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import dagger.Binds;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.multibindings.IntoMap;
import hotchemi.android.rate.AppRate;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.ActivityEntranceBinding;
import me.rei_m.hyakuninisshu.di.ForActivity;
import me.rei_m.hyakuninisshu.presentation.di.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.ExamFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuFragment;
import me.rei_m.hyakuninisshu.presentation.support.widget.fragment.SupportFragment;
import me.rei_m.hyakuninisshu.presentation.utility.ViewUtil;

public class EntranceActivity extends DaggerAppCompatActivity {

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, EntranceActivity.class);
    }

    private static String KEY_PAGE_INDEX = "pageIndex";

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

            public abstract Builder activityModule(ActivityModule module);

            @Override
            public void seedInstance(EntranceActivity instance) {
                activityModule(new ActivityModule(instance));
            }
        }
    }

    @dagger.Module(subcomponents = Subcomponent.class)
    public abstract class Module {
        @Binds
        @IntoMap
        @ActivityKey(EntranceActivity.class)
        abstract AndroidInjector.Factory<? extends Activity> bind(Subcomponent.Builder builder);
    }

    private ActivityEntranceBinding binding;

    private int currentPageIndex = 0;

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

        ViewUtil.loadAd(binding.adView);

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
        super.onDestroy();
        binding = null;
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

        public static Page forBottomNavigationId(int bottomNavigationId) {
            for (Page page : values()) {
                if (page.bottomNavigationId == bottomNavigationId) {
                    return page;
                }
            }

            throw new AssertionError("no navigation enum found for the id. you forgot to implement?");
        }

        private final int bottomNavigationId;

        Page(int bottomNavigationId) {
            this.bottomNavigationId = bottomNavigationId;
        }

        abstract Fragment newInstance();

        abstract String getTag();
    }
}
