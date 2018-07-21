/*
 * Copyright (c) 2017. Rei Matsushita
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

package me.rei_m.hyakuninisshu.presentation.widget.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.ext.IntExt

class KarutaExamResultCellView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), IntExt {

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

    fun setResult(karutaNo: Int, isCorrect: Boolean) {
        textKarutaNo.text = karutaNo.toKarutaNoStr(context)
        if (isCorrect) {
            imageCorrect.visibility = View.VISIBLE
        } else {
            imageIncorrect.visibility = View.VISIBLE
        }
    }
}
