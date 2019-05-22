package me.rei_m.hyakuninisshu.feature.materialedit.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;
import me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditViewModel;

public abstract class FragmentMaterialEditBinding extends ViewDataBinding {
  @NonNull
  public final Button buttonEditMaterial;

  @NonNull
  public final EditText editFifthPhraseKana;

  @NonNull
  public final EditText editFifthPhraseKanji;

  @NonNull
  public final EditText editFirstPhraseKana;

  @NonNull
  public final EditText editFirstPhraseKanji;

  @NonNull
  public final EditText editFourthPhraseKana;

  @NonNull
  public final EditText editFourthPhraseKanji;

  @NonNull
  public final EditText editSecondPhraseKana;

  @NonNull
  public final EditText editSecondPhraseKanji;

  @NonNull
  public final EditText editThirdPhraseKana;

  @NonNull
  public final EditText editThirdPhraseKanji;

  @NonNull
  public final TextView textCreator;

  @NonNull
  public final TextView textCreatorTitle;

  @NonNull
  public final TextView textEditExplain;

  @NonNull
  public final TextView textFifthPhraseTitle;

  @NonNull
  public final TextView textFirstPhraseTitle;

  @NonNull
  public final TextView textFourthPhraseTitle;

  @NonNull
  public final TextView textSecondPhraseTitle;

  @NonNull
  public final TextView textThirdPhraseTitle;

  @Bindable
  protected MaterialEditViewModel mViewModel;

  protected FragmentMaterialEditBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button buttonEditMaterial, EditText editFifthPhraseKana, EditText editFifthPhraseKanji,
      EditText editFirstPhraseKana, EditText editFirstPhraseKanji, EditText editFourthPhraseKana,
      EditText editFourthPhraseKanji, EditText editSecondPhraseKana, EditText editSecondPhraseKanji,
      EditText editThirdPhraseKana, EditText editThirdPhraseKanji, TextView textCreator,
      TextView textCreatorTitle, TextView textEditExplain, TextView textFifthPhraseTitle,
      TextView textFirstPhraseTitle, TextView textFourthPhraseTitle, TextView textSecondPhraseTitle,
      TextView textThirdPhraseTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.buttonEditMaterial = buttonEditMaterial;
    this.editFifthPhraseKana = editFifthPhraseKana;
    this.editFifthPhraseKanji = editFifthPhraseKanji;
    this.editFirstPhraseKana = editFirstPhraseKana;
    this.editFirstPhraseKanji = editFirstPhraseKanji;
    this.editFourthPhraseKana = editFourthPhraseKana;
    this.editFourthPhraseKanji = editFourthPhraseKanji;
    this.editSecondPhraseKana = editSecondPhraseKana;
    this.editSecondPhraseKanji = editSecondPhraseKanji;
    this.editThirdPhraseKana = editThirdPhraseKana;
    this.editThirdPhraseKanji = editThirdPhraseKanji;
    this.textCreator = textCreator;
    this.textCreatorTitle = textCreatorTitle;
    this.textEditExplain = textEditExplain;
    this.textFifthPhraseTitle = textFifthPhraseTitle;
    this.textFirstPhraseTitle = textFirstPhraseTitle;
    this.textFourthPhraseTitle = textFourthPhraseTitle;
    this.textSecondPhraseTitle = textSecondPhraseTitle;
    this.textThirdPhraseTitle = textThirdPhraseTitle;
  }

  public abstract void setViewModel(@Nullable MaterialEditViewModel viewModel);

  @Nullable
  public MaterialEditViewModel getViewModel() {
    return mViewModel;
  }

  @NonNull
  public static FragmentMaterialEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_material_edit, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentMaterialEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentMaterialEditBinding>inflateInternal(inflater, me.rei_m.hyakuninisshu.feature.materialedit.R.layout.fragment_material_edit, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentMaterialEditBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_material_edit, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentMaterialEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentMaterialEditBinding>inflateInternal(inflater, me.rei_m.hyakuninisshu.feature.materialedit.R.layout.fragment_material_edit, null, false, component);
  }

  public static FragmentMaterialEditBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static FragmentMaterialEditBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentMaterialEditBinding)bind(component, view, me.rei_m.hyakuninisshu.feature.materialedit.R.layout.fragment_material_edit);
  }
}
