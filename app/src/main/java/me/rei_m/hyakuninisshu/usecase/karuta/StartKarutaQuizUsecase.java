package me.rei_m.hyakuninisshu.usecase.karuta;

import rx.Observable;

public interface StartKarutaQuizUsecase {

    Observable<Void> execute(int quizSize);

}
