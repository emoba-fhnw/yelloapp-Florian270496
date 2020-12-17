package fhnw.emoba.yelloapp.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import fhnw.emoba.yelloapp.model.YelloAppModel

@Composable
fun YelloAppUI(model: YelloAppModel){
    Text(model.title, style = TextStyle(fontSize = 28.sp))
}

