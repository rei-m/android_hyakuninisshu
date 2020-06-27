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

package me.rei_m.hyakuninisshu.feature.corecomponent.ext

import android.text.InputType
import android.widget.AutoCompleteTextView
import me.rei_m.hyakuninisshu.feature.corecomponent.R
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.adapter.DropdownSelectAdapter

fun AutoCompleteTextView.setUpDropDown(itemList: List<String>) {
    inputType = InputType.TYPE_NULL
    keyListener = null

    val adapter = DropdownSelectAdapter(
        context,
        R.layout.dropdown_menu_popup_item,
        itemList
    )
    setAdapter(adapter)
}

fun AutoCompleteTextView.setUp(
    initialValue: String,
    onSelected: (position: Int) -> Unit
) {
    setText(initialValue, false)

    setOnItemClickListener { _, _, position, _ ->
        onSelected(position)
    }
}
