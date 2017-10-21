package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;

public class KarutaQuiz extends AbstractEntity<KarutaQuiz, KarutaQuizIdentifier> {

    public static final int CHOICE_SIZE = 4;

    private final List<KarutaIdentifier> choiceList;

    private final KarutaIdentifier correctId;

    private Date startDate;

    private KarutaQuizResult result;

    public KarutaQuiz(@NonNull KarutaQuizIdentifier identifier,
                      @NonNull List<KarutaIdentifier> choiceList,
                      @NonNull KarutaIdentifier correctId) {
        super(identifier);
        this.choiceList = choiceList;
        this.correctId = correctId;
    }

    public KarutaQuiz(@NonNull KarutaQuizIdentifier identifier,
                      @NonNull List<KarutaIdentifier> choiceList,
                      @NonNull KarutaIdentifier correctId,
                      @NonNull Date startDate) {
        this(identifier, choiceList, correctId);
        this.startDate = startDate;
    }

    public KarutaQuiz(@NonNull KarutaQuizIdentifier identifier,
                      @NonNull List<KarutaIdentifier> choiceList,
                      @NonNull KarutaIdentifier correctId,
                      @NonNull Date startDate,
                      long answerTime,
                      @NonNull ChoiceNo choiceNo,
                      boolean isCorrect) {
        this(identifier, choiceList, correctId, startDate);
        this.result = new KarutaQuizResult(correctId,
                choiceNo,
                isCorrect,
                answerTime);
    }

    public List<KarutaIdentifier> choiceList() {
        return Collections.unmodifiableList(choiceList);
    }

    public KarutaIdentifier correctId() {
        return correctId;
    }

    public Date startDate() {
        return startDate;
    }

    public KarutaQuizResult result() {
        return result;
    }

    public KarutaQuiz start(@NonNull Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public KarutaQuiz verify(@NonNull ChoiceNo choiceNo, @NonNull Date answerDate) throws IllegalStateException, IllegalArgumentException {
        if (startDate == null) {
            throw new IllegalStateException("Quiz is not started. Call start.");
        }
        KarutaIdentifier selectedId = choiceList.get(choiceNo.asIndex());
        boolean isCorrect = correctId.equals(selectedId);
        long answerTime = answerDate.getTime() - startDate.getTime();
        this.result = new KarutaQuizResult(correctId, choiceNo, isCorrect, answerTime);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        KarutaQuiz that = (KarutaQuiz) o;

        if (!choiceList.equals(that.choiceList)) return false;
        if (!correctId.equals(that.correctId)) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null)
            return false;
        return result != null ? result.equals(that.result) : that.result == null;

    }

    @Override
    public int hashCode() {
        int result1 = super.hashCode();
        result1 = 31 * result1 + choiceList.hashCode();
        result1 = 31 * result1 + correctId.hashCode();
        result1 = 31 * result1 + (startDate != null ? startDate.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "KarutaQuiz{" +
                "choiceList=" + choiceList +
                ", correctId=" + correctId +
                ", startDate=" + startDate +
                ", result=" + result +
                "} " + super.toString();
    }
}
