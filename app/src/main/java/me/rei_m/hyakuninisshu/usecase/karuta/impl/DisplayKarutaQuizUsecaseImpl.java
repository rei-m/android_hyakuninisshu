package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import java.util.List;

import java8.util.stream.StreamSupport;
import me.rei_m.hyakuninisshu.domain.karuta.model.BottomPhrase;
import me.rei_m.hyakuninisshu.domain.karuta.model.TopPhrase;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.KarutaQuizViewModel;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizUsecase;
import rx.Observable;

public class DisplayKarutaQuizUsecaseImpl implements DisplayKarutaQuizUsecase {

    private final KarutaQuizRepository karutaQuizRepository;

    public DisplayKarutaQuizUsecaseImpl(@NonNull KarutaQuizRepository karutaQuizRepository) {
        this.karutaQuizRepository = karutaQuizRepository;
    }

    @Override
    public Observable<KarutaQuizViewModel> execute() {
        return karutaQuizRepository.pop().map(karutaQuiz -> {
            TopPhrase quizPhrase = karutaQuiz.getQuizPhrase();
            List<BottomPhrase> choices = karutaQuiz.getQuizChoices();
            StreamSupport.stream(karutaQuiz.getQuizChoices())
                    .map(it -> new KarutaQuizViewModel.KarutaQuizChoiceViewModel(it.getIdentifier().getValue(), it.getFourth().getKanji(), it.getFifth().getKana()));
            return new KarutaQuizViewModel(quizPhrase.getFirst().getKanji(),
                    quizPhrase.getSecond().getKanji(),
                    quizPhrase.getThird().getKanji(),
                    new KarutaQuizViewModel.KarutaQuizChoiceViewModel(choices.get(0).getIdentifier().getValue(), choices.get(0).getFourth().getKanji(), choices.get(0).getFifth().getKana()),
                    new KarutaQuizViewModel.KarutaQuizChoiceViewModel(choices.get(1).getIdentifier().getValue(), choices.get(1).getFourth().getKanji(), choices.get(1).getFifth().getKana()),
                    new KarutaQuizViewModel.KarutaQuizChoiceViewModel(choices.get(2).getIdentifier().getValue(), choices.get(2).getFourth().getKanji(), choices.get(2).getFifth().getKana()),
                    new KarutaQuizViewModel.KarutaQuizChoiceViewModel(choices.get(3).getIdentifier().getValue(), choices.get(3).getFourth().getKanji(), choices.get(3).getFifth().getKana()));
        });
    }
}
