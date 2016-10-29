package me.rei_m.hyakuninisshu.presentation.karuta;

import android.os.Bundle;

public interface QuizMasterContact {

    interface View {
        void startQuiz();
    }

    interface Actions {
        void onCreate(View view, Bundle savedInstanceState);

        void onAnswered(String quizId, boolean isCollect);
    }
}
