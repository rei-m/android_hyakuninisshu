package me.rei_m.hyakuninisshu.domain.util.rx

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import me.rei_m.hyakuninisshu.util.rx.SchedulerProvider

class TestSchedulerProvider : SchedulerProvider {
    override fun ui(): Scheduler = Schedulers.trampoline()

    override fun computation(): Scheduler = Schedulers.trampoline()

    override fun new(): Scheduler = Schedulers.trampoline()

    override fun io(): Scheduler = Schedulers.trampoline()
}
