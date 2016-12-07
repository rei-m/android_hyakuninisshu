package me.rei_m.hyakuninisshu.usecase.karuta;

import io.reactivex.Maybe;

public interface FinishKarutaExamUsecase {
    Maybe<Long> execute();
}
