package me.rei_m.hyakuninisshu.util

import me.rei_m.hyakuninisshu.action.Action
import timber.log.Timber

object Logger {
    fun action(a: Action) {
        Timber.tag("Action").d("$a")
    }
}
