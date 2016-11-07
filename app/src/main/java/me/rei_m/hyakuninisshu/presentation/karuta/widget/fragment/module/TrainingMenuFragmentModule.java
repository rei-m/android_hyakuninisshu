package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuContact;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuPresenter;

@Module
public class TrainingMenuFragmentModule {

    private final Context context;

    public TrainingMenuFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    TrainingMenuContact.Actions provideTrainingMenuPresenter() {
        return new TrainingMenuPresenter();
    }
}
