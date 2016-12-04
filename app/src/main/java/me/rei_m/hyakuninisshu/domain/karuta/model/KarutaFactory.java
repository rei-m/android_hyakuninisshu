package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.infrastructure.database.KarutaSchema;

public class KarutaFactory {

    private KarutaFactory() {
    }

    public static Karuta create(@NonNull KarutaSchema schema) {

        KarutaIdentifier identifier = new KarutaIdentifier(schema.id);

        Phrase firstPart = new Phrase(schema.firstKana, schema.firstKanji);
        Phrase secondPart = new Phrase(schema.secondKana, schema.secondKanji);
        Phrase thirdPart = new Phrase(schema.thirdKana, schema.thirdKanji);
        Phrase fourthPart = new Phrase(schema.fourthKana, schema.fourthKanji);
        Phrase fifthPart = new Phrase(schema.fifthKana, schema.fifthKanji);

        TopPhrase topPhrase = new TopPhrase(identifier, firstPart, secondPart, thirdPart);
        BottomPhrase bottomPhrase = new BottomPhrase(identifier, fourthPart, fifthPart);
        
        return new Karuta(identifier, schema.creator, topPhrase, bottomPhrase, schema.kimariji, schema.imageNo);
    }
}
