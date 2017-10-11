package me.rei_m.hyakuninisshu.presentation.karuta.widget.dialog.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.dialog.ConfirmMaterialEditDialogFragment;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.dialog.module.ConfirmMaterialEditDialogFragmentViewModelModule;

@Module
public abstract class ConfirmMaterialEditDialogFragmentModule {
    @ForFragment
    @ContributesAndroidInjector(modules = ConfirmMaterialEditDialogFragmentViewModelModule.class)
    abstract ConfirmMaterialEditDialogFragment contributeConfirmMaterialEditDialogFragmentInjector();
}
