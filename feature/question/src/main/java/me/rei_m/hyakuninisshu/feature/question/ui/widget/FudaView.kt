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
import androidx.core.content.withStyledAttributes
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.view.VerticalSingleLineTextView
import me.rei_m.hyakuninisshu.feature.question.R
import me.rei_m.hyakuninisshu.state.material.model.Material

class FudaView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : FrameLayout(context, attrs, defStyleAttr) {
        private val shokuView: VerticalSingleLineTextView
        private val nikuView: VerticalSingleLineTextView
        private val sankuView: VerticalSingleLineTextView
        private val shikuView: VerticalSingleLineTextView
        private val gokuView: VerticalSingleLineTextView
        private val creatorView: VerticalSingleLineTextView

        init {
            View.inflate(context, R.layout.fuda_view, this)

            val padding = resources.getDimensionPixelOffset(me.rei_m.hyakuninisshu.feature.corecomponent.R.dimen.spacing_1)
            setPadding(padding, padding, padding, padding)
            background = getDrawable(resources, R.drawable.bg_fuda, null)
            foregroundGravity = Gravity.CENTER

            shokuView = findViewById(R.id.text_fuda_shoku)
            nikuView = findViewById(R.id.text_fuda_niku)
            sankuView = findViewById(R.id.text_fuda_sanku)
            shikuView = findViewById(R.id.text_fuda_shiku)
            gokuView = findViewById(R.id.text_fuda_goku)
            creatorView = findViewById(R.id.text_fuda_creator)

            context.withStyledAttributes(attrs, R.styleable.FudaView) {
                val testSize = getResourceId(R.styleable.FudaView_textSize, me.rei_m.hyakuninisshu.feature.corecomponent.R.dimen.text_ll)
                shokuView.setTextSize(testSize)
                nikuView.setTextSize(testSize)
                sankuView.setTextSize(testSize)
                shikuView.setTextSize(testSize)
                gokuView.setTextSize(testSize)
                creatorView.setTextSize(me.rei_m.hyakuninisshu.feature.corecomponent.R.dimen.text_m)
            }
        }

        var material: Material? = null
            set(value) {
                field = value
                shokuView.drawText(material?.shokuKanji)
                nikuView.drawText(material?.nikuKanji)
                sankuView.drawText(material?.sankuKanji)
                shikuView.drawText(material?.shikuKanji)
                gokuView.drawText(material?.gokuKanji)
                creatorView.drawText(material?.creator)
            }
    }
