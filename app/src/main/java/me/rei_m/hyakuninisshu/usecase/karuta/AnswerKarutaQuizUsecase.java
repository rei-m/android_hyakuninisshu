package me.rei_m.hyakuninisshu.usecase.karuta;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.KarutaQuiz;
import rx.Observable;

public interface AnswerKarutaQuizUsecase {

    Observable<KarutaQuiz> execute();

}
