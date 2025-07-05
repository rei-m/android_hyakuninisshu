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

package me.rei_m.hyakuninisshu.state.core.ext

import android.content.Context
import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.domain.question.model.Exam
import me.rei_m.hyakuninisshu.state.R
import me.rei_m.hyakuninisshu.state.exam.model.ExamResult
import me.rei_m.hyakuninisshu.state.material.model.Material
import me.rei_m.hyakuninisshu.state.question.model.QuestionResult
import me.rei_m.hyakuninisshu.state.question.model.ToriFuda
import me.rei_m.hyakuninisshu.state.question.model.YomiFuda
import me.rei_m.hyakuninisshu.state.training.model.DisplayStyleCondition
import java.util.Date
import java.util.Locale

fun KarutaNo.toText(context: Context): String {
    if (this.value == 100) {
        return context.getString(R.string.karuta_number, context.getString(R.string.hundred))
    }

    val resources = context.resources
    val numArray = resources.getStringArray(R.array.number)

    val doubleDigits = this.value / 10
    val singleDigits = this.value % 10

    val sb = StringBuilder()
    if (0 < doubleDigits) {
        if (1 < doubleDigits) {
            sb.append(numArray[doubleDigits - 1])
        }
        sb.append(resources.getString(R.string.ten))
    }

    if (0 < singleDigits) {
        sb.append(numArray[singleDigits - 1])
    }

    return context.getString(R.string.karuta_number, sb.toString())
}

fun Karuta.toYomiFuda(displayStyleCondition: DisplayStyleCondition): YomiFuda =
    when (displayStyleCondition) {
        DisplayStyleCondition.KANA -> {
            YomiFuda(
                karutaNo = no.value,
                firstLine = kamiNoKu.shoku.kana,
                secondLine = kamiNoKu.niku.kana,
                thirdLine = kamiNoKu.sanku.kana,
            )
        }
        DisplayStyleCondition.KANJI -> {
            YomiFuda(
                karutaNo = no.value,
                firstLine = kamiNoKu.shoku.kanji,
                secondLine = kamiNoKu.niku.kanji,
                thirdLine = kamiNoKu.sanku.kanji,
            )
        }
    }

fun Karuta.toToriFuda(displayStyleCondition: DisplayStyleCondition): ToriFuda =
    when (displayStyleCondition) {
        DisplayStyleCondition.KANA -> {
            ToriFuda(
                karutaNo = no.value,
                firstLine = shimoNoKu.shiku.kana.padEnd(8, '　'),
                secondLine = shimoNoKu.goku.kana.padEnd(8, '　'),
            )
        }
        DisplayStyleCondition.KANJI -> {
            ToriFuda(
                karutaNo = no.value,
                firstLine = shimoNoKu.shiku.kanji.padEnd(8, '　'),
                secondLine = shimoNoKu.goku.kanji.padEnd(8, '　'),
            )
        }
    }

fun Karuta.toMaterial(context: Context): Material {
    val resources = context.resources

    val kimarijiArray = resources.getStringArray(R.array.kimariji)
    val kimarijiTxt = kimarijiArray[kimariji.value - 1]

    val typedArray = resources.obtainTypedArray(R.array.karuta_image)
    val imageResId = typedArray.getResourceId(no.value - 1, 0)
    typedArray.recycle()

    return Material(
        no = no.value,
        noTxt = no.toText(context),
        kimariji = kimariji.value,
        kimarijiTxt = kimarijiTxt,
        creator = creator,
        shokuKanji = kamiNoKu.shoku.kanji,
        shokuKana = kamiNoKu.shoku.kana,
        nikuKanji = kamiNoKu.niku.kanji,
        nikuKana = kamiNoKu.niku.kana,
        sankuKanji = kamiNoKu.sanku.kanji,
        sankuKana = kamiNoKu.sanku.kana,
        shikuKanji = shimoNoKu.shiku.kanji,
        shikuKana = shimoNoKu.shiku.kana,
        gokuKanji = shimoNoKu.goku.kanji,
        gokuKana = shimoNoKu.goku.kana,
        translation = translation,
        imageResId = imageResId,
    )
}

fun Exam.toResult(
    context: Context,
    now: Date,
): ExamResult {
    val averageAnswerTimeString =
        String.format(
            Locale.JAPAN,
            "%.2f",
            result.resultSummary.averageAnswerSec,
        )

    return ExamResult(
        id = identifier.value,
        score = result.resultSummary.score,
        averageAnswerSecText = context.getString(R.string.seconds, averageAnswerTimeString),
        questionResultList =
            KarutaNo.LIST.map { karutaNo ->
                QuestionResult(
                    karutaNo = karutaNo.value,
                    karutaNoText = karutaNo.toText(context),
                    isCorrect = !result.wrongKarutaNoCollection.contains(karutaNo),
                )
            },
        fromNowText = tookDate.diffString(context, now),
    )
}
