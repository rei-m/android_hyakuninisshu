package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

public class Exam extends AbstractEntity<Exam, ExamIdentifier> {

    public Exam(@NonNull ExamIdentifier identifier,
                @NonNull Date tookExamDate,
                int totalQuizCount,
                @NonNull List<KarutaIdentifier> wrongKarutaIdList) {
        super(identifier);
        this.tookExamDate = tookExamDate;
        this.totalQuizCount = totalQuizCount;
        this.wrongKarutaIdList = wrongKarutaIdList;
    }

    public final Date tookExamDate;

    public final int totalQuizCount;

    public final List<KarutaIdentifier> wrongKarutaIdList;
}
