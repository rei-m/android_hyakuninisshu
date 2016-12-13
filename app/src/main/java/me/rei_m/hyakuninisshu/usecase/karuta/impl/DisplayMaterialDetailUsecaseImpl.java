package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import io.reactivex.Maybe;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.MaterialDetailViewModel;
import me.rei_m.hyakuninisshu.presentation.utilitty.KarutaDisplayUtil;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayMaterialDetailUsecase;

import static me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaConstant.SPACE;

public class DisplayMaterialDetailUsecaseImpl implements DisplayMaterialDetailUsecase {

    private final Context context;

    private final KarutaRepository karutaRepository;

    public DisplayMaterialDetailUsecaseImpl(@NonNull Context context,
                                            @NonNull KarutaRepository karutaRepository) {
        this.context = context;
        this.karutaRepository = karutaRepository;
    }

    @Override
    public Maybe<MaterialDetailViewModel> execute(int karutaNo) {
        return karutaRepository.resolve(new KarutaIdentifier(karutaNo)).map(karuta -> {

            String karutaIdentifierString = KarutaDisplayUtil.convertNumberToString(context, (int) karuta.getIdentifier().getValue());

            String topPhraseKanji = karuta.getTopPhrase().getFirst().getKanji() + SPACE +
                    karuta.getTopPhrase().getSecond().getKanji() + SPACE +
                    karuta.getTopPhrase().getThird().getKanji();

            String bottomPhraseKanji = karuta.getBottomPhrase().getFourth().getKanji() + SPACE +
                    karuta.getBottomPhrase().getFifth().getKanji();

            String topPhraseKana = karuta.getTopPhrase().getFirst().getKana() + SPACE +
                    karuta.getTopPhrase().getSecond().getKana() + SPACE +
                    karuta.getTopPhrase().getThird().getKana();

            String bottomPhraseKana = karuta.getBottomPhrase().getFourth().getKana() + SPACE +
                    karuta.getBottomPhrase().getFifth().getKana();

            return new MaterialDetailViewModel(karutaIdentifierString,
                    karuta.getImageNo(),
                    karuta.getCreator(),
                    karuta.getKimariji(),
                    topPhraseKanji,
                    bottomPhraseKanji,
                    topPhraseKana,
                    bottomPhraseKana,
                    karuta.getTranslation());
        });
    }
}
