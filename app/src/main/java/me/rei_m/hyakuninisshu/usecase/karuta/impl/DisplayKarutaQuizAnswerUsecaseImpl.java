package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizAnswerViewModel;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizAnswerUsecase;
import rx.Observable;
import rx.functions.Func1;

public class DisplayKarutaQuizAnswerUsecaseImpl implements DisplayKarutaQuizAnswerUsecase {

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    public DisplayKarutaQuizAnswerUsecaseImpl(@NonNull KarutaRepository karutaRepository,
                                              @NonNull KarutaQuizRepository karutaQuizRepository) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
    }

    @Override
    public Observable<QuizAnswerViewModel> execute(String quizId) {

        Observable<Boolean> existNextQuizObservable = karutaQuizRepository.existNextQuiz();
        Observable<KarutaQuiz> karutaQuizObservable = karutaQuizRepository.resolve(new KarutaQuizIdentifier(quizId)).share();
        Observable<Karuta> karutaObservable = karutaQuizObservable.concatMap(new Func1<KarutaQuiz, Observable<Karuta>>() {
            @Override
            public Observable<Karuta> call(KarutaQuiz karutaQuiz) {
                return karutaRepository.resolve(karutaQuiz.getResult().collectKarutaId);
            }
        });

        return Observable.zip(existNextQuizObservable, karutaQuizObservable, karutaObservable, (existNextQuiz, karutaQuiz, karuta) ->

                new QuizAnswerViewModel("第" + karuta.getIdentifier().getValue() + "首",
                        karuta.getCreator(),
                        karuta.getTopPhrase().getFirst().getKanji(),
                        karuta.getTopPhrase().getSecond().getKanji(),
                        karuta.getTopPhrase().getThird().getKanji(),
                        karuta.getBottomPhrase().getFourth().getKanji(),
                        karuta.getBottomPhrase().getFifth().getKanji(),
                        "karuta_" + karuta.getImageNo(),
                        karutaQuiz.getResult().isCollect,
                        existNextQuiz));
    }
}
