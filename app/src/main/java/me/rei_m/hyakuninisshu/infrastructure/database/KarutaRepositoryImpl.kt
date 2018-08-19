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

package me.rei_m.hyakuninisshu.infrastructure.database

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Single
import me.rei_m.hyakuninisshu.domain.model.karuta.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class KarutaRepositoryImpl(private val context: Context,
                           private val preferences: SharedPreferences,
                           private val orma: OrmaDatabase) : KarutaRepository {

    override fun initialize(): Completable {

        val karutaJsonVersion = preferences.getInt(KarutaJsonConstant.KEY_KARUTA_JSON_VERSION, 0)

        if (karutaJsonVersion < KarutaJsonConstant.KARUTA_VERSION) {
            try {
                val inputStream = context.assets.open("karuta_list.json")
                val reader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    stringBuilder.append(line)
                }
                reader.close()

                val karutaSchemaList = KarutaJsonAdaptor.convert(stringBuilder.toString())

                return orma.transactionAsCompletable {

                    karutaSchemaList.forEach {
                        val count = KarutaSchema.relation(orma).selector().idEq(it.id)
                                .and()
                                .where("isEdited = ?", true).count()
                        if (count == 0) {
                            KarutaSchema.relation(orma).upsert(it)
                        }
                    }

                    preferences.edit()
                            .putInt(KarutaJsonConstant.KEY_KARUTA_JSON_VERSION, KarutaJsonConstant.KARUTA_VERSION)
                            .apply()
                }
            } catch (e: IOException) {
                return Completable.error(e)
            }

        } else {
            return Completable.complete()
        }
    }

    override fun list(): Single<Karutas> {
        val relation = KarutaSchema.relation(orma)
        return relation.selector()
                .orderBy(relation.schema.id.orderInAscending())
                .executeAsObservable()
                .map { KarutaFactory.create(it) }
                .toList()
                .map { Karutas(it) }
    }

    override fun list(color: Color?): Single<Karutas> {
        val relation = KarutaSchema.relation(orma)
        var selector = relation.selector()
        if (color != null) {
            selector = selector.and().where("color = ?", color.value)
        }
        return selector
                .orderBy(relation.schema.id.orderInAscending())
                .executeAsObservable()
                .map { KarutaFactory.create(it) }
                .toList()
                .map { Karutas(it) }
    }

    override fun findIds(fromIdentifier: KarutaIdentifier,
                         toIdentifier: KarutaIdentifier,
                         color: Color?,
                         kimariji: Kimariji?): Single<KarutaIds> {
        val relation = KarutaSchema.relation(orma)
        var selector = relation.selector()
                .idGe(fromIdentifier.value.toLong())
                .and()
                .idLe(toIdentifier.value.toLong())

        if (color != null) {
            selector = selector.and().where("color = ?", color.value)
        }

        if (kimariji != null) {
            selector = selector.and().where("kimariji = ?", kimariji.value)
        }

        return selector
                .orderBy(relation.schema.id.orderInAscending())
                .executeAsObservable()
                .map { karutaSchema -> KarutaIdentifier(karutaSchema.id.toInt()) }
                .toList()
                .map { KarutaIds(it) }
    }

    override fun findIds(): Single<KarutaIds> {
        val relation = KarutaSchema.relation(orma)
        return KarutaSchema.relation(orma).selector()
                .orderBy(relation.schema.id.orderInAscending())
                .executeAsObservable()
                .map { karutaSchema -> KarutaIdentifier(karutaSchema.id.toInt()) }
                .toList()
                .map { KarutaIds(it) }
    }

    override fun findBy(identifier: KarutaIdentifier): Single<Karuta> {
        return KarutaSchema.relation(orma).selector()
                .idEq(identifier.value.toLong())
                .executeAsObservable()
                .map { KarutaFactory.create(it) }
                .singleOrError()
    }

    override fun findBy2(karutaId: KarutaIdentifier): Karuta? {
        return KarutaSchema.relation(orma).selector()
            .idEq(karutaId.value.toLong())
            .firstOrNull()?.let {
                KarutaFactory.create(it)
            }
    }

    override fun store(karuta: Karuta): Completable {

        val kamiNoKu = karuta.kamiNoKu
        val shimoNoKu = karuta.shimoNoKu

        return KarutaSchema.relation(orma).updater()
                .idEq(karuta.identifier().value.toLong())
                .firstKana(kamiNoKu.first.kana)
                .firstKanji(kamiNoKu.first.kanji)
                .secondKana(kamiNoKu.second.kana)
                .secondKanji(kamiNoKu.second.kanji)
                .thirdKana(kamiNoKu.third.kana)
                .thirdKanji(kamiNoKu.third.kanji)
                .fourthKana(shimoNoKu.fourth.kana)
                .fourthKanji(shimoNoKu.fourth.kanji)
                .fifthKana(shimoNoKu.fifth.kana)
                .fifthKanji(shimoNoKu.fifth.kanji)
                .isEdited(true)
                .executeAsSingle()
                .ignoreElement()
    }
}
