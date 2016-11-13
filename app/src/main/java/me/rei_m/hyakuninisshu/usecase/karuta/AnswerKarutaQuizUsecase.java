package me.rei_m.hyakuninisshu.usecase.karuta;

import io.reactivex.Maybe;

public interface AnswerKarutaQuizUsecase {
    Maybe<Boolean> execute(String quizId, int choiceNo);
}
