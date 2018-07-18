package me.rei_m.hyakuninisshu.presentation.core

import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier

interface CoreInteractionListener {
    fun onAnswered(quizId: KarutaQuizIdentifier)

    fun onGoToNext()

    fun onGoToResult()
}
