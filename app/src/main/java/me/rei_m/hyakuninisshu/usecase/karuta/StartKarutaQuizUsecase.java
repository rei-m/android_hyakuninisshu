package me.rei_m.hyakuninisshu.usecase.karuta;


import io.reactivex.Completable;

public interface StartKarutaQuizUsecase {
    Completable execute(int fromKarutaId, int toKarutaId, int kimarijiPosition);
}
