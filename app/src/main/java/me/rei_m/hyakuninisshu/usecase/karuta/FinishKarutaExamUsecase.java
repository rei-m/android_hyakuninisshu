package me.rei_m.hyakuninisshu.usecase.karuta;

import io.reactivex.Maybe;
import me.rei_m.hyakuninisshu.domain.karuta.model.ExamIdentifier;

public interface FinishKarutaExamUsecase {
    Maybe<ExamIdentifier> execute();
}
