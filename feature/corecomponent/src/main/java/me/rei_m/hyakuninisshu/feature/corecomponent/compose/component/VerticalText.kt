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

package me.rei_m.hyakuninisshu.feature.corecomponent.compose.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.rei_m.hyakuninisshu.feature.corecomponent.compose.theme.Blackba
import me.rei_m.hyakuninisshu.feature.corecomponent.compose.theme.HannariFontFamily
import me.rei_m.hyakuninisshu.feature.corecomponent.compose.theme.HyakuninisshuTheme

@Composable
fun VerticalText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    fontFamily: FontFamily = HannariFontFamily,
    style: TextStyle = TextStyle.Default
) {
    val density = LocalDensity.current
    val textPaint = android.graphics.Paint().apply {
        isAntiAlias = true
        textSize = with(density) { fontSize.toPx() }
        color = style.color.hashCode()
        typeface = android.graphics.Typeface.DEFAULT
    }
    
    val textSizeDp: Dp = with(density) { fontSize.toDp() }
    val height: Dp = textSizeDp * text.length * 1.05f
    
    Canvas(
        modifier = modifier.size(width = textSizeDp, height = height)
    ) {
        drawIntoCanvas { canvas ->
            text.forEachIndexed { index, char ->
                canvas.nativeCanvas.drawText(
                    char.toString(),
                    0f,
                    textPaint.textSize * (index + 1),
                    textPaint
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerticalTextPreview() {
    HyakuninisshuTheme {
        VerticalText(
            text = "百人一首",
            fontSize = 24.sp,
            style = TextStyle(color = Blackba)
        )
    }
}