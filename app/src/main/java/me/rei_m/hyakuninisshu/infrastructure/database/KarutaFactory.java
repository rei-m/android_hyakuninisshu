package me.rei_m.hyakuninisshu.infrastructure.database;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.model.karuta.BottomPhrase;
import me.rei_m.hyakuninisshu.domain.model.karuta.Color;
import me.rei_m.hyakuninisshu.domain.model.karuta.ImageNo;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji;
import me.rei_m.hyakuninisshu.domain.model.karuta.Phrase;
import me.rei_m.hyakuninisshu.domain.model.karuta.TopPhrase;

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

        String translation = (schema.translation != null) ? schema.translation : "";

        String color = (schema.color != null) ? schema.color : "";

        return new Karuta(identifier,
                schema.creator,
                topPhrase,
                bottomPhrase,
                new Kimariji(schema.kimariji),
                new ImageNo(schema.imageNo),
                translation,
                new Color(color));
    }
}
