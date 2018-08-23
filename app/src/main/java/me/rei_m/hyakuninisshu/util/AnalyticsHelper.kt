/*
 * Copyright (c) 2018. Rei Matsushita
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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.util

import android.app.Activity
import android.content.Context
import android.os.Bundle

import com.google.firebase.analytics.FirebaseAnalytics

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsHelper @Inject constructor(context: Context) {

    private val analytics = FirebaseAnalytics.getInstance(context)

    fun sendScreenView(screenName: String, activity: Activity) {
        val params = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, screenName)
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, CONTENT_TYPE_SCREEN_VIEW)
        }
        analytics.run {
            setCurrentScreen(activity, screenName, null)
            logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params)
        }
        Logger.analytics("Screen($screenName) recorded")
    }

    fun logActionEvent(event: ActionEvent) {
        val params = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, event.id)
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, CONTENT_TYPE_ACTION_EVENT)
            putString(KEY_ACTION, event.event)
        }
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params)
        Logger.analytics("$event recorded")
    }

    enum class ActionEvent(val id: String, val event: String) {
        START_TRAINING("10", "start_training"),
        START_EXAM("11", "start_exam"),
        START_TRAINING_FOR_EXAM("12", "start_training_for_exam"),
        RESTART_TRAINING("13", "restart_training"),
        FINISH_TRAINING("14", "finish_training"),
        FINISH_EXAM("15", "finish_exam");

        override fun toString(): String = "ActionEvent(id='$id', event='$event')"
    }

    companion object {
        private const val KEY_ACTION = "ui_action"
        private const val CONTENT_TYPE_SCREEN_VIEW = "screen"
        private const val CONTENT_TYPE_ACTION_EVENT = "action event"
    }
}
