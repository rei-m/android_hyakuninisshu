/*
 * Copyright (c) 2025. Rei Matsushita
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
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat.getDrawable
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.view.VerticalSingleLineTextView
import me.rei_m.hyakuninisshu.feature.question.R
import me.rei_m.hyakuninisshu.state.question.model.ToriFuda

class ToriFudaView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : FrameLayout(context, attrs, defStyleAttr) {
        private val firstLineView: VerticalSingleLineTextView
        private val secondLineView: VerticalSingleLineTextView

        init {
            View.inflate(context, R.layout.torifuda_view, this)

            val padding = resources.getDimensionPixelOffset(me.rei_m.hyakuninisshu.feature.corecomponent.R.dimen.spacing_0_5)
            setPadding(padding, padding, padding, padding)
            background = getDrawable(resources, R.drawable.bg_fuda, null)
            foregroundGravity = Gravity.CENTER

            firstLineView = findViewById(R.id.text_torifuda_1)
            secondLineView = findViewById(R.id.text_torifuda_2)

            val pad = context.getString(R.string.torifuda_pad)
            firstLineView.drawText(pad)
            secondLineView.drawText(pad)
        }

        var textSizeByPx: Int? = null
            set(value) {
                field = value
                value ?: return
                firstLineView.setTextSizeByPx(value)
                secondLineView.setTextSizeByPx(value)
            }

        var toriFuda: ToriFuda? = null
            set(value) {
                field = value
                value ?: return
                firstLineView.drawText(value.firstLine)
                secondLineView.drawText(value.secondLine)
            }
    }
