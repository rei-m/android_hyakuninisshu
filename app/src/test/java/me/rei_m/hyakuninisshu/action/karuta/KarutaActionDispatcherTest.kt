package me.rei_m.hyakuninisshu.action.karuta

import com.nhaarman.mockito_kotlin.check
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.util.rx.TestSchedulerProvider
import me.rei_m.hyakuninisshu.helper.TestHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class KarutaActionDispatcherTest : TestHelper {

    private lateinit var actionDispatcher: KarutaActionDispatcher

    private lateinit var karutaRepository: KarutaRepository

    private lateinit var dispatcher: Dispatcher

    @Before
    fun setUp() {
        dispatcher = mock {}
        karutaRepository = mock {}
        actionDispatcher = KarutaActionDispatcher(
                karutaRepository,
                dispatcher,
                TestSchedulerProvider()
        )
    }

    @Test
    fun fetch() {
        val karuta = createKaruta(id = 1)

        whenever(karutaRepository.findBy(karuta.identifier())).thenReturn(Single.just(karuta))

        actionDispatcher.fetch(karuta.identifier())

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchKarutaAction::class.java)
            if (it is FetchKarutaAction) {
                assertThat(it.karuta).isEqualTo(karuta)
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun fetchWithTest() {
        whenever(karutaRepository.findBy(KarutaIdentifier(1))).thenReturn(Single.error(RuntimeException()))

        actionDispatcher.fetch(KarutaIdentifier(1))

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchKarutaAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }
}
