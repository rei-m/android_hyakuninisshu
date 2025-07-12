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

package me.rei_m.hyakuninisshu.domain.question.model

import me.rei_m.hyakuninisshu.domain.ValueObject
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo

/**
 * 問題の回答結果.
 *
 * @param selectedKarutaNo 選択した歌の番号
 * @param answerMillSec 回答にかかった時間（ミリ秒）
 * @param judgement 判定結果
 */
data class QuestionResult(
    val selectedKarutaNo: KarutaNo,
    val answerMillSec: Long,
    val judgement: QuestionJudgement,
) : ValueObject
