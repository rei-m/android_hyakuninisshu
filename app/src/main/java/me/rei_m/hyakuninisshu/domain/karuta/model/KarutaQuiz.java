package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

public class KarutaQuiz extends AbstractEntity<KarutaQuiz, KarutaQuizIdentifier> {

    private final KarutaQuizContents contents;

    private Date startDate;

    private KarutaQuizResult result;

    public KarutaQuiz(@NonNull KarutaQuizIdentifier identifier,
                      @NonNull List<KarutaIdentifier> choiceList,
                      @NonNull KarutaIdentifier collectId) {
        super(identifier);
        contents = new KarutaQuizContents(choiceList, collectId);
    }

    public KarutaQuizResult getResult() {
        return result;
    }

    public KarutaQuizContents start(@NonNull Date startDate) {
        this.startDate = startDate;
        return contents;
    }

    public void verify(int choiceNo, @NonNull Date answerDate) {
        // TODO: startをチェックしてnullならエラー
        // TODO: startよりanswerが小さかったらエラー
        // TODO: choiceNoがリストの数より大きかったらエラー
        KarutaIdentifier selectedId = contents.choiceList.get(choiceNo - 1);
        boolean isCollect = contents.collectId.equals(selectedId);
        long answerTime = answerDate.getTime() - startDate.getTime();
        this.result = new KarutaQuizResult(contents.collectId, isCollect, answerTime);
    }
}
