/*
 * Copyright (c) 2018. Rei Matsushita
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

package me.rei_m.hyakuninisshu.presentation.entrance

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.ViewGroup
import android.widget.RelativeLayout
import dagger.Binds
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.IntoMap
import hotchemi.android.rate.AppRate
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.databinding.ActivityEntranceBinding
import me.rei_m.hyakuninisshu.di.ForActivity
import me.rei_m.hyakuninisshu.ext.AppCompatActivityExt
import me.rei_m.hyakuninisshu.presentation.widget.ad.AdViewObserver
import me.rei_m.hyakuninisshu.presentation.di.ActivityModule
import javax.inject.Inject

class EntranceActivity : DaggerAppCompatActivity(), AppCompatActivityExt {

    @Inject
    lateinit var adViewObserver: AdViewObserver

    private lateinit var binding: ActivityEntranceBinding

    private val initialPage = Page.forBottomNavigationId(R.id.bottom_navigation_training)

    private var currentPageIndex: Int = initialPage.ordinal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_entrance)

        setupActionBar(binding.toolbar) {
        }

        subscribeUi()

        setupAd()

        setupAppRate()

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.content, initialPage.newInstance(), initialPage.tag)
                    .commit()
        } else {
            currentPageIndex = savedInstanceState.getInt(KEY_PAGE_INDEX, 0)
            binding.bottomNavigation.menu.getItem(currentPageIndex).isChecked = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_PAGE_INDEX, currentPageIndex)
        super.onSaveInstanceState(outState)
    }

    private fun subscribeUi() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            val page = Page.forBottomNavigationId(item.itemId)
            if (currentPageIndex == page.ordinal) {
                return@setOnNavigationItemSelectedListener false
            }
            currentPageIndex = page.ordinal
            supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.content, page.newInstance(), page.tag)
                    .commit()
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setupAd() {
        lifecycle.addObserver(adViewObserver)
        val adView = adViewObserver.adView()
        val params = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.ABOVE, R.id.bottom_navigation)
        }
        adView.layoutParams = params
        binding.root.addView(adView)

        adViewObserver.loadAd()
    }

    private fun setupAppRate() {
        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(10)
                .setShowLaterButton(true)
                .setDebug(false)
                .monitor()

        AppRate.showRateDialogIfMeetsConditions(this)
    }

    enum class Page(private val bottomNavigationId: Int) {
        TRAINING(R.id.bottom_navigation_training) {
            override val tag = TrainingMenuFragment.TAG
            override fun newInstance() = TrainingMenuFragment.newInstance()
        },
        EXAM(R.id.bottom_navigation_exam) {
            override val tag = ExamMenuFragment.TAG
            override fun newInstance() = ExamMenuFragment.newInstance()
        },
        MATERIAL(R.id.bottom_navigation_material) {
            override val tag = MaterialListFragment.TAG
            override fun newInstance() = MaterialListFragment.newInstance()
        },
        SUPPORT(R.id.bottom_navigation_support) {
            override val tag = SupportFragment.TAG
            override fun newInstance() = SupportFragment.newInstance()
        };

        abstract val tag: String

        abstract fun newInstance(): Fragment

        companion object {
            fun forBottomNavigationId(bottomNavigationId: Int): Page = values().find {
                it.bottomNavigationId == bottomNavigationId
            } ?: let {
                throw AssertionError("no navigation enum found for the id. you forgot to implement?")
            }
        }
    }

    @ForActivity
    @dagger.Subcomponent(modules = [
        ActivityModule::class,
        TrainingMenuFragment.Module::class,
        ExamMenuFragment.Module::class,
        MaterialListFragment.Module::class,
        SupportFragment.Module::class
    ])
    interface Subcomponent : AndroidInjector<EntranceActivity> {
        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Builder<EntranceActivity>() {

            abstract fun activityModule(module: ActivityModule): Builder

            override fun seedInstance(instance: EntranceActivity) {
                activityModule(ActivityModule(instance))
            }
        }
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract class Module {
        @Binds
        @IntoMap
        @ActivityKey(EntranceActivity::class)
        abstract fun bind(builder: Subcomponent.Builder): AndroidInjector.Factory<out Activity>
    }

    companion object {

        private const val KEY_PAGE_INDEX = "pageIndex"

        fun createIntent(context: Context) = Intent(context, EntranceActivity::class.java)
    }
}
