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

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.DialogFragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

interface AppCompatActivityExt {
    fun <T : ViewModel> AppCompatActivity.obtainStore(viewModelClass: Class<T>, factory: ViewModelProvider.Factory) =
            ViewModelProviders.of(this, factory).get(viewModelClass)

    fun AppCompatActivity.setupActionBar(toolbar: Toolbar, action: ActionBar.() -> Unit) {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            action()
        }
    }

    fun AppCompatActivity.showDialogFragment(tag: String, dialog: () -> DialogFragment) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            dialog().show(supportFragmentManager, tag)
        }
    }
}
