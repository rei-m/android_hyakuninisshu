package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaConstant;

public class KarutaExam extends AbstractEntity<KarutaExam, KarutaExamIdentifier> {

    public static final int MAX_HISTORY_COUNT = 10;

    public KarutaExam(@NonNull KarutaExamIdentifier identifier,
                      @NonNull Date tookExamDate,
                      int totalQuizCount,
                      float averageAnswerTime,
                      @NonNull List<KarutaIdentifier> wrongKarutaIdList) {
        super(identifier);
        this.tookExamDate = tookExamDate;
        this.totalQuizCount = totalQuizCount;
        this.averageAnswerTime = averageAnswerTime;
        this.wrongKarutaIdList = wrongKarutaIdList;
    }

    public final Date tookExamDate;

    public final int totalQuizCount;

    public final float averageAnswerTime;

    public final List<KarutaIdentifier> wrongKarutaIdList;

    public ExamResult result() {
        final boolean[] karutaQuizResultList = new boolean[KarutaConstant.NUMBER_OF_KARUTA];

        Arrays.fill(karutaQuizResultList, true);

        for (KarutaIdentifier wrongKarutaIdentifier : wrongKarutaIdList) {
            int wrongKarutaIndex = (int) wrongKarutaIdentifier.value() - 1;
            karutaQuizResultList[wrongKarutaIndex] = false;
        }

        return new ExamResult(totalQuizCount,
                totalQuizCount - wrongKarutaIdList.size(),
                averageAnswerTime,
                karutaQuizResultList);
    }
}
