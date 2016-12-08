package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Locale;

import io.reactivex.Single;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.karuta.model.ExamIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaExamRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.ExamResultViewModel;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaExamResultUsecase;

public class DisplayKarutaExamResultUsecaseImpl implements DisplayKarutaExamResultUsecase {

    private final Context context;

    private final KarutaExamRepository karutaExamRepository;

    public DisplayKarutaExamResultUsecaseImpl(@NonNull Context context,
                                              @NonNull KarutaExamRepository karutaExamRepository) {
        this.context = context;
        this.karutaExamRepository = karutaExamRepository;
    }

    @Override
    public Single<ExamResultViewModel> execute(@NonNull Long examId) {

        return karutaExamRepository.resolve(new ExamIdentifier(examId)).map(exam -> {

            final int quizCount = exam.totalQuizCount;

            final String result = (quizCount - exam.wrongKarutaIdList.size()) + "/" + quizCount;

            final String averageAnswerTimeString = String.format(Locale.JAPAN, "%.2f", exam.averageAnswerTime);

            return new ExamResultViewModel(result, context.getString(R.string.seconds, averageAnswerTimeString));
        });
    }
}