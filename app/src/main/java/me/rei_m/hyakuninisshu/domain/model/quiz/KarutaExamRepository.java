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

package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface KarutaExamRepository {

    Single<KarutaExamIdentifier> storeResult(@NonNull KarutaExamResult karutaExamResult, @NonNull Date tookExamDate);

    Completable adjustHistory(int historySize);

    Single<KarutaExam> findBy(@NonNull KarutaExamIdentifier identifier);

    Single<KarutaExams> list();
}
