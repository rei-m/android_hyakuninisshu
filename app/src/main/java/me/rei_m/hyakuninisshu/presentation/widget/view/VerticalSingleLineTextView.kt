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

package me.rei_m.hyakuninisshu.presentation.widget.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.DimenRes
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.View
import me.rei_m.hyakuninisshu.R

class VerticalSingleLineTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint()

    private var text: String = ""

    private var textSize: Int = 0

    init {
        initialize(context)
    }

    private fun initialize(context: Context) {
        val resources = context.resources
        text = ""
        textSize = context.resources.getDimensionPixelOffset(R.dimen.text_l)

        with(paint) {
            isAntiAlias = true
            textSize = this@VerticalSingleLineTextView.textSize.toFloat()
            color = ResourcesCompat.getColor(resources, R.color.black8a, null)
            typeface = KarutaFontHolder.getTypeFace(context)
        }
    }

    fun setTextSize(@DimenRes dimenId: Int) {
        textSize = context.resources.getDimensionPixelOffset(dimenId)
        paint.textSize = textSize.toFloat()
    }

    fun setTextSizeByPx(textSize: Int) {
        this.textSize = textSize
        paint.textSize = textSize.toFloat()
    }

    fun drawText(text: String?) {
        if (text != null) {
            this.text = text
        }
        val layoutParams = layoutParams
        layoutParams.width = textSize
        layoutParams.height = (textSize.toDouble() * this.text.length.toDouble() * 1.05).toInt()
        setLayoutParams(layoutParams)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val textLength = text.length
        for (i in 0 until textLength) {
            canvas.drawText(text.substring(i, i + 1), 0f, (textSize * (i + 1)).toFloat(), paint)
        }
    }
}
