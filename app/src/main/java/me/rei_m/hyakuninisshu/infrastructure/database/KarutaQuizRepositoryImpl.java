package me.rei_m.hyakuninisshu.infrastructure.database;

import android.support.annotation.NonNull;

import com.github.gfx.android.orma.Inserter;
import com.github.gfx.android.orma.SingleAssociation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizPosition;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzes;

public class KarutaQuizRepositoryImpl implements KarutaQuizRepository {

    private final OrmaDatabase orma;

    public KarutaQuizRepositoryImpl(@NonNull OrmaDatabase orma) {
        this.orma = orma;
    }

    @Override
    public Completable initialize(List<KarutaQuiz> karutaQuizList) {
        return orma.transactionAsCompletable(() -> {

            KarutaQuizSchema.relation(orma).deleter().execute();
            KarutaQuizChoiceSchema.relation(orma).deleter().execute();

            Inserter<KarutaQuizSchema> karutaQuizSchemaInserter = KarutaQuizSchema.relation(orma).inserter();
            Inserter<KarutaQuizChoiceSchema> karutaQuizChoiceSchemaInserter = KarutaQuizChoiceSchema.relation(orma).inserter();

            for (KarutaQuiz karutaQuiz : karutaQuizList) {
                KarutaQuizSchema karutaQuizSchema = new KarutaQuizSchema();
                karutaQuizSchema.quizId = karutaQuiz.identifier().value();
                karutaQuizSchema.collectId = karutaQuiz.collectId().value();
                karutaQuizSchema.id = karutaQuizSchemaInserter.execute(karutaQuizSchema);
                List<KarutaIdentifier> choiceList = karutaQuiz.choiceList();
                for (KarutaIdentifier karutaIdentifier : choiceList) {
                    KarutaQuizChoiceSchema karutaQuizChoiceSchema = new KarutaQuizChoiceSchema();
                    karutaQuizChoiceSchema.karutaQuizSchema = SingleAssociation.just(karutaQuizSchema);
                    karutaQuizChoiceSchema.karutaId = karutaIdentifier.value();
                    karutaQuizChoiceSchema.orderNo = choiceList.indexOf(karutaIdentifier);
                    karutaQuizChoiceSchemaInserter.execute(karutaQuizChoiceSchema);
                }
            }
        });
    }

    @Override
    public Single<KarutaQuiz> pop() {
        return KarutaQuizSchema.relation(orma)
                .where("answerTime = ?", 0)
                .selector()
                .executeAsObservable()
                .firstOrError()
                .map(karutaQuizSchema -> {
                    List<KarutaIdentifier> karutaIdentifierList = new ArrayList<>();
                    karutaQuizSchema.getChoices(orma)
                            .selector()
                            .executeAsObservable()
                            .map(karutaQuizChoiceSchema -> new KarutaIdentifier(karutaQuizChoiceSchema.karutaId))
                            .subscribe(karutaIdentifierList::add);
                    return new KarutaQuiz(new KarutaQuizIdentifier(karutaQuizSchema.quizId),
                            karutaIdentifierList,
                            new KarutaIdentifier(karutaQuizSchema.collectId));
                });
    }

    @Override
    public Single<KarutaQuiz> resolve(KarutaQuizIdentifier identifier) {
        return KarutaQuizSchema.relation(orma)
                .quizIdEq(identifier.value())
                .selector()
                .executeAsObservable()
                .firstOrError()
                .map(funcConvertEntity);
    }

    @Override
    public Completable store(KarutaQuiz karutaQuiz) {
        return orma.transactionAsCompletable(() -> {
            KarutaQuizSchema_Updater updater = KarutaQuizSchema.relation(orma)
                    .quizIdEq(karutaQuiz.identifier().value())
                    .updater();

            updater.startDate(karutaQuiz.startDate());
            if (karutaQuiz.result() != null) {
                updater.isCollect(karutaQuiz.result().isCollect)
                        .choiceNo(karutaQuiz.result().choiceNo)
                        .answerTime(karutaQuiz.result().answerTime);
            }
            updater.execute();
        });
    }

    @Override
    public Single<Boolean> existNextQuiz() {
        return KarutaQuizSchema.relation(orma)
                .where("answerTime = ?", 0)
                .selector()
                .executeAsObservable().count()
                .map(count -> 0 < count);
    }

    @Override
    public Single<KarutaQuizzes> list() {
        return KarutaQuizSchema.relation(orma)
                .selector()
                .executeAsObservable()
                .map(funcConvertEntity)
                .toList()
                .map(KarutaQuizzes::new);
    }

    @Override
    public Single<KarutaQuizPosition> countQuizByAnswered() {
        Single<Long> totalCountSingle = KarutaQuizSchema.relation(orma)
                .selector()
                .executeAsObservable()
                .count();

        Single<Long> answeredCountSingle = KarutaQuizSchema.relation(orma)
                .where("answerTime > ?", 0)
                .selector()
                .executeAsObservable()
                .count();

        return Single.zip(totalCountSingle, answeredCountSingle, (totalCount, answeredCount) ->
                new KarutaQuizPosition(totalCount.intValue(), answeredCount.intValue()));
    }

    private Function<KarutaQuizSchema, KarutaQuiz> funcConvertEntity = new Function<KarutaQuizSchema, KarutaQuiz>() {
        @Override
        public KarutaQuiz apply(KarutaQuizSchema karutaQuizSchema) {
            List<KarutaIdentifier> karutaIdentifierList = new ArrayList<>();
            karutaQuizSchema.getChoices(orma)
                    .selector()
                    .executeAsObservable()
                    .map(karutaQuizChoiceSchema -> new KarutaIdentifier(karutaQuizChoiceSchema.karutaId))
                    .subscribe(karutaIdentifierList::add);
            return new KarutaQuiz(new KarutaQuizIdentifier(karutaQuizSchema.quizId),
                    karutaIdentifierList,
                    new KarutaIdentifier(karutaQuizSchema.collectId),
                    karutaQuizSchema.startDate,
                    karutaQuizSchema.answerTime,
                    karutaQuizSchema.choiceNo,
                    karutaQuizSchema.isCollect);
        }
    };
}
