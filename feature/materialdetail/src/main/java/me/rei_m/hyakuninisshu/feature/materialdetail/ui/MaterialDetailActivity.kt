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
package me.rei_m.hyakuninisshu.feature.materialdetail.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import dagger.Binds
import dagger.multibindings.ClassKey
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.IntoMap
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityModule
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.ColorFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.provideViewModel
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.setupActionBar
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.showAlertDialog
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.EventObserver
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.ad.AdViewObserver
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.dialog.AlertDialogFragment
import me.rei_m.hyakuninisshu.feature.materialdetail.R
import me.rei_m.hyakuninisshu.feature.materialdetail.databinding.ActivityMaterialDetailBinding
import me.rei_m.hyakuninisshu.feature.materialdetail.di.MaterialDetailModule
import me.rei_m.hyakuninisshu.feature.materialdetail.helper.Navigator
import javax.inject.Inject

class MaterialDetailActivity : DaggerAppCompatActivity(),
    AlertDialogFragment.OnDialogInteractionListener {

    @Inject
    lateinit var viewModelFactory: MaterialDetailViewModel.Factory

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var adViewObserver: AdViewObserver

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    private lateinit var viewModel: MaterialDetailViewModel

    private lateinit var binding: ActivityMaterialDetailBinding

    private val lastPosition by lazy {
        intent.getIntExtra(ARG_LIST_POSITION, 0)
    }

    private val colorFilter by lazy {
        ColorFilter[intent.getIntExtra(ARG_COLOR_FILTER, 0)]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel(MaterialDetailViewModel::class.java, viewModelFactory)
        viewModel.unhandledErrorEvent.observe(this, EventObserver {
            showAlertDialog(R.string.text_title_error, R.string.text_message_unhandled_error)
        })

        binding = DataBindingUtil.setContentView(this, R.layout.activity_material_detail)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupActionBar(binding.toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }

        setupAd()

        binding.pager.adapter = MaterialDetailPagerAdapter(supportFragmentManager)

        binding.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(p0: Int) {
                trackInfoScreenView(p0)
            }
        })

        trackInfoScreenView(lastPosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_material_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.activity_material_detail_edit -> {
                viewModel.karutaList.value?.let {
                    navigator.navigateToMaterialEdit(it[binding.pager.currentItem].identifier)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAlertPositiveClick() {
        finish()
    }

    override fun onAlertNegativeClick() {
        // Negative Button is disable.
    }

    private fun trackInfoScreenView(position: Int) {
        val karutaList = viewModel.karutaList.value ?: return
        val karutaId = karutaList[position].identifier.value
        analyticsHelper.sendScreenView("MaterialDetail-$karutaId", this)
    }

    private fun setupAd() {
        lifecycle.addObserver(adViewObserver)
        val adView = adViewObserver.adView()
        val params = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, adView.id)
        }
        adView.layoutParams = params
        binding.root.addView(adView)

        adViewObserver.loadAd()
    }

    @ActivityScope
    @dagger.Subcomponent(
        modules = [
            ActivityModule::class,
            MaterialDetailModule::class,
            MaterialDetailFragment.Module::class
        ]
    )
    interface Subcomponent : AndroidInjector<MaterialDetailActivity> {
        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Factory<MaterialDetailActivity> {
            override fun create(instance: MaterialDetailActivity): AndroidInjector<MaterialDetailActivity> =
                activityModule(ActivityModule(instance)).
                    materialDetailModule(MaterialDetailModule(instance.colorFilter, instance.lastPosition))
                    .build()

            abstract fun activityModule(module: ActivityModule): Builder

            abstract fun materialDetailModule(module: MaterialDetailModule): Builder

            abstract fun build(): AndroidInjector<MaterialDetailActivity>
        }
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract class Module {
        @Binds
        @IntoMap
        @ClassKey(MaterialDetailActivity::class)
        abstract fun bind(builder: Subcomponent.Builder): AndroidInjector.Factory<*>
    }

    companion object {

        private const val ARG_LIST_POSITION = "listPosition"

        private const val ARG_COLOR_FILTER = "colorFilter"

        fun createIntent(
            context: Context,
            position: Int,
            colorFilter: ColorFilter
        ) = Intent(context, MaterialDetailActivity::class.java).apply {
            putExtra(ARG_LIST_POSITION, position)
            putExtra(ARG_COLOR_FILTER, colorFilter.ordinal)
        }
    }
}
