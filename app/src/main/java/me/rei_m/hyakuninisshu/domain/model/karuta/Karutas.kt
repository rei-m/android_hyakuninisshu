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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.domain.model.karuta

import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzes
import me.rei_m.hyakuninisshu.domain.util.generateRandomIndexArray
import java.util.Collections

/**
 * 全ての歌のコレクション.
 */
class Karutas(private val values: List<Karuta>) {

    private val ids: List<KarutaIdentifier> = values.map { it.identifier }

    /**
     * 指定の歌を取得する.
     *
     * @param karutaId 歌ID
     * @return 歌
     */
    operator fun get(karutaId: KarutaIdentifier): Karuta = values[ids.indexOf(karutaId)]

    /**
     * @return 歌のリスト
     */
    fun asList(): List<Karuta> = values

    /**
     * 保持している歌を色で絞り込む.
     *
     * @param color 歌の色
     * @return 歌のリスト
     */
    fun asList(color: Color?): List<Karuta> = if (color == null) {
        asList()
    } else {
        values.filter { it.color == color }
    }

    /**
     * 指定された歌のIDを元に問題を作成する.
     *
     * @param karutaIds 歌IDコレクション。渡されたIDが正解となる問題を作成する。
     * @return 問題のコレクション
     */
    fun createQuizSet(karutaIds: KarutaIds): KarutaQuizzes {
        val quizzes = karutaIds.asRandomized.map {
            val dupIds = ArrayList(this.ids).apply { remove(it) }

            val choices = generateRandomIndexArray(dupIds.size, ChoiceNo.values().size - 1).map { choiceIndex ->
                dupIds[choiceIndex]
            }.toMutableList()

            val correctPosition = generateRandomIndexArray(ChoiceNo.values().size, 1)[0]
            choices.add(correctPosition, it)

            KarutaQuiz.createReady(KarutaQuizIdentifier(), choices, it)
        }

        return KarutaQuizzes(quizzes)
    }

    override fun equals(other: Any?): Boolean {
        other as? Karutas ?: return false
        return (values == other.values)
    }

    override fun hashCode(): Int = values.hashCode()

    override fun toString(): String = "Karutas(values=$values)"
}
