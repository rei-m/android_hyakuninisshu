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
package me.rei_m.hyakuninisshu.feature.materialedit.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.di.FragmentScope
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.provideActivityViewModel
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.withArgs
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.EventObserver
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.materialedit.databinding.FragmentMaterialEditBinding
import me.rei_m.hyakuninisshu.feature.materialedit.helper.Navigator
import javax.inject.Inject

class MaterialEditFragment : DaggerFragment(),
    ConfirmMaterialEditDialogFragment.OnDialogInteractionListener {

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: MaterialEditViewModel.Factory

    private lateinit var binding: FragmentMaterialEditBinding

    private lateinit var viewModel: MaterialEditViewModel

    private var listener: OnFragmentInteractionListener? = null

    private val karutaId by lazy {
        requireNotNull(arguments?.getParcelable<KarutaIdentifier>(ARG_KARUTA_ID)) {
            "$ARG_KARUTA_ID is missing"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) {
            with(viewModelFactory) {
                firstPhraseKanji = savedInstanceState.getString(KEY_FIRST_KANJI, "")
                firstPhraseKana = savedInstanceState.getString(KEY_FIRST_KANA, "")
                secondPhraseKanji = savedInstanceState.getString(KEY_SECOND_KANJI, "")
                secondPhraseKana = savedInstanceState.getString(KEY_SECOND_KANA, "")
                thirdPhraseKanji = savedInstanceState.getString(KEY_THIRD_KANJI, "")
                thirdPhraseKana = savedInstanceState.getString(KEY_THIRD_KANA, "")
                fourthPhraseKanji = savedInstanceState.getString(KEY_FOURTH_KANJI, "")
                fourthPhraseKana = savedInstanceState.getString(KEY_FOURTH_KANA, "")
                fifthPhraseKanji = savedInstanceState.getString(KEY_FIFTH_KANJI, "")
                fifthPhraseKana = savedInstanceState.getString(KEY_FIFTH_KANA, "")
            }
        }
        viewModel = provideActivityViewModel(MaterialEditViewModel::class.java, viewModelFactory)

        binding = FragmentMaterialEditBinding.inflate(inflater, container, false).apply {
            viewModel = this@MaterialEditFragment.viewModel
            setLifecycleOwner(this@MaterialEditFragment.viewLifecycleOwner)
        }

        viewModel.confirmEditEvent.observe(this,
            EventObserver { dialog ->
                fragmentManager?.let {
                    dialog.setTargetFragment(this, 0)
                    dialog.show(it, ConfirmMaterialEditDialogFragment.TAG)
                }
            })
        viewModel.completeEditEvent.observe(this, EventObserver {
            navigator.back()
        })
        viewModel.snackBarMessage.observe(this,
            EventObserver {
                Snackbar.make(binding.root, getString(it), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .show()
            })
        viewModel.unhandledErrorEvent.observe(this,
            EventObserver {
                listener?.onError()
            })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("MaterialEdit-${karutaId.value}", requireActivity())
    }

    override fun onDestroyView() {
        viewModel.onDestroyView()
        super.onDestroyView()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        with(outState) {
            putString(KEY_FIRST_KANJI, viewModel.firstPhraseKanji.value)
            putString(KEY_FIRST_KANA, viewModel.firstPhraseKana.value)
            putString(KEY_SECOND_KANJI, viewModel.secondPhraseKanji.value)
            putString(KEY_SECOND_KANA, viewModel.secondPhraseKana.value)
            putString(KEY_THIRD_KANJI, viewModel.thirdPhraseKanji.value)
            putString(KEY_THIRD_KANA, viewModel.thirdPhraseKana.value)
            putString(KEY_FOURTH_KANJI, viewModel.fourthPhraseKanji.value)
            putString(KEY_FOURTH_KANA, viewModel.fourthPhraseKana.value)
            putString(KEY_FIFTH_KANJI, viewModel.fifthPhraseKanji.value)
            putString(KEY_FIFTH_KANA, viewModel.fifthPhraseKana.value)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onClickUpdate() {
        viewModel.updateMaterial()
    }

    override fun onClickBack() {
    }

    interface OnFragmentInteractionListener {
        fun onError()
    }

    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): MaterialEditFragment
    }

    companion object {

        const val TAG: String = "MaterialEditFragment"

        private const val ARG_KARUTA_ID = "karutaId"

        private const val KEY_FIRST_KANJI = "firstKanji"
        private const val KEY_FIRST_KANA = "firstKana"
        private const val KEY_SECOND_KANJI = "secondKanji"
        private const val KEY_SECOND_KANA = "secondKana"
        private const val KEY_THIRD_KANJI = "thirdKanji"
        private const val KEY_THIRD_KANA = "thirdKana"
        private const val KEY_FOURTH_KANJI = "fourthKanji"
        private const val KEY_FOURTH_KANA = "fourthKana"
        private const val KEY_FIFTH_KANJI = "fifthKanji"
        private const val KEY_FIFTH_KANA = "fifthKana"

        fun newInstance(karutaId: KarutaIdentifier) = MaterialEditFragment().withArgs {
            putParcelable(ARG_KARUTA_ID, karutaId)
        }
    }
}
