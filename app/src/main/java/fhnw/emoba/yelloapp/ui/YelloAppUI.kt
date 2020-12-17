package fhnw.emoba.yelloapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import fhnw.emoba.yelloapp.model.YelloAppModel


@Composable
fun YelloAppUI(model: YelloAppModel){
    MaterialTheme() {
        with(model){
            Box(Modifier.fillMaxSize()){
                Text(text     = height.format("h: %.1f cm"),
                    style    = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.TopEnd))
                Button(onClick  = { forward() },
                    enabled  = available,
                    modifier = Modifier.align(Alignment.TopStart)) {
                    Text("Flip")
                }
                Button(onClick  = { connect() },
                    enabled  = !connected,
                    modifier = Modifier.align(Alignment.TopCenter)) {
                    Text("Connect")
                }
                Button(onClick  = { takeoff() },
                    enabled  = available,
                    modifier = Modifier.align(Alignment.BottomStart)) {
                    Text("Takeoff")
                }
                Button(onClick  = { land() },
                    enabled  = available,
                    modifier = Modifier.align(Alignment.BottomCenter)) {
                    Text("Land")
                }
                Button(onClick  = { forward() },
                    enabled  = available,
                    modifier = Modifier.align(Alignment.BottomEnd)) {
                    Text("Forward")
                }
                if(connected){
                    Box(Modifier.align(Alignment.Center).fillMaxHeight(0.5f).fillMaxWidth(0.8f)){
                        Slider(value            = leftRight.toFloat(),
                            valueRange       = -100f..100f,
                            onValueChange    = { updateLeftRight(it.toInt()) },
                            onValueChangeEnd = { stop() },
                            modifier         = Modifier.align(Alignment.TopCenter)
                        )

                        Slider(value            = forwardBackward.toFloat(),
                            valueRange       = -100f..100f,
                            onValueChange    = { updateForwardBackward(it.toInt()) },
                            onValueChangeEnd = { stop() },
                            modifier         = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}

private fun Float.format(pattern: String):String =  String.format(pattern, this)

