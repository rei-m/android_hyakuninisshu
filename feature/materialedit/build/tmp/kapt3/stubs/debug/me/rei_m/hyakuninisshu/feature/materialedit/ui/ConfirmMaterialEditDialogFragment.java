package me.rei_m.hyakuninisshu.feature.materialedit.ui;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u000e2\u00020\u0001:\u0002\u000e\u000fB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\b\u0010\r\u001a\u00020\u0006H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lme/rei_m/hyakuninisshu/feature/materialedit/ui/ConfirmMaterialEditDialogFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", "listener", "Lme/rei_m/hyakuninisshu/feature/materialedit/ui/ConfirmMaterialEditDialogFragment$OnDialogInteractionListener;", "onAttach", "", "context", "Landroid/content/Context;", "onCreateDialog", "Landroid/app/Dialog;", "savedInstanceState", "Landroid/os/Bundle;", "onDetach", "Companion", "OnDialogInteractionListener", "materialedit_debug"})
public final class ConfirmMaterialEditDialogFragment extends androidx.fragment.app.DialogFragment {
    private me.rei_m.hyakuninisshu.feature.materialedit.ui.ConfirmMaterialEditDialogFragment.OnDialogInteractionListener listener;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TAG = "ConfirmMaterialEditDialogFragment";
    private static final java.lang.String ARG_FIRST_KANJI = "firstKanji";
    private static final java.lang.String ARG_FIRST_KANA = "firstKana";
    private static final java.lang.String ARG_SECOND_KANJI = "secondKanji";
    private static final java.lang.String ARG_SECOND_KANA = "secondKana";
    private static final java.lang.String ARG_THIRD_KANJI = "thirdKanji";
    private static final java.lang.String ARG_THIRD_KANA = "thirdKana";
    private static final java.lang.String ARG_FOURTH_KANJI = "fourthKanji";
    private static final java.lang.String ARG_FOURTH_KANA = "fourthKana";
    private static final java.lang.String ARG_FIFTH_KANJI = "fifthKanji";
    private static final java.lang.String ARG_FIFTH_KANA = "fifthKana";
    public static final me.rei_m.hyakuninisshu.feature.materialedit.ui.ConfirmMaterialEditDialogFragment.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public android.app.Dialog onCreateDialog(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onAttach(@org.jetbrains.annotations.Nullable()
    android.content.Context context) {
    }
    
    @java.lang.Override()
    public void onDetach() {
    }
    
    public ConfirmMaterialEditDialogFragment() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&\u00a8\u0006\u0005"}, d2 = {"Lme/rei_m/hyakuninisshu/feature/materialedit/ui/ConfirmMaterialEditDialogFragment$OnDialogInteractionListener;", "", "onClickBack", "", "onClickUpdate", "materialedit_debug"})
    public static abstract interface OnDialogInteractionListener {
        
        public abstract void onClickUpdate();
        
        public abstract void onClickBack();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002JV\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lme/rei_m/hyakuninisshu/feature/materialedit/ui/ConfirmMaterialEditDialogFragment$Companion;", "", "()V", "ARG_FIFTH_KANA", "", "ARG_FIFTH_KANJI", "ARG_FIRST_KANA", "ARG_FIRST_KANJI", "ARG_FOURTH_KANA", "ARG_FOURTH_KANJI", "ARG_SECOND_KANA", "ARG_SECOND_KANJI", "ARG_THIRD_KANA", "ARG_THIRD_KANJI", "TAG", "newInstance", "Lme/rei_m/hyakuninisshu/feature/materialedit/ui/ConfirmMaterialEditDialogFragment;", "firstKanji", "firstKana", "secondKanji", "secondKana", "thirdKanji", "thirdKana", "fourthKanji", "fourthKana", "fifthKanji", "fifthKana", "materialedit_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final me.rei_m.hyakuninisshu.feature.materialedit.ui.ConfirmMaterialEditDialogFragment newInstance(@org.jetbrains.annotations.NotNull()
        java.lang.String firstKanji, @org.jetbrains.annotations.NotNull()
        java.lang.String firstKana, @org.jetbrains.annotations.NotNull()
        java.lang.String secondKanji, @org.jetbrains.annotations.NotNull()
        java.lang.String secondKana, @org.jetbrains.annotations.NotNull()
        java.lang.String thirdKanji, @org.jetbrains.annotations.NotNull()
        java.lang.String thirdKana, @org.jetbrains.annotations.NotNull()
        java.lang.String fourthKanji, @org.jetbrains.annotations.NotNull()
        java.lang.String fourthKana, @org.jetbrains.annotations.NotNull()
        java.lang.String fifthKanji, @org.jetbrains.annotations.NotNull()
        java.lang.String fifthKana) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}