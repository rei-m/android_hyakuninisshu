/*
 * Copyright (c) 2020. Rei Matsushita
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

package me.rei_m.hyakuninisshu.feature.question.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import me.rei_m.hyakuninisshu.feature.question.R

class YomiFudaWordView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    private var position: Int? = null

    init {
        typeface = ResourcesCompat.getFont(context, R.font.hannari)
        context.withStyledAttributes(attrs, R.styleable.YomiFudaWordView) {
            position = getInt(R.styleable.YomiFudaWordView_position, 0)
        }
    }

    var textSizeByPx: Int? = null
        set(value) {
            field = value
            value ?: return
            setTextSize(TypedValue.COMPLEX_UNIT_PX, value.toFloat())
        }

    fun setText(text: String?) {
        text ?: return
        val textPosition = position ?: return
        if (text.length < textPosition) {
            return
        }
        this.text = text.substring(textPosition - 1, textPosition)
    }
}
