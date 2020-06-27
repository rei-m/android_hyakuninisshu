/*
 * Copyright (c) 2020. Rei Matsushita
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

package me.rei_m.hyakuninisshu.state.exam.action

import me.rei_m.hyakuninisshu.state.core.Action

/**
 * 力試しを開始するアクション.
 */
sealed class StartExamAction(override val error: Throwable? = null) : Action {

    class Success(val questionId: String) : StartExamAction() {
        override fun toString() = "$name(questionId=$questionId)"
    }

    class Failure(e: Throwable) : StartExamAction(e) {
        override fun toString() = "$name(error=$error)"
    }

    override val name = "StartExamAction"
}
