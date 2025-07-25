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

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import me.rei_m.hyakuninisshu.feature.corecomponent.R
import me.rei_m.hyakuninisshu.feature.corecomponent.compose.theme.HyakuninisshuTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun KarutaImage(
    @DrawableRes resId: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    GlideImage(
        model = resId,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Fit,
        loading = placeholder(R.drawable.tatami_part)
    )
}

@Preview(showBackground = true)
@Composable
fun KarutaImagePreview() {
    HyakuninisshuTheme {
        KarutaImage(
            resId = R.drawable.tatami_part,
            contentDescription = "Karuta card",
            modifier = Modifier.fillMaxSize()
        )
    }
}