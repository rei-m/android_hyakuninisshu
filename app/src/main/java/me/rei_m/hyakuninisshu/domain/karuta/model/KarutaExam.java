package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

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
}
