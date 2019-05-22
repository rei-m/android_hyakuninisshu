package me.rei_m.hyakuninisshu.feature.materialedit.ui;

import androidx.fragment.app.Fragment;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import javax.annotation.Generated;
import me.rei_m.hyakuninisshu.feature.corecomponent.di.FragmentScope;

@Module(
  subcomponents =
      MaterialEditFragment_Module_ContributeInjector.MaterialEditFragmentSubcomponent.class
)
@Generated("dagger.android.processor.AndroidProcessor")
public abstract class MaterialEditFragment_Module_ContributeInjector {
  private MaterialEditFragment_Module_ContributeInjector() {}

  @Binds
  @IntoMap
  @FragmentKey(MaterialEditFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      MaterialEditFragmentSubcomponent.Builder builder);

  @Subcomponent
  @FragmentScope
  public interface MaterialEditFragmentSubcomponent extends AndroidInjector<MaterialEditFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MaterialEditFragment> {}
  }
}
