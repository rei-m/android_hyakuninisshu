/*
 * Copyright (c) 2019. Rei Matsushita
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
package me.rei_m.hyakuninisshu.feature.examhistory.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import dagger.Binds
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityModule
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.addFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.setupActionBar
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.showAlertDialog
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.ad.AdViewObserver
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.dialog.AlertDialogFragment
import me.rei_m.hyakuninisshu.feature.examhistory.R
import me.rei_m.hyakuninisshu.feature.examhistory.databinding.ActivityExamHistoryBinding
import javax.inject.Inject

class ExamHistoryActivity : DaggerAppCompatActivity(),
    ExamHistoryFragment.OnFragmentInteractionListener,
    AlertDialogFragment.OnDialogInteractionListener {

    @Inject
    lateinit var adViewObserver: AdViewObserver

    private lateinit var binding: ActivityExamHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exam_history)

        setupActionBar(binding.toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }

        setupAd()

        if (savedInstanceState == null) {
            addFragment(R.id.content, ExamHistoryFragment.newInstance(), ExamHistoryFragment.TAG)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
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

    override fun onError() {
        showAlertDialog(R.string.text_title_error, R.string.text_message_unhandled_error)
    }

    private fun setupAd() {
        lifecycle.addObserver(adViewObserver)
        adViewObserver.loadAd(this, binding.adViewContainer)
    }

    @ActivityScope
    @dagger.Subcomponent(
        modules = [
            ActivityModule::class,
            ExamHistoryFragment.Module::class
        ]
    )
    interface Subcomponent : AndroidInjector<ExamHistoryActivity> {
        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Factory<ExamHistoryActivity> {
            override fun create(instance: ExamHistoryActivity): AndroidInjector<ExamHistoryActivity> =
                activityModule(ActivityModule(instance)).build()

            abstract fun activityModule(module: ActivityModule): Builder

            abstract fun build(): AndroidInjector<ExamHistoryActivity>
        }
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract class Module {
        @Binds
        @IntoMap
        @ClassKey(ExamHistoryActivity::class)
        abstract fun bind(builder: Subcomponent.Builder): AndroidInjector.Factory<*>
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, ExamHistoryActivity::class.java)
    }
}
