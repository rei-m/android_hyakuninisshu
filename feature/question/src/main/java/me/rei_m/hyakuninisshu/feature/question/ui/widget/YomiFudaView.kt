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
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout
import androidx.core.content.res.ResourcesCompat.getDrawable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import me.rei_m.hyakuninisshu.feature.question.R
import me.rei_m.hyakuninisshu.state.question.model.YomiFuda
import java.util.concurrent.TimeUnit

class YomiFudaView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : RelativeLayout(context, attrs, defStyleAttr) {
        private val firstLineWordList: List<YomiFudaWordView>
        private val secondLineWordList: List<YomiFudaWordView>
        private val thirdLineWordList: List<YomiFudaWordView>

        private var animationDisposable: Disposable? = null

        init {
            View.inflate(context, R.layout.yomifuda_view, this)
            val padding = resources.getDimensionPixelOffset(me.rei_m.hyakuninisshu.feature.corecomponent.R.dimen.spacing_1)
            setPadding(padding, padding, padding, padding)
            background = getDrawable(resources, R.drawable.bg_fuda, null)

            firstLineWordList =
                listOf(
                    findViewById(R.id.phrase_1_1),
                    findViewById(R.id.phrase_1_2),
                    findViewById(R.id.phrase_1_3),
                    findViewById(R.id.phrase_1_4),
                    findViewById(R.id.phrase_1_5),
                    findViewById(R.id.phrase_1_6),
                )
            secondLineWordList =
                listOf(
                    findViewById(R.id.phrase_2_1),
                    findViewById(R.id.phrase_2_2),
                    findViewById(R.id.phrase_2_3),
                    findViewById(R.id.phrase_2_4),
                    findViewById(R.id.phrase_2_5),
                    findViewById(R.id.phrase_2_6),
                    findViewById(R.id.phrase_2_7),
                    findViewById(R.id.phrase_2_8),
                )
            thirdLineWordList =
                listOf(
                    findViewById(R.id.phrase_3_1),
                    findViewById(R.id.phrase_3_2),
                    findViewById(R.id.phrase_3_3),
                    findViewById(R.id.phrase_3_4),
                    findViewById(R.id.phrase_3_5),
                    findViewById(R.id.phrase_3_6),
                )
        }

        var textSizeByPx: Int? = null
            set(value) {
                field = value
                value ?: return
                firstLineWordList.forEach { it.textSizeByPx = value }
                secondLineWordList.forEach { it.textSizeByPx = value }
                thirdLineWordList.forEach { it.textSizeByPx = value }
            }

        fun startDisplayWithAnimation(
            yomiFuda: YomiFuda,
            speed: Long?,
        ) {
            if (animationDisposable != null) {
                return
            }

            firstLineWordList.forEach { it.setText(yomiFuda.firstLine) }
            secondLineWordList.forEach { it.setText(yomiFuda.secondLine) }
            thirdLineWordList.forEach { it.setText(yomiFuda.thirdLine) }

            speed ?: return

            firstLineWordList.forEach { it.visibility = View.INVISIBLE }
            secondLineWordList.forEach { it.visibility = View.INVISIBLE }
            thirdLineWordList.forEach { it.visibility = View.INVISIBLE }

            val totalWordList =
                ArrayList<YomiFudaWordView>().apply {
                    addAll(firstLineWordList.take(yomiFuda.firstLine.length))
                    addAll(secondLineWordList.take(yomiFuda.secondLine.length))
                    addAll(thirdLineWordList.take(yomiFuda.thirdLine.length))
                }

            animationDisposable =
                Observable
                    .interval(speed, TimeUnit.MILLISECONDS)
                    .zipWith(totalWordList) { _, wordView -> wordView }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnDispose {
                        totalWordList.forEach { it.animation?.cancel() }
                    }.subscribe { textView ->
                        val fadeIn =
                            AlphaAnimation(0f, 1f).apply {
                                interpolator = DecelerateInterpolator()
                                duration = speed
                            }
                        textView.visibility = View.VISIBLE
                        textView.startAnimation(fadeIn)
                    }
        }

        fun stopAnimation() {
            animationDisposable?.dispose()
            animationDisposable = null
        }
    }
