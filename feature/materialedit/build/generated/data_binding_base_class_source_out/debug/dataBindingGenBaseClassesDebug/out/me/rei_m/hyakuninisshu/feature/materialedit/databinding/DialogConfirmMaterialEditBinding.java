package me.rei_m.hyakuninisshu.feature.materialedit.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogConfirmMaterialEditBinding extends ViewDataBinding {
  @Bindable
  protected String mFirstPhraseKanji;

  @Bindable
  protected String mFirstPhraseKana;

  @Bindable
  protected String mSecondPhraseKanji;

  @Bindable
  protected String mSecondPhraseKana;

  @Bindable
  protected String mThirdPhraseKanji;

  @Bindable
  protected String mThirdPhraseKana;

  @Bindable
  protected String mFourthPhraseKanji;

  @Bindable
  protected String mFourthPhraseKana;

  @Bindable
  protected String mFifthPhraseKanji;

  @Bindable
  protected String mFifthPhraseKana;

  protected DialogConfirmMaterialEditBinding(Object _bindingComponent, View _root,
      int _localFieldCount) {
    super(_bindingComponent, _root, _localFieldCount);
  }

  public abstract void setFirstPhraseKanji(@Nullable String firstPhraseKanji);

  @Nullable
  public String getFirstPhraseKanji() {
    return mFirstPhraseKanji;
  }

  public abstract void setFirstPhraseKana(@Nullable String firstPhraseKana);

  @Nullable
  public String getFirstPhraseKana() {
    return mFirstPhraseKana;
  }

  public abstract void setSecondPhraseKanji(@Nullable String secondPhraseKanji);

  @Nullable
  public String getSecondPhraseKanji() {
    return mSecondPhraseKanji;
  }

  public abstract void setSecondPhraseKana(@Nullable String secondPhraseKana);

  @Nullable
  public String getSecondPhraseKana() {
    return mSecondPhraseKana;
  }

  public abstract void setThirdPhraseKanji(@Nullable String thirdPhraseKanji);

  @Nullable
  public String getThirdPhraseKanji() {
    return mThirdPhraseKanji;
  }

  public abstract void setThirdPhraseKana(@Nullable String thirdPhraseKana);

  @Nullable
  public String getThirdPhraseKana() {
    return mThirdPhraseKana;
  }

  public abstract void setFourthPhraseKanji(@Nullable String fourthPhraseKanji);

  @Nullable
  public String getFourthPhraseKanji() {
    return mFourthPhraseKanji;
  }

  public abstract void setFourthPhraseKana(@Nullable String fourthPhraseKana);

  @Nullable
  public String getFourthPhraseKana() {
    return mFourthPhraseKana;
  }

  public abstract void setFifthPhraseKanji(@Nullable String fifthPhraseKanji);

  @Nullable
  public String getFifthPhraseKanji() {
    return mFifthPhraseKanji;
  }

  public abstract void setFifthPhraseKana(@Nullable String fifthPhraseKana);

  @Nullable
  public String getFifthPhraseKana() {
    return mFifthPhraseKana;
  }

  @NonNull
  public static DialogConfirmMaterialEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_confirm_material_edit, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogConfirmMaterialEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogConfirmMaterialEditBinding>inflateInternal(inflater, me.rei_m.hyakuninisshu.feature.materialedit.R.layout.dialog_confirm_material_edit, root, attachToRoot, component);
  }

  @NonNull
  public static DialogConfirmMaterialEditBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_confirm_material_edit, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogConfirmMaterialEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogConfirmMaterialEditBinding>inflateInternal(inflater, me.rei_m.hyakuninisshu.feature.materialedit.R.layout.dialog_confirm_material_edit, null, false, component);
  }

  public static DialogConfirmMaterialEditBinding bind(@NonNull View view) {
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
  public static DialogConfirmMaterialEditBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (DialogConfirmMaterialEditBinding)bind(component, view, me.rei_m.hyakuninisshu.feature.materialedit.R.layout.dialog_confirm_material_edit);
  }
}
