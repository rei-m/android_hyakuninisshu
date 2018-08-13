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

package me.rei_m.hyakuninisshu.ext

import android.content.Context
import me.rei_m.hyakuninisshu.R
import java.util.*
import java.util.concurrent.TimeUnit

interface DateExt {

    fun Date.diffString(context: Context, now: Date): String {
        val diffMillSeconds = now.time - this.time

        val diffSeconds = TimeUnit.MILLISECONDS.toSeconds(diffMillSeconds)
        if (diffSeconds < COUNT_SECONDS_AT_MINUTE) {
            val displaySecond = if (diffSeconds < 0) {
                1
            } else {
                diffSeconds
            }
            return context.getString(R.string.past_second, displaySecond)
        }

        val diffMinutes = TimeUnit.SECONDS.toMinutes(diffSeconds)
        if (diffMinutes < COUNT_SECONDS_AT_MINUTE) {
            return context.getString(R.string.past_minute, diffMinutes)
        }

        val diffHours = TimeUnit.MINUTES.toHours(diffMinutes)
        if (diffHours < COUNT_HOURS_AT_DAY) {
            return context.getString(R.string.past_hour, diffHours)
        }

        val diffDays = TimeUnit.HOURS.toDays(diffHours)
        if (diffDays == 1L) {
            return context.getString(R.string.yesterday)
        } else if (diffDays < COUNT_DAYS_AT_WEEK) {
            return context.getString(R.string.past_day, diffDays)
        }

        val diffWeeks = diffDays / COUNT_DAYS_AT_WEEK
        if (diffWeeks == 1L) {
            return context.getString(R.string.last_week)
        } else if (diffWeeks < COUNT_WEEKS_AT_MONTH) {
            return context.getString(R.string.past_week, diffWeeks)
        }

        val diffMonths = diffDays / COUNT_DAYS_AT_MONTH
        if (diffMonths == 1L) {
            return context.getString(R.string.last_month)
        } else if (diffMonths < COUNT_MONTHS_AT_YEAR) {
            return context.getString(R.string.past_month, diffMonths)
        }

        val diffYears = diffMonths / COUNT_MONTHS_AT_YEAR
        return if (diffYears == 1L) {
            context.getString(R.string.last_year)
        } else {
            context.getString(R.string.past_year, diffYears)
        }
    }

    companion object {
        private const val COUNT_MONTHS_AT_YEAR = 12

        private const val COUNT_DAYS_AT_MONTH = 30

        private const val COUNT_WEEKS_AT_MONTH = 5

        private const val COUNT_DAYS_AT_WEEK = 7

        private const val COUNT_HOURS_AT_DAY = 24

        private const val COUNT_SECONDS_AT_MINUTE = 60
    }
}