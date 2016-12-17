package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizAnswerViewModel;
import me.rei_m.hyakuninisshu.presentation.utilitty.KarutaDisplayUtil;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizAnswerUsecase;

public class DisplayKarutaQuizAnswerUsecaseImpl implements DisplayKarutaQuizAnswerUsecase {

    private final Context context;

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    public DisplayKarutaQuizAnswerUsecaseImpl(@NonNull Context context,
                                              @NonNull KarutaRepository karutaRepository,
                                              @NonNull KarutaQuizRepository karutaQuizRepository) {
        this.context = context;
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
    }

    @Override
    public Single<QuizAnswerViewModel> execute(@NonNull String quizId) {

        Observable<Boolean> existNextQuizObservable = karutaQuizRepository.existNextQuiz().toObservable();

        Observable<KarutaQuiz> karutaQuizObservable = karutaQuizRepository.resolve(new KarutaQuizIdentifier(quizId))
                .toObservable()
                .share();

        Observable<Karuta> karutaObservable = karutaQuizObservable.flatMapSingle(karutaQuiz ->
                karutaRepository.resolve(karutaQuiz.getResult().collectKarutaId));

        return Observable.zip(existNextQuizObservable, karutaQuizObservable, karutaObservable, (existNextQuiz, karutaQuiz, karuta) -> {

            String karutaIdentifierString = KarutaDisplayUtil.convertNumberToString(context, (int) karuta.getIdentifier().getValue());

            String kimariji = KarutaDisplayUtil.convertKimarijiToString(context, karuta.getKimariji());

            return new QuizAnswerViewModel(karutaIdentifierString + " / " + kimariji,
                    karuta.getCreator(),
                    karuta.getTopPhrase().getFirst().getKanji(),
                    karuta.getTopPhrase().getSecond().getKanji(),
                    karuta.getTopPhrase().getThird().getKanji(),
                    karuta.getBottomPhrase().getFourth().getKanji(),
                    karuta.getBottomPhrase().getFifth().getKanji(),
                    "karuta_" + karuta.getImageNo(),
                    karutaQuiz.getResult().isCollect,
                    existNextQuiz);

        }).singleOrError();
    }
}
