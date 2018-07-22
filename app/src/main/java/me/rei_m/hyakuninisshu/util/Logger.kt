package me.rei_m.hyakuninisshu.util

import me.rei_m.hyakuninisshu.action.Action
import timber.log.Timber

object Logger {
    fun action(a: Action) {
        if (a.error == null) {
            Timber.tag("Action").d("$a")
        } else {
            Timber.tag("Action").e("$a")
        }
    }
}
