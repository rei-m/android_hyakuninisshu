package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizResultViewModel;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizResultUsecase;

public class DisplayKarutaQuizResultUsecaseImpl implements DisplayKarutaQuizResultUsecase {

    private final Context context;

    private final KarutaQuizRepository karutaQuizRepository;

    public DisplayKarutaQuizResultUsecaseImpl(@NonNull Context context,
                                              @NonNull KarutaQuizRepository karutaQuizRepository) {
        this.context = context;
        this.karutaQuizRepository = karutaQuizRepository;
    }

    @Override
    public Single<QuizResultViewModel> execute() {

        return karutaQuizRepository.asEntityList().map(karutaQuizList -> {

            final int quizCount = karutaQuizList.size();

            long totalAnswerTimeMillSec = 0;

            int collectCount = 0;

            for (KarutaQuiz karutaQuiz : karutaQuizList) {
                totalAnswerTimeMillSec += karutaQuiz.getResult().answerTime;
                if (karutaQuiz.getResult().isCollect) {
                    collectCount++;
                }
            }

            final String result = collectCount + "/" + quizCount;

            final float averageAnswerTime = totalAnswerTimeMillSec / (float) quizCount / (float) TimeUnit.SECONDS.toMillis(1);

            final String averageAnswerTimeString = String.format(Locale.JAPAN, "%.2f", averageAnswerTime);

            return new QuizResultViewModel(result, context.getString(R.string.seconds, averageAnswerTimeString));
        });
    }
}
