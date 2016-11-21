package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.Date;
import java.util.List;

import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import me.rei_m.hyakuninisshu.domain.karuta.model.BottomPhrase;
import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizContents;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizViewModel;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizUsecase;

public class DisplayKarutaQuizUsecaseImpl implements DisplayKarutaQuizUsecase {

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    public DisplayKarutaQuizUsecaseImpl(@NonNull KarutaRepository karutaRepository,
                                        @NonNull KarutaQuizRepository karutaQuizRepository) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
    }

    @Override
    public Single<QuizViewModel> execute() {

        return karutaQuizRepository.pop().flatMapSingle(new Function<KarutaQuiz, SingleSource<QuizViewModel>>() {
            @Override
            public SingleSource<QuizViewModel> apply(KarutaQuiz karutaQuiz) {
                KarutaQuizContents quizContents = karutaQuiz.start(new Date());

                Single<List<Karuta>> choiceObservable = Observable.fromIterable(quizContents.choiceList).flatMapMaybe(new Function<KarutaIdentifier, MaybeSource<Karuta>>() {
                    @Override
                    public MaybeSource<Karuta> apply(KarutaIdentifier identifier) {
                        return karutaRepository.resolve(identifier);
                    }
                }).toList();

                Single<Karuta> collectObservable = karutaRepository.resolve(quizContents.collectId).toSingle();

                Single<Pair<Integer, Integer>> countObservable = karutaQuizRepository.countQuizByAnswered();

                return Single.zip(choiceObservable, collectObservable, countObservable, (karutaList, karuta, count) -> {
                    BottomPhrase choiceFirst = karutaList.get(0).getBottomPhrase();
                    BottomPhrase choiceSecond = karutaList.get(1).getBottomPhrase();
                    BottomPhrase choiceThird = karutaList.get(2).getBottomPhrase();
                    BottomPhrase choiceFourth = karutaList.get(3).getBottomPhrase();

                    String quizCount = String.valueOf(count.second + 1) + " / " + count.first.toString();

                    return new QuizViewModel(karutaQuiz.getIdentifier().getValue(),
                            karuta.getTopPhrase().getFirst().getKanji(),
                            karuta.getTopPhrase().getSecond().getKanji(),
                            karuta.getTopPhrase().getThird().getKanji(),
                            new QuizViewModel.QuizChoiceViewModel(choiceFirst.getFourth().getKanji(), choiceFirst.getFifth().getKanji()),
                            new QuizViewModel.QuizChoiceViewModel(choiceSecond.getFourth().getKanji(), choiceSecond.getFifth().getKanji()),
                            new QuizViewModel.QuizChoiceViewModel(choiceThird.getFourth().getKanji(), choiceThird.getFifth().getKanji()),
                            new QuizViewModel.QuizChoiceViewModel(choiceFourth.getFourth().getKanji(), choiceFourth.getFifth().getKanji()),
                            quizCount);
                });
            }
        });
    }
}
