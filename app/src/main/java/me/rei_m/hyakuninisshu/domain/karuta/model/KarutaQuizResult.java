package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class KarutaQuizResult implements ValueObject {

    public final KarutaIdentifier collectKarutaId;

    public final boolean isCollect;

    public final long answerTime;

    public KarutaQuizResult(@NonNull KarutaIdentifier collectKarutaId,
                            boolean isCollect,
                            long answerTime) {
        this.collectKarutaId = collectKarutaId;
        this.isCollect = isCollect;
        this.answerTime = answerTime;
    }
}
