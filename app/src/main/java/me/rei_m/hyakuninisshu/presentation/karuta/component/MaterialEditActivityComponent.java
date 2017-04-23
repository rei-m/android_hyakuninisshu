package me.rei_m.hyakuninisshu.presentation.karuta.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialEditActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.module.MaterialEditActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.dialog.ConfirmMaterialEditDialogFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialEditFragment;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;

@Subcomponent(modules = {ActivityModule.class, MaterialEditActivityModule.class})
public interface MaterialEditActivityComponent extends MaterialEditFragment.Injector,
        ConfirmMaterialEditDialogFragment.Injector {
    void inject(MaterialEditActivity activity);
}
