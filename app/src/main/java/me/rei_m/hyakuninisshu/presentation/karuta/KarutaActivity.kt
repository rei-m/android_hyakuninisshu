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
package me.rei_m.hyakuninisshu.presentation.karuta

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import dagger.Binds
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.IntoMap
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.databinding.ActivityKarutaBinding
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.ext.addFragment
import me.rei_m.hyakuninisshu.ext.setupActionBar
import me.rei_m.hyakuninisshu.ext.showAlertDialog
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityModule
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.ad.AdViewObserver
import me.rei_m.hyakuninisshu.presentation.di.OldActivityModule
import me.rei_m.hyakuninisshu.presentation.karuta.di.KarutaActivityModule
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.dialog.AlertDialogFragment
import javax.inject.Inject

class KarutaActivity : DaggerAppCompatActivity(),
    KarutaFragment.OnFragmentInteractionListener,
    AlertDialogFragment.OnDialogInteractionListener {

    @Inject
    lateinit var adViewObserver: AdViewObserver

    private lateinit var binding: ActivityKarutaBinding

    private val karutaId by lazy {
        intent.getParcelableExtra<KarutaIdentifier>(ARG_KARUTA_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_karuta)

        setupActionBar(binding.toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }

        setupAd()

        if (savedInstanceState == null) {
            addFragment(R.id.content, KarutaFragment.newInstance(karutaId), KarutaFragment.TAG)
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
        showAlertDialog(R.string.text_title_error, R.string.text_message_illegal_arguments)
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
            OldActivityModule::class,
            KarutaActivityModule::class,
            KarutaFragment.Module::class
        ]
    )
    interface Subcomponent : AndroidInjector<KarutaActivity> {

        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Builder<KarutaActivity>() {

            abstract fun oldActivityModule(module: OldActivityModule): Builder

            abstract fun activityModule(module: ActivityModule): Builder

            abstract fun karutaActivityModule(module: KarutaActivityModule): Builder

            override fun seedInstance(instance: KarutaActivity) {
                activityModule(ActivityModule(instance))
                oldActivityModule(OldActivityModule(instance))
                karutaActivityModule(KarutaActivityModule(instance.karutaId))
            }
        }
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract class Module {
        @Binds
        @IntoMap
        @ActivityKey(KarutaActivity::class)
        abstract fun bind(builder: Subcomponent.Builder): AndroidInjector.Factory<out Activity>
    }

    companion object {

        private const val ARG_KARUTA_ID = "karutaId"

        fun createIntent(
            context: Context,
            karutaId: KarutaIdentifier
        ) = Intent(context, KarutaActivity::class.java).apply {
            putExtra(ARG_KARUTA_ID, karutaId)
        }
    }
}
