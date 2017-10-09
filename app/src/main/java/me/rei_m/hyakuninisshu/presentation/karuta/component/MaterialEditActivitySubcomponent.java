package me.rei_m.hyakuninisshu.presentation.karuta.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialEditActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.dialog.module.ConfirmMaterialEditDialogFragmentModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.MaterialEditFragmentModule;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.ForActivity;

@ForActivity
@Subcomponent(modules = {
        ActivityModule.class,
        MaterialEditFragmentModule.class,
        ConfirmMaterialEditDialogFragmentModule.class
})
public interface MaterialEditActivitySubcomponent extends AndroidInjector<MaterialEditActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MaterialEditActivity> {
        public abstract Builder activityModule(ActivityModule module);

        @Override
        public void seedInstance(MaterialEditActivity instance) {
            activityModule(new ActivityModule(instance));
        }
    }
}
