package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.dialog.di;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.dialog.ConfirmMaterialEditDialogFragmentViewModel;

@Module
public class ConfirmMaterialEditDialogFragmentViewModelModule {
    @Provides
    @ForFragment
    ConfirmMaterialEditDialogFragmentViewModel provideConfirmMaterialEditDialogFragmentViewModel() {
        return new ConfirmMaterialEditDialogFragmentViewModel();
    }
}
