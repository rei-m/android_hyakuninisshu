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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.feature.entrance.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import hotchemi.android.rate.AppRate
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityModule
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.addFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.replaceFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.setupActionBar
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.ad.AdViewObserver
import me.rei_m.hyakuninisshu.feature.entrance.R
import me.rei_m.hyakuninisshu.feature.entrance.databinding.ActivityEntranceBinding
import me.rei_m.hyakuninisshu.feature.exammenu.ui.ExamMenuFragment
import me.rei_m.hyakuninisshu.feature.materiallist.ui.MaterialListFragment
import me.rei_m.hyakuninisshu.feature.support.ui.SupportFragment
import me.rei_m.hyakuninisshu.feature.trainingmenu.ui.TrainingMenuFragment
import javax.inject.Inject

class EntranceActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var adViewObserver: AdViewObserver

    private lateinit var binding: ActivityEntranceBinding

    private val initialPage = Page.forBottomNavigationId(R.id.bottom_navigation_training)

    private var currentPageIndex: Int = initialPage.ordinal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntranceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupActionBar(binding.toolbar) {
        }

        subscribeUi()

        setupAd()

        setupAppRate()

        if (savedInstanceState == null) {
            addFragment(R.id.content, initialPage.newInstance(), initialPage.tag)
        } else {
            currentPageIndex = savedInstanceState.getInt(KEY_PAGE_INDEX, 0)
            val page = Page.values()[currentPageIndex]
            replaceFragment(R.id.content, page.newInstance(), page.tag)
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
            replaceFragment(
                R.id.content,
                page.newInstance(),
                page.tag
            )
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setupAd() {
        lifecycle.addObserver(adViewObserver)
        adViewObserver.loadAd(this, binding.adViewContainer)
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

    @ActivityScope
    @dagger.Subcomponent(
        modules = [
            ActivityModule::class,
            TrainingMenuFragment.Module::class,
            ExamMenuFragment.Module::class,
            MaterialListFragment.Module::class,
            SupportFragment.Module::class
        ]
    )
    interface Subcomponent : AndroidInjector<EntranceActivity> {
        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Factory<EntranceActivity> {
            override fun create(instance: EntranceActivity): AndroidInjector<EntranceActivity> =
                activityModule(ActivityModule(instance)).build()

            abstract fun activityModule(module: ActivityModule): Builder

            abstract fun build(): AndroidInjector<EntranceActivity>
        }
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract class Module {
        @Binds
        @IntoMap
        @ClassKey(EntranceActivity::class)
        abstract fun bind(builder: Subcomponent.Builder): AndroidInjector.Factory<*>
    }

    companion object {

        private const val KEY_PAGE_INDEX = "pageIndex"

        fun createIntent(context: Context) = Intent(context, EntranceActivity::class.java)
    }
}
