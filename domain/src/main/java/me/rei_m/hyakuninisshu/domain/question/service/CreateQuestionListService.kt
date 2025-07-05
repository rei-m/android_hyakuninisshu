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

package me.rei_m.hyakuninisshu.domain.question.service

import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNoCollection
import me.rei_m.hyakuninisshu.domain.question.model.Question
import me.rei_m.hyakuninisshu.domain.question.model.QuestionId
import me.rei_m.hyakuninisshu.domain.util.generateRandomIndexArray

/**
 * 問題作成サービス.
 */
class CreateQuestionListService {
    /**
     * @param allKarutaNoCollection 百人一首全ての歌の番号コレクション
     * @param targetKarutaNoCollection 問題の対象となる歌番号コレクション
     * @param choiceSize 選択肢の数
     *
     * @return 問題リスト
     */
    @Throws(IllegalArgumentException::class)
    operator fun invoke(
        allKarutaNoCollection: KarutaNoCollection,
        targetKarutaNoCollection: KarutaNoCollection,
        choiceSize: Int,
    ): List<Question> {
        if (allKarutaNoCollection.size != KarutaNo.MAX.value) {
            throw IllegalArgumentException("allKarutaList is invalid")
        }

        if (targetKarutaNoCollection.size == 0) {
            throw IllegalArgumentException("targetKarutaNoList is empty")
        }

        return targetKarutaNoCollection.asRandomized.mapIndexed { index, targetKarutaNo ->

            val dupNos =
                allKarutaNoCollection.values.toMutableList().apply {
                    remove(targetKarutaNo)
                }

            val choices =
                generateRandomIndexArray(
                    dupNos.size,
                    choiceSize - 1,
                ).map { choiceIndex ->
                    dupNos[choiceIndex]
                }.toMutableList()

            val correctPosition = generateRandomIndexArray(choiceSize, 1)[0]
            choices.add(correctPosition, targetKarutaNo)

            Question(QuestionId(), index + 1, choices, targetKarutaNo, Question.State.Ready)
        }
    }
}
