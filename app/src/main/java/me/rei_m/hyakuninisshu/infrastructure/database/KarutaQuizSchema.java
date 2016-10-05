package me.rei_m.hyakuninisshu.infrastructure.database;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class KarutaQuizSchema {

    @PrimaryKey(auto = true)
    public int id;

    @Column
    public int collectKarutaId;

    @Column
    public int answerKarutaId;

    @Column
    public long startedTimeInMills;

    @Column
    public long answeredTimeInMills;
}
