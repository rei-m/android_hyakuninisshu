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

import android.arch.lifecycle.Observer
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
import me.rei_m.hyakuninisshu.ext.FragmentExt
import javax.inject.Inject

class MaterialEditFragment : DaggerFragment(),
        ConfirmMaterialEditDialogFragment.OnDialogInteractionListener,
        FragmentExt {

    @Inject
    lateinit var storeFactory: MaterialEditStore.Factory

    @Inject
    lateinit var viewModelFactory: MaterialEditViewModel.Factory

    lateinit var binding: FragmentMaterialEditBinding

    lateinit var viewModel: MaterialEditViewModel

    private var listener: OnFragmentInteractionListener? = null

    private val karutaId: KarutaIdentifier
        get() = requireNotNull(arguments?.getParcelable(ARG_KARUTA_ID)) {
            "$ARG_KARUTA_ID is missing"
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

        viewModel = viewModelFactory.create(obtainActivityStore(MaterialEditStore::class.java, storeFactory), karutaId)
        with(viewModel) {
            confirmEditEvent.observe(this@MaterialEditFragment, Observer { dialog ->
                dialog ?: return@Observer
                fragmentManager?.let {
                    dialog.setTargetFragment(this@MaterialEditFragment, 0)
                    dialog.show(it, ConfirmMaterialEditDialogFragment.TAG)
                }
            })
            snackBarMessage.observe(this@MaterialEditFragment, Observer {
                if (it == null) return@Observer
                Snackbar.make(binding.root, getString(it), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null)
                        .show()
            })
            unhandledErrorEvent.observe(this@MaterialEditFragment, Observer {
                listener?.onError()
            })
        }

        binding = FragmentMaterialEditBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@MaterialEditFragment)
        }

        binding.viewModel = viewModel

        return binding.root
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
        @ForFragment
        @ContributesAndroidInjector
        abstract fun contributeInjector(): MaterialEditFragment
    }

    companion object : FragmentExt {

        val TAG: String = MaterialEditFragment::class.java.simpleName

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
