package me.rei_m.hyakuninisshu.feature.materialedit.ui;

import androidx.fragment.app.Fragment;
import dagger.MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.DaggerFragment_MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper;
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.Navigator;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MaterialEditFragment_MembersInjector
    implements MembersInjector<MaterialEditFragment> {
  private final Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider;

  private final Provider<AnalyticsHelper> analyticsHelperProvider;

  private final Provider<Navigator> navigatorProvider;

  private final Provider<MaterialEditViewModel.Factory> viewModelFactoryProvider;

  public MaterialEditFragment_MembersInjector(
      Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider,
      Provider<AnalyticsHelper> analyticsHelperProvider,
      Provider<Navigator> navigatorProvider,
      Provider<MaterialEditViewModel.Factory> viewModelFactoryProvider) {
    this.childFragmentInjectorProvider = childFragmentInjectorProvider;
    this.analyticsHelperProvider = analyticsHelperProvider;
    this.navigatorProvider = navigatorProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<MaterialEditFragment> create(
      Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider,
      Provider<AnalyticsHelper> analyticsHelperProvider,
      Provider<Navigator> navigatorProvider,
      Provider<MaterialEditViewModel.Factory> viewModelFactoryProvider) {
    return new MaterialEditFragment_MembersInjector(
        childFragmentInjectorProvider,
        analyticsHelperProvider,
        navigatorProvider,
        viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(MaterialEditFragment instance) {
    DaggerFragment_MembersInjector.injectChildFragmentInjector(
        instance, childFragmentInjectorProvider.get());
    injectAnalyticsHelper(instance, analyticsHelperProvider.get());
    injectNavigator(instance, navigatorProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  public static void injectAnalyticsHelper(
      MaterialEditFragment instance, AnalyticsHelper analyticsHelper) {
    instance.analyticsHelper = analyticsHelper;
  }

  public static void injectNavigator(MaterialEditFragment instance, Navigator navigator) {
    instance.navigator = navigator;
  }

  public static void injectViewModelFactory(
      MaterialEditFragment instance, MaterialEditViewModel.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}
