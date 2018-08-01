package me.rei_m.hyakuninisshu.action.material

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Single
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.Color
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas
import me.rei_m.hyakuninisshu.domain.util.rx.TestSchedulerProvider
import me.rei_m.hyakuninisshu.helper.TestHelper
import me.rei_m.hyakuninisshu.presentation.enums.ColorFilter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class MaterialActionDispatcherTest : TestHelper {

    private lateinit var actionDispatcher: MaterialActionDispatcher

    private lateinit var karutaRepository: KarutaRepository

    private lateinit var dispatcher: Dispatcher

    @Before
    fun setUp() {
        dispatcher = mock {}
        karutaRepository = mock {}
        actionDispatcher = MaterialActionDispatcher(
                karutaRepository,
                dispatcher,
                TestSchedulerProvider()
        )
    }

    @Test
    fun fetch() {
        val karutas = Karutas(listOf(createKaruta(id = 1)))

        whenever(karutaRepository.list(Color.BLUE)).thenReturn(Single.just(karutas))

        actionDispatcher.fetch(ColorFilter.BLUE)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchMaterialAction::class.java)
            if (it is FetchMaterialAction) {
                assertThat(it.karutas).isEqualTo(karutas)
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun fetchWithError() {
        whenever(karutaRepository.list(Color.BLUE)).thenReturn(Single.error(RuntimeException()))

        actionDispatcher.fetch(ColorFilter.BLUE)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchMaterialAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }

    @Test
    fun startEdit() {
        val karuta = createKaruta(id = 1)

        whenever(karutaRepository.findBy(karuta.identifier())).thenReturn(Single.just(karuta))

        actionDispatcher.startEdit(karuta.identifier())

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartEditMaterialAction::class.java)
            if (it is StartEditMaterialAction) {
                assertThat(it.karuta).isEqualTo(karuta)
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun startEditWithError() {
        whenever(karutaRepository.findBy(KarutaIdentifier(1))).thenReturn(Single.error(RuntimeException()))

        actionDispatcher.startEdit(KarutaIdentifier(1))

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartEditMaterialAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }

    @Test
    fun edit() {
        val karuta = createKaruta(1)

        whenever(karutaRepository.findBy(karuta.identifier())).thenReturn(Single.just(karuta))
        whenever(karutaRepository.store(any())).thenReturn(Completable.complete())

        actionDispatcher.edit(
                karuta.identifier(),
                "初句改",
                "しょくかい",
                "二句改",
                "にくかい",
                "三句改",
                "さんくかい",
                "四句改",
                "よんくかい",
                "五句改",
                "ごくかい"
        )

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(EditMaterialAction::class.java)
            if (it is EditMaterialAction) {
                assertThat(it.karuta).isEqualTo(karuta)
                assertThat(it.karuta!!.kamiNoKu.first.kanji).isEqualTo("初句改")
                assertThat(it.karuta!!.kamiNoKu.first.kana).isEqualTo("しょくかい")
                assertThat(it.karuta!!.kamiNoKu.second.kanji).isEqualTo("二句改")
                assertThat(it.karuta!!.kamiNoKu.second.kana).isEqualTo("にくかい")
                assertThat(it.karuta!!.kamiNoKu.third.kanji).isEqualTo("三句改")
                assertThat(it.karuta!!.kamiNoKu.third.kana).isEqualTo("さんくかい")
                assertThat(it.karuta!!.shimoNoKu.fourth.kanji).isEqualTo("四句改")
                assertThat(it.karuta!!.shimoNoKu.fourth.kana).isEqualTo("よんくかい")
                assertThat(it.karuta!!.shimoNoKu.fifth.kanji).isEqualTo("五句改")
                assertThat(it.karuta!!.shimoNoKu.fifth.kana).isEqualTo("ごくかい")
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun editWithError() {
        val karuta = createKaruta(1)

        whenever(karutaRepository.findBy(karuta.identifier())).thenReturn(Single.just(karuta))
        whenever(karutaRepository.store(any())).thenReturn(Completable.error(RuntimeException()))

        actionDispatcher.edit(
                karuta.identifier(),
                "初句改",
                "しょくかい",
                "二句改",
                "にくかい",
                "三句改",
                "さんくかい",
                "四句改",
                "よんくかい",
                "五句改",
                "ごくかい"
        )

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(EditMaterialAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }
}
