package com.gmail.in2horizon.aescore.views

import android.util.Log
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gmail.in2horizon.aescore.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MSpinner(
    items: List<Any>,initial:Any?=null, onSelect: (Any) -> Unit, label: String, enabled: Boolean =
        true
) {

    var expanded by remember { mutableStateOf(false) }

    var initialValue=""
    initial?.let { initialValue=it.toString() }
    Log.d("competitm",initial.toString())

    var teraz by remember {
        mutableStateOf(initialValue)
    }


    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
        expanded = !expanded

    }) {
        OutlinedTextField(
            enabled = enabled,
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            label = { Text(text = label) },
            value = teraz,//initialValue.toString(),
            onValueChange = { },
            trailingIcon = {
                TrailingIcon(
                    expanded = expanded
                )
            },
            //  colors = ExposedDropdownMenuDefaults.textFieldColors()
        )



        ExposedDropdownMenu(

            expanded = expanded && enabled,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach { it ->
                DropdownMenuItem(text = { Text(text = it.toString()) }, onClick = {
                    expanded = false
                //    initialValue=it.toString()
                        teraz=it.toString()
                    //    selectedText = it.toString();
                    onSelect(it)
                })
            }
        }


    }

}