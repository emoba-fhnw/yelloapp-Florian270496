package fhnw.emoba.yelloapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fhnw.emoba.yelloapp.ui.theme.padSmall

@Composable
fun StatusText(
    text : String,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(padSmall),
        color = MaterialTheme.colors.onBackground
    )
}
