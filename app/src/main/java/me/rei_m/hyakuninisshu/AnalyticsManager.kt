/*
 * Copyright (c) 2017. Rei Matsushita
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package me.rei_m.hyakuninisshu

import android.content.Context
import android.os.Bundle

import com.google.firebase.analytics.FirebaseAnalytics

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsManager @Inject constructor(context: Context) {

    private val analytics = FirebaseAnalytics.getInstance(context)

    fun logScreenEvent(event: ScreenEvent) {
        if (analytics == null) {
            return
        }
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_CATEGORY, ITEM_CATEGORY_SCREEN)
            putString(FirebaseAnalytics.Param.ITEM_ID, event.eventName)
            putString(FirebaseAnalytics.Param.ITEM_NAME, event.eventName)
        }
        analytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle)
    }

    fun logActionEvent(event: ActionEvent) {
        if (analytics == null) {
            return
        }
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, event.id)
            putString(FirebaseAnalytics.Param.ITEM_NAME, event.eventName)
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, event.contentType)
        }
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    enum class ScreenEvent(val eventName: String) {
        TRAINING_MENU("training_menu"),
        QUIZ_RESULT("quiz_result"),
        EXAM("exam"),
        EXAM_RESULT("exam_result"),
        MATERIAL("material"),
        SUPPORT("support")
    }

    enum class ActionEvent(val id: String,
                           val eventName: String,
                           val contentType: String) {
        START_TRAINING("10", "start_training", CONTENT_TYPE_BUTTON),
        START_EXAM("11", "start_exam", CONTENT_TYPE_BUTTON),
        START_TRAINING_FOR_EXAM("12", "start_training_for_exam", CONTENT_TYPE_BUTTON),
        RESTART_TRAINING("13", "restart_training", CONTENT_TYPE_BUTTON),
        FINISH_TRAINING("14", "finish_training", CONTENT_TYPE_BUTTON),
        FINISH_EXAM("15", "finish_exam", CONTENT_TYPE_BUTTON)
    }

    companion object {
        private const val CONTENT_TYPE_BUTTON = "button"

        private const val ITEM_CATEGORY_SCREEN = "screen"
    }
}
