package me.rei_m.hyakuninisshu.usecase.karuta;

import io.reactivex.Single;

public interface FinishKarutaExamUsecase {
    Single<Long> execute();
}
