/*
 * Copyright (c) 2020. Rei Matsushita.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package me.rei_m.hyakuninisshu.feature.examresult.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import me.rei_m.hyakuninisshu.feature.examresult.R
import me.rei_m.hyakuninisshu.state.question.model.QuestionResult

class ExamResultView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        initialize(context)
    }

    var questionResultList: List<QuestionResult>? = null
        set(value) {
            field = value
            questionResultList?.forEachIndexed { index, questionResult ->
                with(findViewById<CellView>(cellViewIdList[index])) {
                    setResult(questionResult.karutaNoText, questionResult.isCorrect)
                    setOnClickListener {
                        listener?.onClick(questionResult.karutaNo)
                    }
                }
            }
        }

    var listener: OnClickItemListener? = null

    interface OnClickItemListener {
        fun onClick(karutaNo: Int)
    }

    private fun initialize(context: Context) {
        orientation = VERTICAL
        for (i in 0 until NUMBER_OF_KARUTA_ROW) {
            addView(createRow(context, i))
        }
    }

    private fun createRow(context: Context, rowIndex: Int): LinearLayout {

        val linearLayout = LinearLayout(context).apply {
            orientation = HORIZONTAL
        }

        val layoutParams = LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)

        val currentRowTopIndex = rowIndex * NUMBER_OF_KARUTA_PER_ROW

        for (i in 0 until NUMBER_OF_KARUTA_PER_ROW) {
            val totalIndex = currentRowTopIndex + i
            val cellView = CellView(context).apply {
                id = cellViewIdList[totalIndex]
                gravity = Gravity.CENTER
            }
            linearLayout.addView(cellView, layoutParams)
        }

        return linearLayout
    }

    inner class CellView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : RelativeLayout(context, attrs, defStyleAttr) {

        init {
            initialize(context)
        }

        private lateinit var textKarutaNo: TextView

        private lateinit var imageCorrect: ImageView

        private lateinit var imageIncorrect: ImageView

        private fun initialize(context: Context) {
            val view = View.inflate(context, R.layout.view_karuta_exam_result_cell, this)
            textKarutaNo = view.findViewById(R.id.text_karuta_no)
            imageCorrect = view.findViewById(R.id.image_quiz_result_correct)
            imageIncorrect = view.findViewById(R.id.image_quiz_result_incorrect)
        }

        fun setResult(karutaNoText: String, isCorrect: Boolean) {
            textKarutaNo.text = karutaNoText
            if (isCorrect) {
                imageCorrect.visibility = View.VISIBLE
            } else {
                imageIncorrect.visibility = View.VISIBLE
            }
        }
    }

    companion object {

        private const val NUMBER_OF_KARUTA_PER_ROW = 5
        private const val NUMBER_OF_CELL = 100
        private const val NUMBER_OF_KARUTA_ROW = NUMBER_OF_CELL / NUMBER_OF_KARUTA_PER_ROW

        private val cellViewIdList = IntArray(NUMBER_OF_CELL)

        init {
            for (i in 0 until NUMBER_OF_CELL) {
                cellViewIdList[i] = View.generateViewId()
            }
        }
    }
}
