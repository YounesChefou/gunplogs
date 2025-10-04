package com.example.gunplogs.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gunplogs.R
import com.example.gunplogs.ui.theme.GunplogsTheme


@Composable
fun GunplogSearchBar(
    onSearchChange : (String) -> Unit,
) {
    var searchValue by rememberSaveable { mutableStateOf("") }

    OutlinedTextField (
        value = searchValue,
        onValueChange = {
            searchValue = it
            onSearchChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        label = { Text(stringResource(R.string.label_recherche)) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
    )

}

@Preview
@Composable
fun GunplogSearchBarPreview() {
    GunplogsTheme {
        GunplogSearchBar(
            onSearchChange = {}
        )
    }
}