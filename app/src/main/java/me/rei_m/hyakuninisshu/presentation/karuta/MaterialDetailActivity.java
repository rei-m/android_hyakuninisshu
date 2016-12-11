package me.rei_m.hyakuninisshu.presentation.karuta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.presentation.BaseActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.component.MaterialDetailActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.module.MaterialDetailActivityModule;

public class MaterialDetailActivity extends BaseActivity {

    private static final String ARG_KARUTA_NO = "karutaNo";

    private static final int UNKNOWN_KARUTA_NO = -1;

    public static Intent createIntent(@NonNull Context context,
                                      int karutaNo) {
        Intent intent = new Intent(context, MaterialDetailActivity.class);
        intent.putExtra(ARG_KARUTA_NO, karutaNo);
        return intent;
    }

    private MaterialDetailActivityComponent component;

    private int initialDisplayKarutaNo = UNKNOWN_KARUTA_NO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialDisplayKarutaNo = getIntent().getIntExtra(ARG_KARUTA_NO, UNKNOWN_KARUTA_NO);
        // TODO エラーチェック.
        System.out.println(initialDisplayKarutaNo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        component = null;
    }

    @Override
    protected void setupActivityComponent() {
        component = ((App) getApplication()).getComponent().plus(new MaterialDetailActivityModule(this));
        component.inject(this);
    }
}
