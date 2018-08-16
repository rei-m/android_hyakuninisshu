package me.rei_m.hyakuninisshu.presentation.helper.bindingadapters

import android.databinding.BindingAdapter
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizJudgement
import me.rei_m.hyakuninisshu.presentation.widget.view.KarutaExamResultView

@BindingAdapter("examJudgements")
fun setKarutaExamResult(view: KarutaExamResultView,
                        judgements: List<KarutaQuizJudgement>?) {
    judgements ?: return
    view.setResult(judgements)
}
