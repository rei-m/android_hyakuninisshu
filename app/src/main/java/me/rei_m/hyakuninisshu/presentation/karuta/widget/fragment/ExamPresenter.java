package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

public class ExamPresenter implements ExamContact.Actions {

    private ExamContact.View view;


    @Override
    public void onCreate(ExamContact.View view) {
        this.view = view;
    }

    @Override
    public void onClickStartExam() {
        view.navigateToExamMaster();
    }
}
