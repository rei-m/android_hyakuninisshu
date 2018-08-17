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

import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import me.rei_m.hyakuninisshu.presentation.widget.dialog.AlertDialogFragment

fun AppCompatActivity.addFragment(@IdRes containerViewId: Int,
                                  fragment: Fragment,
                                  tag: String) {
    supportFragmentManager
        .beginTransaction()
        .add(containerViewId, fragment, tag)
        .commit()
}

fun AppCompatActivity.replaceFragment(@IdRes containerViewId: Int,
                                      fragment: Fragment,
                                      tag: String,
                                      transition: Int? = null) {
    val transaction = supportFragmentManager.beginTransaction()
    transition?.let { transaction.setTransition(it) }
    transaction
        .replace(containerViewId, fragment, tag)
        .commit()
}

fun AppCompatActivity.setupActionBar(toolbar: Toolbar, action: ActionBar.() -> Unit) {
    setSupportActionBar(toolbar)
    supportActionBar?.run {
        action()
    }
}

fun AppCompatActivity.showAlertDialog(@StringRes titleId: Int, @StringRes messageId: Int) {
    if (supportFragmentManager.findFragmentByTag(AlertDialogFragment.TAG) == null) {
        AlertDialogFragment.newInstance(
            titleId,
            messageId,
            true,
            false).show(supportFragmentManager, AlertDialogFragment.TAG)
    }
}
