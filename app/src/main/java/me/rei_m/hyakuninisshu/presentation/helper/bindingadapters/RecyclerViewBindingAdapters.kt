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

package me.rei_m.hyakuninisshu.presentation.helper.bindingadapters

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam
import me.rei_m.hyakuninisshu.presentation.entrance.MaterialListAdapter
import me.rei_m.hyakuninisshu.presentation.examhistory.KarutaExamListAdapter

@BindingAdapter("materials")
fun setMaterial(view: RecyclerView, karutaList: List<Karuta>?) {
    karutaList ?: return
    with(view.adapter as MaterialListAdapter) {
        replaceData(karutaList)
    }
}

@BindingAdapter("karutaExamList")
fun setKarutaExamList(view: RecyclerView, karutaExamList: List<KarutaExam>?) {
    karutaExamList ?: return
    with(view.adapter as KarutaExamListAdapter) {
        replaceData(karutaExamList)
    }
}
