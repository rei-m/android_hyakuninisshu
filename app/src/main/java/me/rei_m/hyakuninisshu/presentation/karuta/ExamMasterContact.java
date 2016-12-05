package me.rei_m.hyakuninisshu.presentation.karuta;

import me.rei_m.hyakuninisshu.domain.karuta.model.ExamIdentifier;

public interface ExamMasterContact {

    interface View {
        void startExam();

        void navigateToResult(ExamIdentifier examIdentifier);
    }

    interface Actions {
        void onCreate(View view);

        void onClickGoToResult();
    }
}
