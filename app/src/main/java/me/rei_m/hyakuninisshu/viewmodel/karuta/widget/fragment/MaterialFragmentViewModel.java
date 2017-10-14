package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment;

import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class MaterialFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableArrayList<Karuta> karutaList = new ObservableArrayList<>();

    private final KarutaModel karutaModel;

    private final AnalyticsManager analyticsManager;

    private Color color = Color.ALL;

    public MaterialFragmentViewModel(@NonNull KarutaModel karutaModel,
                                     @NonNull AnalyticsManager analyticsManager) {
        this.karutaModel = karutaModel;
        this.analyticsManager = analyticsManager;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaModel.completeGetKarutaListEvent.subscribe(karutaList -> {
            this.karutaList.clear();
            this.karutaList.addAll(karutaList);
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.MATERIAL);
        karutaModel.getKarutaList(color);
    }

    public void onOptionItemSelected(@NonNull Color color) {
        karutaModel.getKarutaList(color);
        this.color = color;
    }
}
