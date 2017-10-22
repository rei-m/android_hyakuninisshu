/*
 * Copyright (c) 2017. Rei Matsushita
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

package me.rei_m.hyakuninisshu.infrastructure.database;

import android.support.annotation.Nullable;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import java.util.Date;

@Table
public class KarutaQuizSchema {

    public static KarutaQuizSchema_Relation relation(OrmaDatabase orma) {
        return orma.relationOfKarutaQuizSchema();
    }

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column(indexed = true)
    public String quizId;

    @Column
    public long collectId;

    @Column
    @Nullable
    public Date startDate;

    @Column
    @Nullable
    public int choiceNo;

    @Column
    public boolean isCollect;

    @Column
    public long answerTime;

    public KarutaQuizChoiceSchema_Relation getChoices(OrmaDatabase orma) {
        return orma.relationOfKarutaQuizChoiceSchema()
                .karutaQuizSchemaEq(this)
                .orderByOrderNoAsc();
    }
}
