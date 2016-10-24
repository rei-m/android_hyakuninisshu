package me.rei_m.hyakuninisshu.usecase.karuta;

import rx.Observable;

public interface AnswerKarutaQuizUsecase {
    Observable<Boolean> execute(String quizId, int choiceNo);
}
