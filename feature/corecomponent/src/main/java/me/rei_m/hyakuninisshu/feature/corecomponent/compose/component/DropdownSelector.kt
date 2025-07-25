/*
 * Copyright (c) 2025. Rei Matsushita
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

package me.rei_m.hyakuninisshu.feature.corecomponent.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.rei_m.hyakuninisshu.feature.corecomponent.compose.theme.HyakuninisshuTheme
import me.rei_m.hyakuninisshu.feature.corecomponent.compose.theme.WhiteSmoke

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownSelector(
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    itemContent: @Composable (T) -> String = { it.toString() }
) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            value = selectedItem?.let { itemContent(it) } ?: "",
            onValueChange = {},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .background(WhiteSmoke),
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                containerColor = WhiteSmoke
            )
        )
        
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(itemContent(item)) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownSelectorPreview() {
    HyakuninisshuTheme {
        val items = listOf("Item 1", "Item 2", "Item 3")
        var selectedItem by remember { mutableStateOf(items[0]) }
        
        DropdownSelector(
            items = items,
            selectedItem = selectedItem,
            onItemSelected = { selectedItem = it },
            label = "Select an item"
        )
    }
}