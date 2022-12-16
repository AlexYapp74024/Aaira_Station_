@file:OptIn(ExperimentalMaterialApi::class)

package com.example.forage.core.ui_util

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun <T> ExposedDropdown(
    options: List<T>,
    modifier: Modifier = Modifier,
    displayName: (T) -> String = { it.toString() },
    onSelect: (T) -> Unit,
    label: @Composable (() -> Unit)?,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(displayName(options.first())) }

    ExposedDropdownContainer(
        modifier = modifier,
        selectedOptionText = selectedOptionText,
        expanded = expanded,
        changeExpand = { expanded = it },
        label = label,
    ) {
        options.forEach { selectionOption ->
            DropdownMenuItem(
                onClick = {
                    onSelect(selectionOption)
                    expanded = false
                }
            ) {
                Text(text = displayName(selectionOption))
            }
        }
    }
}

@Composable
fun <T> ExposedDropdownCanAddNewItem(
    options: List<T>,
    addNewItemPrompt: String,
    modifier: Modifier = Modifier,
    listItemToString: (T) -> String = { it.toString() },
    onSelect: (T) -> Unit,
    onAddNewItem: (String) -> Unit = {},
    label: @Composable (() -> Unit)?,
    value: T? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedOptionText =
        if (options.isEmpty())
            "(New)"
        else
            listItemToString(value ?: options.first())


    var showTextInputDialog by remember { mutableStateOf(false) }

    if (showTextInputDialog) {
        var textInput by remember { mutableStateOf("") }

        TextInputDialog(
            dismissButton = {},
            onDismissRequest = { showTextInputDialog = false },
            title = { Text(text = addNewItemPrompt) },
            value = textInput,
            onValueChange = { textInput = it },
            confirmButton = {
                Button(onClick = {
                    showTextInputDialog = false
                    onAddNewItem(textInput)
                }) {
                    Text(text = "Add")
                }
            },
        )
    }

    ExposedDropdownContainer(
        modifier = modifier,
        selectedOptionText = selectedOptionText,
        expanded = expanded,
        changeExpand = { expanded = it },
        label = label,
    ) {
        options.forEach { selectionOption ->
            DropdownMenuItem(
                onClick = {
                    onSelect(selectionOption)
                    expanded = false
                }
            ) {
                Text(text = listItemToString(selectionOption))
            }
        }

        DropdownMenuItem(
            onClick = {
                showTextInputDialog = true
                expanded = false
            }
        ) {
            Text(text = "(New)")
        }
    }
}

@Composable
private fun ExposedDropdownContainer(
    modifier: Modifier = Modifier,
    selectedOptionText: String,
    expanded: Boolean,
    changeExpand: (Boolean) -> Unit,
    label: @Composable (() -> Unit)?,
    content: @Composable (ColumnScope.() -> Unit),
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            changeExpand(!expanded)
        }
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = label,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = {
                changeExpand(false)
            },
            content = content
        )
    }
}