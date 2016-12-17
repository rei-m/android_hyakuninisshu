package me.rei_m.hyakuninisshu.usecase.karuta;

import io.reactivex.Single;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.ExamViewModel;

public interface DisplayExamUsecase {
    Single<ExamViewModel> execute();
}
