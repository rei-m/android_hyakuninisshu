package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.karuta.model.BottomPhrase;
import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizContents;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizViewModel;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizUsecase;
import rx.Observable;
import rx.functions.Func1;

public class DisplayKarutaQuizUsecaseImpl implements DisplayKarutaQuizUsecase {

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    public DisplayKarutaQuizUsecaseImpl(@NonNull KarutaRepository karutaRepository,
                                        @NonNull KarutaQuizRepository karutaQuizRepository) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
    }

    @Override
    public Observable<QuizViewModel> execute() {
        return karutaQuizRepository.pop().concatMap(karutaQuiz -> {

            KarutaQuizContents quizContents = karutaQuiz.start(new Date());

            Observable<List<Karuta>> choiceObservable = Observable.from(quizContents.choiceList).flatMap(new Func1<KarutaIdentifier, Observable<Karuta>>() {
                @Override
                public Observable<Karuta> call(KarutaIdentifier identifier) {
                    return karutaRepository.resolve(identifier);
                }
            }).toList();

            Observable<Karuta> collectObservable = karutaRepository.resolve(quizContents.collectId);

            return Observable.zip(choiceObservable, collectObservable, (karutaList, karuta) -> {
                BottomPhrase choiceFirst = karutaList.get(0).getBottomPhrase();
                BottomPhrase choiceSecond = karutaList.get(1).getBottomPhrase();
                BottomPhrase choiceThird = karutaList.get(2).getBottomPhrase();
                BottomPhrase choiceFourth = karutaList.get(3).getBottomPhrase();
                return new QuizViewModel(karutaQuiz.getIdentifier().getValue(),
                        karuta.getTopPhrase().getFirst().getKanji(),
                        karuta.getTopPhrase().getSecond().getKanji(),
                        karuta.getTopPhrase().getThird().getKanji(),
                        new QuizViewModel.QuizChoiceViewModel(choiceFirst.getFourth().getKanji(), choiceFirst.getFifth().getKanji()),
                        new QuizViewModel.QuizChoiceViewModel(choiceSecond.getFourth().getKanji(), choiceSecond.getFifth().getKanji()),
                        new QuizViewModel.QuizChoiceViewModel(choiceThird.getFourth().getKanji(), choiceThird.getFifth().getKanji()),
                        new QuizViewModel.QuizChoiceViewModel(choiceFourth.getFourth().getKanji(), choiceFourth.getFifth().getKanji()));
            });
        });
    }
}
