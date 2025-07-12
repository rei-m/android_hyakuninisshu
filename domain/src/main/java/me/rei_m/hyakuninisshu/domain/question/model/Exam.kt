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

import me.rei_m.hyakuninisshu.domain.AbstractEntity
import java.util.Date

/**
 * 百人一首の力試し.
 *
 * @param id ID
 * @param tookDate 受験日
 * @param result 力試し結果
 */
class Exam(
    id: ExamId,
    val tookDate: Date,
    val result: ExamResult,
) : AbstractEntity<ExamId>(id) {
    override fun toString() = "Exam(id=$identifier, tookDate=$tookDate, result=$result)"
}
