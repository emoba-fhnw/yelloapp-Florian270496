package fhnw.emoba.yelloapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.TextUnit
import fhnw.emoba.yelloapp.ui.theme.myFontSize
import fhnw.emoba.yelloapp.ui.theme.myFontStyle
import fhnw.emoba.yelloapp.ui.theme.myTextColor
import fhnw.emoba.yelloapp.ui.theme.padSmall

@Composable
fun StatusText(
    text : String,
    fontSize : TextUnit = myFontSize,
    fontStyle : FontStyle = myFontStyle,
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontStyle = fontStyle,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(padSmall),
        color = myTextColor
    )
}
