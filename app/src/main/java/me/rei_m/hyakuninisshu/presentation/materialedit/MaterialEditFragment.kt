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

package me.rei_m.hyakuninisshu.presentation.materialedit

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.databinding.FragmentMaterialEditBinding
import me.rei_m.hyakuninisshu.di.ForFragment
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.ext.withArgs
import me.rei_m.hyakuninisshu.util.AnalyticsHelper
import me.rei_m.hyakuninisshu.util.EventObserver
import javax.inject.Inject

class MaterialEditFragment : DaggerFragment(),
    ConfirmMaterialEditDialogFragment.OnDialogInteractionListener {

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    @Inject
    lateinit var viewModelFactory: MaterialEditViewModel.Factory

    private lateinit var binding: FragmentMaterialEditBinding

    private lateinit var materialEditViewModel: MaterialEditViewModel

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

        materialEditViewModel = viewModelFactory.create(requireActivity(), karutaId)

        binding = FragmentMaterialEditBinding.inflate(inflater, container, false).apply {
            viewModel = materialEditViewModel
            setLifecycleOwner(this@MaterialEditFragment)
        }

        materialEditViewModel.confirmEditEvent.observe(this, EventObserver { dialog ->
            fragmentManager?.let {
                dialog.setTargetFragment(this, 0)
                dialog.show(it, ConfirmMaterialEditDialogFragment.TAG)
            }
        })
        materialEditViewModel.snackBarMessage.observe(this, EventObserver {
            Snackbar.make(binding.root, getString(it), Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
                .show()
        })
        materialEditViewModel.unhandledErrorEvent.observe(this, EventObserver {
            listener?.onError()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("MaterialEdit-${karutaId.value}", requireActivity())
    }

    override fun onDestroyView() {
        materialEditViewModel.onDestroyView()
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
            putString(KEY_FIRST_KANJI, materialEditViewModel.firstPhraseKanji.value)
            putString(KEY_FIRST_KANA, materialEditViewModel.firstPhraseKana.value)
            putString(KEY_SECOND_KANJI, materialEditViewModel.secondPhraseKanji.value)
            putString(KEY_SECOND_KANA, materialEditViewModel.secondPhraseKana.value)
            putString(KEY_THIRD_KANJI, materialEditViewModel.thirdPhraseKanji.value)
            putString(KEY_THIRD_KANA, materialEditViewModel.thirdPhraseKana.value)
            putString(KEY_FOURTH_KANJI, materialEditViewModel.fourthPhraseKanji.value)
            putString(KEY_FOURTH_KANA, materialEditViewModel.fourthPhraseKana.value)
            putString(KEY_FIFTH_KANJI, materialEditViewModel.fifthPhraseKanji.value)
            putString(KEY_FIFTH_KANA, materialEditViewModel.fifthPhraseKana.value)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onClickUpdate() {
        materialEditViewModel.updateMaterial()
    }

    override fun onClickBack() {

    }

    interface OnFragmentInteractionListener {
        fun onError()
    }

    @dagger.Module
    abstract class Module {
        @ForFragment
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
