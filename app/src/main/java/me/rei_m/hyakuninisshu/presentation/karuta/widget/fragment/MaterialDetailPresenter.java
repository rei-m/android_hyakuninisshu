package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayMaterialDetailUsecase;

public class MaterialDetailPresenter implements MaterialDetailContact.Actions {

    private final DisplayMaterialDetailUsecase displayMaterialDetailUsecase;

    private MaterialDetailContact.View view;

    private CompositeDisposable disposable;

    public MaterialDetailPresenter(@NonNull DisplayMaterialDetailUsecase displayMaterialDetailUsecase) {
        this.displayMaterialDetailUsecase = displayMaterialDetailUsecase;
    }

    @Override
    public void onCreate(@NonNull MaterialDetailContact.View view) {
        this.view = view;
    }

    @Override
    public void onResume(int karutaNo) {
        disposable = new CompositeDisposable();
        disposable.add(displayMaterialDetailUsecase.execute(karutaNo)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::initialize));
    }

    @Override
    public void onPause() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
