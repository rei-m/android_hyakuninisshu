package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import java.util.List;

import java8.util.stream.StreamSupport;
import me.rei_m.hyakuninisshu.domain.karuta.model.BottomPhrase;
import me.rei_m.hyakuninisshu.domain.karuta.model.TopPhrase;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizViewModel;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizUsecase;
import rx.Observable;

public class DisplayKarutaQuizUsecaseImpl implements DisplayKarutaQuizUsecase {

    private final KarutaQuizRepository karutaQuizRepository;

    public DisplayKarutaQuizUsecaseImpl(@NonNull KarutaQuizRepository karutaQuizRepository) {
        this.karutaQuizRepository = karutaQuizRepository;
    }

    @Override
    public Observable<QuizViewModel> execute() {
        return karutaQuizRepository.pop().map(karutaQuiz -> {
            TopPhrase quizPhrase = karutaQuiz.getQuizPhrase();
            List<BottomPhrase> choices = karutaQuiz.getQuizChoices();
            StreamSupport.stream(karutaQuiz.getQuizChoices())
                    .map(it -> new QuizViewModel.QuizChoiceViewModel(it.getIdentifier().getValue(), it.getFourth().getKanji(), it.getFifth().getKanji()));

            BottomPhrase choiceFirst = choices.get(0);
            BottomPhrase choiceSecond = choices.get(1);
            BottomPhrase choiceThird = choices.get(2);
            BottomPhrase choiceFourth = choices.get(3);

            return new QuizViewModel(karutaQuiz.getIdentifier().getValue(),
                    quizPhrase.getFirst().getKanji(),
                    quizPhrase.getSecond().getKanji(),
                    quizPhrase.getThird().getKanji(),
                    new QuizViewModel.QuizChoiceViewModel(choiceFirst.getIdentifier().getValue(), choiceFirst.getFourth().getKanji(), choiceFirst.getFifth().getKanji()),
                    new QuizViewModel.QuizChoiceViewModel(choiceSecond.getIdentifier().getValue(), choiceSecond.getFourth().getKanji(), choiceSecond.getFifth().getKanji()),
                    new QuizViewModel.QuizChoiceViewModel(choiceThird.getIdentifier().getValue(), choiceThird.getFourth().getKanji(), choiceThird.getFifth().getKanji()),
                    new QuizViewModel.QuizChoiceViewModel(choiceFourth.getIdentifier().getValue(), choiceFourth.getFourth().getKanji(), choiceFourth.getFifth().getKanji()));
        });
    }
}
