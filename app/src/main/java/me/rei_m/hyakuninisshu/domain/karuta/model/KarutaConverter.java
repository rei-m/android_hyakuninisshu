package me.rei_m.hyakuninisshu.domain.karuta.model;

import me.rei_m.hyakuninisshu.infrastructure.database.KarutaScheme;

public class KarutaConverter {

    private KarutaConverter() {
    }

    public static Karuta convert(KarutaScheme scheme) {

        KarutaPart firstPart = new KarutaPart(scheme.firstKana, scheme.firstKanji);
        KarutaPart secondPart = new KarutaPart(scheme.secondKana, scheme.secondKanji);
        KarutaPart thirdPart = new KarutaPart(scheme.thirdKana, scheme.thirdKanji);
        KarutaPart fourthPart = new KarutaPart(scheme.fourthKana, scheme.fourthKanji);
        KarutaPart fifthPart = new KarutaPart(scheme.fifthKana, scheme.fifthKanji);

        FirstHalf firstHalf = new FirstHalf(firstPart, secondPart, thirdPart);
        SecondHalf secondHalf = new SecondHalf(fourthPart, fifthPart);

        return new Karuta(new KarutaIdentifier(scheme.id), firstHalf, secondHalf, scheme.kimariji, scheme.imageNo);
    }
}
