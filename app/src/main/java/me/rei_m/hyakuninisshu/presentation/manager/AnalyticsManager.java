package me.rei_m.hyakuninisshu.presentation.manager;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;

import me.rei_m.hyakuninisshu.App;

public class AnalyticsManager {

    public enum ScreenEvent {
        TRAINING_MENU("training_menu"),
        QUIZ_RESULT("quiz_result"),
        EXAM("exam"),
        EXAM_RESULT("exam_result"),
        MATERIAL("material"),
        SUPPORT("support");

        ScreenEvent(@NonNull String name) {
            this.name = name;
        }

        public final String name;
    }

    public enum ActionEvent {
        START_TRAINING("10", "start_training", CONTENT_TYPE_BUTTON),
        START_EXAM("11", "start_exam", CONTENT_TYPE_BUTTON),
        START_TRAINING_FOR_EXAM("12", "start_training_for_exam", CONTENT_TYPE_BUTTON),
        RESTART_TRAINING("13", "restart_training", CONTENT_TYPE_BUTTON),
        FINISH_TRAINING("14", "finish_training", CONTENT_TYPE_BUTTON);

        public final String id;

        public final String name;

        public final String contentType;

        ActionEvent(@NonNull String id,
                    @NonNull String name,
                    @NonNull String contentType) {
            this.id = id;
            this.name = name;
            this.contentType = contentType;
        }
    }

    private static final String CONTENT_TYPE_BUTTON = "button";

    private static final String ITEM_CATEGORY_SCREEN = "screen";

    private FirebaseAnalytics analytics;

    public void initialize(@NonNull App app) {
        analytics = FirebaseAnalytics.getInstance(app);
    }

    public void logScreenEvent(@NonNull ScreenEvent event) {
        if (analytics == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, ITEM_CATEGORY_SCREEN);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, event.name);
        analytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    public void logActionEvent(@NonNull ActionEvent event) {
        if (analytics == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, event.id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, event.name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, event.contentType);
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
