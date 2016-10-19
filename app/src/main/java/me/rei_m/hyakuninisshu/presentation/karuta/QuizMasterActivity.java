package me.rei_m.hyakuninisshu.presentation.karuta;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.ActivityQuizMasterBinding;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.BaseActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.KarutaQuizViewModel;

public class QuizMasterActivity extends BaseActivity implements QuizMasterContact.View {

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, QuizMasterActivity.class);
    }

    @Inject
    ActivityNavigator activityNavigator;

    @Inject
    QuizMasterContact.Actions presenter;

    private ActivityQuizMasterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_master);

        presenter.onCreate(this, savedInstanceState);
    }

    @Override
    public void startQuiz(KarutaQuizViewModel viewModel) {
        binding.setViewModel(viewModel);
    }
}
