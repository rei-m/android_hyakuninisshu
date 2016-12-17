package me.rei_m.hyakuninisshu.usecase.karuta;

import io.reactivex.Single;

public interface AnswerKarutaQuizUsecase {
    Single<Boolean> execute(String quizId, int choiceNo);
}
