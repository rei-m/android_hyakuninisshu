package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Locale;

import io.reactivex.Single;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaExam;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaExamRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.ExamViewModel;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayExamUsecase;

public class DisplayExamUsecaseImpl implements DisplayExamUsecase {

    private final Context context;

    private final KarutaExamRepository karutaExamRepository;

    public DisplayExamUsecaseImpl(@NonNull Context context,
                                  @NonNull KarutaExamRepository karutaExamRepository) {
        this.context = context;
        this.karutaExamRepository = karutaExamRepository;
    }

    @Override
    public Single<ExamViewModel> execute() {
        return karutaExamRepository.asEntityList().map(karutaExams -> {

            final ExamViewModel viewModel;

            if (karutaExams.isEmpty()) {
                viewModel = new ExamViewModel(false, "", "");
            } else {
                KarutaExam karutaExam = karutaExams.get(0);

                final String averageAnswerTimeString = String.format(Locale.JAPAN, "%.2f", karutaExam.averageAnswerTime);

                final String score = (karutaExam.totalQuizCount - karutaExam.wrongKarutaIdList.size()) + "/" + karutaExam.totalQuizCount;

                viewModel = new ExamViewModel(true, score, context.getString(R.string.seconds, averageAnswerTimeString));
            }

            return viewModel;
        });
    }
}
