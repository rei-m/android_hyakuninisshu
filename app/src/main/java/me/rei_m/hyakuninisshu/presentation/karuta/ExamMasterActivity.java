package me.rei_m.hyakuninisshu.presentation.karuta;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.ActivityExamMasterBinding;

public class ExamMasterActivity extends AppCompatActivity {

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, ExamMasterActivity.class);
    }

    private ActivityExamMasterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exam_master);

        setSupportActionBar(binding.toolbar);

        if (savedInstanceState == null) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
