package fhnw.emoba.yelloapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fhnw.emoba.yelloapp.model.YelloAppModel

val padding = 6.dp

@Composable
fun YelloAppUI(model: YelloAppModel){
    MaterialTheme() {
        with(model){
            Box(Modifier.fillMaxSize().padding(padding)){
                Column(Modifier.align(Alignment.TopStart)) {
                    Row {
                        Text(text     = battery.format("Battery: %.0f%%"),
                            style    = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(padding))
                        Text(text     = wifiStrength.format("Wifi: %.0f%% "),
                            style    = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(padding))
                    }
                    Row {
                        Text(text     = speed.format("Speed: %.1fm/s"),
                            style    = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(padding))
                        Text(text     = height.format("Height: %.1fcm"),
                            style    = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(padding))
                    }
                    Row {
                        Button(onClick  = { if (connected) disconnect() else connect() },
                            modifier = Modifier.padding(padding)) {
                            Text(if (connected) "Disconnect" else "Connect")
                        }
                        Button(onClick  = { emergency() },
                            enabled = connected,
                            modifier = Modifier.padding(padding),
                            colors = ButtonConstants.defaultButtonColors(backgroundColor = Color.Red)) {
                            Text("Emergency")
                        }
                    }
                }

                if(connected){
                    Box(Modifier.align(Alignment.Center).fillMaxHeight(0.5f).fillMaxWidth(0.8f)){
                        Column(Modifier.padding(padding).fillMaxWidth()) {
                            Text("left <-> right")
                            Slider(value            = leftRight.toFloat(),
                                valueRange       = -100f..100f,
                                onValueChange    = { updateLeftRight(it.toInt()) },
                                onValueChangeEnd = { stop() },
                            )

                            Text("backward <-> forward")
                            Slider(value            = forwardBackward.toFloat(),
                                valueRange       = -100f..100f,
                                onValueChange    = { updateForwardBackward(it.toInt()) },
                                onValueChangeEnd = { stop() },
                            )

                            Text("down <-> up")
                            Slider(value            = upwardDownward.toFloat(),
                                valueRange       = -100f..100f,
                                onValueChange    = { updateUpwardDownward(it.toInt()) },
                                onValueChangeEnd = { stop() },
                            )

                            Text("rotate left <-> rotate right")
                            Slider(value            = rotateLeftRight.toFloat(),
                                valueRange       = -100f..100f,
                                onValueChange    = { updateRotateLeftRight(it.toInt()) },
                                onValueChangeEnd = { stop() },
                            )
                        }
                    }
                }

                Column(Modifier.align(Alignment.BottomStart)) {
                    Button(onClick  = { takeoff() },
                        enabled  = available,
                        modifier = Modifier.padding(padding)) {
                        Text("Takeoff")
                    }
                    Button(onClick  = { land() },
                        enabled  = available,
                        modifier = Modifier.padding(padding)) {
                        Text("Land")
                    }
                }

                Column(Modifier.align(Alignment.BottomCenter)) {
                    Button(onClick  = { flip('f') },
                        enabled  = available,
                        modifier = Modifier.padding(padding)) {
                        Text("Flip f")
                    }
                    Button(onClick  = { flip('b') },
                        enabled  = available,
                        modifier = Modifier.padding(padding)) {
                        Text("Flip b")
                    }
                }

                Column(Modifier.align(Alignment.BottomEnd)) {
                    Button(onClick  = { flip('l') },
                        enabled  = available,
                        modifier = Modifier.padding(padding)) {
                        Text("Flip l")
                    }
                    Button(onClick  = { flip('r') },
                        enabled  = available,
                        modifier = Modifier.padding(padding)) {
                        Text("Flip r")
                    }
                }
            }
        }
    }
}

private fun Float.format(pattern: String):String =  String.format(pattern, this)


