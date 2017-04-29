package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.dialog.ConfirmMaterialEditDialogFragmentViewModel;

@Module
public class ConfirmMaterialEditDialogFragmentModule {

    private final Context context;

    public ConfirmMaterialEditDialogFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForFragment
    Context provideContext() {
        return context;
    }

    @Provides
    ConfirmMaterialEditDialogFragmentViewModel provideConfirmMaterialEditDialogFragmentViewModel() {
        return new ConfirmMaterialEditDialogFragmentViewModel();
    }
}
