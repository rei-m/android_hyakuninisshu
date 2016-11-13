package me.rei_m.hyakuninisshu.usecase.karuta;


import io.reactivex.Observable;

public interface StartKarutaQuizUsecase {
    Observable<Void> execute(int fromKarutaId, int toKarutaId, int kimarijiPosition);
}
