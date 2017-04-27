package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.dialog.ConfirmMaterialEditDialogFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.ConfirmMaterialEditDialogFragmentModule;
import me.rei_m.hyakuninisshu.presentation.module.FragmentModule;

@Subcomponent(modules = {FragmentModule.class, ConfirmMaterialEditDialogFragmentModule.class})
public interface ConfirmMaterialEditDialogFragmentComponent {
    void inject(ConfirmMaterialEditDialogFragment fragment);
}
