/*
 * Copyright (c) 2020. Rei Matsushita.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package me.rei_m.hyakuninisshu.di

import androidx.savedstate.SavedStateRegistryOwner
import dagger.BindsInstance
import dagger.Subcomponent
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.feature.examhistory.di.ExamHistoryComponent
import me.rei_m.hyakuninisshu.feature.exammenu.di.ExamMenuComponent
import me.rei_m.hyakuninisshu.feature.examresult.di.ExamResultComponent
import me.rei_m.hyakuninisshu.feature.examstarter.di.ExamStarterComponent
import me.rei_m.hyakuninisshu.feature.material.di.MaterialComponent
import me.rei_m.hyakuninisshu.feature.materialedit.di.MaterialEditComponent
import me.rei_m.hyakuninisshu.feature.question.di.QuestionComponent
import me.rei_m.hyakuninisshu.feature.splash.di.SplashComponent
import me.rei_m.hyakuninisshu.feature.support.di.SupportComponent
import me.rei_m.hyakuninisshu.feature.trainingmenu.di.TrainingMenuComponent
import me.rei_m.hyakuninisshu.feature.trainingresult.di.TrainingResultComponent
import me.rei_m.hyakuninisshu.feature.trainingstarter.di.TrainingStarterComponent
import me.rei_m.hyakuninisshu.ui.MainActivity

@ActivityScope
@Subcomponent
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance owner: SavedStateRegistryOwner): MainComponent
    }

    fun inject(activity: MainActivity)

    fun splashComponent(): SplashComponent.Factory

    fun trainingMenuComponent(): TrainingMenuComponent.Factory

    fun examMenuComponent(): ExamMenuComponent.Factory

    fun materialComponent(): MaterialComponent.Factory

    fun supportComponent(): SupportComponent.Factory

    fun trainingStarterComponent(): TrainingStarterComponent.Factory

    fun questionComponent(): QuestionComponent.Factory

    fun trainingResultComponent(): TrainingResultComponent.Factory

    fun examStarterComponent(): ExamStarterComponent.Factory

    fun examResultComponent(): ExamResultComponent.Factory

    fun examHistoryComponent(): ExamHistoryComponent.Factory

    fun materialEditComponent(): MaterialEditComponent.Factory

    interface Injector : SplashComponent.Injector,
        TrainingMenuComponent.Injector,
        ExamMenuComponent.Injector,
        MaterialComponent.Injector,
        SupportComponent.Injector,
        TrainingStarterComponent.Injector,
        QuestionComponent.Injector,
        TrainingResultComponent.Injector,
        ExamStarterComponent.Injector,
        ExamResultComponent.Injector,
        ExamHistoryComponent.Injector,
        MaterialEditComponent.Injector {

        fun mainComponent(): MainComponent

        override fun splashComponent() = mainComponent().splashComponent()

        override fun trainingMenuComponent() = mainComponent().trainingMenuComponent()

        override fun examMenuComponent() = mainComponent().examMenuComponent()

        override fun materialComponent() = mainComponent().materialComponent()

        override fun supportComponent() = mainComponent().supportComponent()

        override fun trainingStarterComponent() = mainComponent().trainingStarterComponent()

        override fun questionComponent() = mainComponent().questionComponent()

        override fun trainingResultComponent() = mainComponent().trainingResultComponent()

        override fun examStarterComponent() = mainComponent().examStarterComponent()

        override fun examResultComponent() = mainComponent().examResultComponent()

        override fun examHistoryComponent() = mainComponent().examHistoryComponent()

        override fun materialEditComponent() = mainComponent().materialEditComponent()
    }
}
