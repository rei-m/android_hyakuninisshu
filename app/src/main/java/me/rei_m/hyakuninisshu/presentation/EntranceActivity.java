package me.rei_m.hyakuninisshu.presentation;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.ActivityEntranceBinding;
import me.rei_m.hyakuninisshu.presentation.component.EntranceActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.ExamFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuFragment;
import me.rei_m.hyakuninisshu.presentation.module.EntranceActivityModule;

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

        setSupportActionBar(binding.toolbar);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            Page page = Page.forBottomNavigationId(item.getItemId());
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.content, page.newInstance(), page.getTag())
                    .commit();
            return true;
        });

        if (savedInstanceState == null) {
            Page initialPage = Page.forBottomNavigationId(R.id.bottom_navigation_training);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, initialPage.newInstance(), initialPage.getTag())
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        component = null;
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
        DOCUMENT(R.id.bottom_navigation_document) {
            @Override
            Fragment newInstance() {
                return TrainingMenuFragment.newInstance();
            }

            @Override
            String getTag() {
                return TrainingMenuFragment.TAG;
            }
        },
        SUPPORT(R.id.bottom_navigation_support) {
            @Override
            Fragment newInstance() {
                return TrainingMenuFragment.newInstance();
            }

            @Override
            String getTag() {
                return TrainingMenuFragment.TAG;
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
