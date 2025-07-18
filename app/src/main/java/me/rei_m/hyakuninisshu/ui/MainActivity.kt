/*
 * Copyright (c) 2025. Rei Matsushita
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

package me.rei_m.hyakuninisshu.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.rei_m.hyakuninisshu.App
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.di.MainComponent
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.setupActionBar
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.ad.AdViewObserver

class MainActivity :
    AppCompatActivity(),
    MainComponent.Injector {
    override fun mainComponent(): MainComponent = _mainComponent

    private val adViewObserver: AdViewObserver = AdViewObserver()

    private lateinit var _mainComponent: MainComponent

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        _mainComponent = (application as App).appComponent.mainComponent().create(this)
        _mainComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar(findViewById(R.id.toolbar)) {
        }

        setupNavController()

        setupAd()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, windowInsets ->
            val bars = windowInsets.getInsets(
                WindowInsetsCompat.Type.statusBars(),
            )
            v.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right,
                bottom = bars.bottom,
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupNavController() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.navigation_training_menu,
                    R.id.navigation_exam_menu,
                    R.id.navigation_material_list,
                    R.id.navigation_support,
                ),
            )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val adViewContainer: FrameLayout = findViewById(R.id.ad_view_container)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                me.rei_m.hyakuninisshu.feature.corecomponent.R.id.splash_fragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    navView.visibility = View.GONE
                }

                me.rei_m.hyakuninisshu.feature.corecomponent.R.id.navigation_training_starter,
                me.rei_m.hyakuninisshu.feature.corecomponent.R.id.navigation_training_re_starter,
                me.rei_m.hyakuninisshu.feature.corecomponent.R.id.navigation_exam_practice_training_starter,
                me.rei_m.hyakuninisshu.feature.corecomponent.R.id.navigation_exam_finisher,
                me.rei_m.hyakuninisshu.feature.corecomponent.R.id.navigation_question,
                me.rei_m.hyakuninisshu.feature.corecomponent.R.id.navigation_question_answer,
                    -> {
                    navView.visibility = View.GONE
                    adViewObserver.hideAd()
                }

                me.rei_m.hyakuninisshu.feature.corecomponent.R.id.navigation_training_result,
                me.rei_m.hyakuninisshu.feature.corecomponent.R.id.navigation_exam_result,
                me.rei_m.hyakuninisshu.feature.corecomponent.R.id.navigation_exam_history,
                me.rei_m.hyakuninisshu.feature.corecomponent.R.id.navigation_material_detail,
                me.rei_m.hyakuninisshu.feature.corecomponent.R.id.navigation_material_detail_page,
                me.rei_m.hyakuninisshu.feature.corecomponent.R.id.navigation_material_edit,
                me.rei_m.hyakuninisshu.feature.corecomponent.R.id.navigation_material_edit_confirm_dialog,
                    -> {
                    navView.visibility = View.GONE
                    adViewObserver.showAd(this, adViewContainer)
                }

                else -> {
                    navView.visibility = View.VISIBLE
                    adViewObserver.showAd(this, adViewContainer)
                }
            }
        }
    }

    private fun setupAd() {
        lifecycle.addObserver(adViewObserver)
    }
}
