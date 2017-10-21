package me.rei_m.hyakuninisshu.infrastructure.database;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.model.karuta.Color;
import me.rei_m.hyakuninisshu.domain.model.karuta.ImageNo;
import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKu;
import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKuIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji;
import me.rei_m.hyakuninisshu.domain.model.karuta.Phrase;
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKu;
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKuIdentifier;

class KarutaFactory {

    private KarutaFactory() {
    }

    public static Karuta create(@NonNull KarutaSchema schema) {

        KarutaIdentifier identifier = new KarutaIdentifier(schema.id);

        Phrase firstPart = new Phrase(schema.firstKana, schema.firstKanji);
        Phrase secondPart = new Phrase(schema.secondKana, schema.secondKanji);
        Phrase thirdPart = new Phrase(schema.thirdKana, schema.thirdKanji);
        Phrase fourthPart = new Phrase(schema.fourthKana, schema.fourthKanji);
        Phrase fifthPart = new Phrase(schema.fifthKana, schema.fifthKanji);

        KamiNoKu kamiNoKu = new KamiNoKu(new KamiNoKuIdentifier(), firstPart, secondPart, thirdPart);
        ShimoNoKu shimoNoKu = new ShimoNoKu(new ShimoNoKuIdentifier(), fourthPart, fifthPart);

        String translation = (schema.translation != null) ? schema.translation : "";

        Kimariji kimariji = Kimariji.forValue(schema.kimariji);

        Color color = Color.forValue(schema.color);

        return new Karuta(identifier,
                schema.creator,
                kamiNoKu,
                shimoNoKu,
                kimariji,
                new ImageNo(schema.imageNo),
                translation,
                color);
    }
}
