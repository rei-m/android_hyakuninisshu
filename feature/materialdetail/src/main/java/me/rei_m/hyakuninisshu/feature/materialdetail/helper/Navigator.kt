package me.rei_m.hyakuninisshu.feature.materialdetail.helper

import androidx.appcompat.app.AppCompatActivity
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditActivity
import javax.inject.Inject

@ActivityScope
class Navigator @Inject constructor (private val activity: AppCompatActivity) {
    fun navigateToMaterialEdit(karutaId: KarutaIdentifier) {
        val intentToLaunch = MaterialEditActivity.createIntent(activity, karutaId)
        activity.startActivity(intentToLaunch)
    }
}
