package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.infrastructure.database.KarutaSchema;

public class KarutaFactory {

    private KarutaFactory() {
    }

    public static Karuta create(@NonNull KarutaSchema schema) {

        KarutaIdentifier identifier = new KarutaIdentifier(schema.id);

        KarutaPart firstPart = new KarutaPart(schema.firstKana, schema.firstKanji);
        KarutaPart secondPart = new KarutaPart(schema.secondKana, schema.secondKanji);
        KarutaPart thirdPart = new KarutaPart(schema.thirdKana, schema.thirdKanji);
        KarutaPart fourthPart = new KarutaPart(schema.fourthKana, schema.fourthKanji);
        KarutaPart fifthPart = new KarutaPart(schema.fifthKana, schema.fifthKanji);

        FirstHalf firstHalf = new FirstHalf(identifier, firstPart, secondPart, thirdPart);
        SecondHalf secondHalf = new SecondHalf(identifier, fourthPart, fifthPart);

        return new Karuta(identifier, firstHalf, secondHalf, schema.kimariji, schema.imageNo);
    }
}
