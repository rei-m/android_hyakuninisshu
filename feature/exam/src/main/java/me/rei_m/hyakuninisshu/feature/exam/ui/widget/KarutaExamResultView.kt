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
package me.rei_m.hyakuninisshu.feature.exam.ui.widget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizJudgement
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.Event
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.view.ViewIdHolder

class KarutaExamResultView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        initialize(context)
    }

    private val _onClickKarutaEvent = MutableLiveData<Event<KarutaIdentifier>>()
    val onClickKarutaEvent: LiveData<Event<KarutaIdentifier>> = _onClickKarutaEvent

    private fun initialize(context: Context) {
        orientation = LinearLayout.VERTICAL
        for (i in 0 until NUMBER_OF_KARUTA_ROW) {
            addView(createRow(context, i))
        }
    }

    private fun createRow(context: Context, rowIndex: Int): LinearLayout {

        val linearLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        val layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)

        val currentRowTopIndex = rowIndex * NUMBER_OF_KARUTA_PER_ROW

        for (i in 0 until NUMBER_OF_KARUTA_PER_ROW) {
            val totalIndex = currentRowTopIndex + i
            val cellView = KarutaExamResultCellView(context).apply {
                id = cellViewIdList[totalIndex]
                gravity = Gravity.CENTER
            }
            linearLayout.addView(cellView, layoutParams)
        }

        return linearLayout
    }

    fun setResult(judgements: List<KarutaQuizJudgement>) {
        judgements.forEachIndexed { index, karutaQuizJudgement ->
            val (karutaId, isCorrect) = karutaQuizJudgement
            with(findViewById<KarutaExamResultCellView>(cellViewIdList[index])) {
                setResult(karutaId.value, isCorrect)
                setOnClickListener {
                    _onClickKarutaEvent.value =
                        Event(karutaId)
                }
            }
        }
    }

    companion object {

        private const val NUMBER_OF_KARUTA_PER_ROW = 5

        private const val NUMBER_OF_KARUTA_ROW = Karuta.NUMBER_OF_KARUTA / NUMBER_OF_KARUTA_PER_ROW

        private val cellViewIdList = IntArray(Karuta.NUMBER_OF_KARUTA)

        init {
            for (i in 0 until Karuta.NUMBER_OF_KARUTA) {
                val viewId: Int = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    View.generateViewId()
                } else {
                    ViewIdHolder.generateViewId()
                }
                cellViewIdList[i] = viewId
            }
        }
    }
}
