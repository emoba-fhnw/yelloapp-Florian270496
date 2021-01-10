package fhnw.emoba.yelloapp.ui


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.emoba.yelloapp.model.YelloAppModel

val padding = 6.dp

@Composable
fun YelloAppUI(model: YelloAppModel) {
    MaterialTheme() {
        with(model) {
            Scaffold(
                topBar      = { TopBar(model) },
                bodyContent = { Body(model) },
                bottomBar   = { BottomBar(model)}
            )
        }
    }
}

@Composable
private fun TopBar(model: YelloAppModel) {
    model.apply {
        Box(Modifier.background(Color(0xFF404040))) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 6.dp, end = 6.dp, top = 6.dp, bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Button(
                            onClick = { if (connected) disconnect() else connect() },
                            modifier = Modifier.padding(padding).width(120.dp),
                            colors = ButtonConstants.defaultButtonColors(backgroundColor = Color(0xFF425292), contentColor = Color.White)
                        ) {
                            Text(if (connected) "Disconnect" else "Connect")
                        }
                        Button(
                            onClick = { takeoff() },
                            enabled = available,
                            modifier = Modifier.padding(padding).width(120.dp),
                            colors = ButtonConstants.defaultButtonColors(backgroundColor = Color(0xFF425292), contentColor = Color.White)

                        ) {
                            Text("Takeoff")
                        }
                    }
                    Column {
                        Button(
                            onClick = { emergency() },
                            enabled = connected,
                            modifier = Modifier.padding(padding).width(120.dp),
                            colors = ButtonConstants.defaultButtonColors(backgroundColor = Color(0xFFE60000), contentColor = Color.White)
                        ) {
                            Text("Emergency")
                        }
                        Button(
                            onClick = { land() },
                            enabled = available,
                            modifier = Modifier.padding(padding).width(120.dp),
                            colors = ButtonConstants.defaultButtonColors(backgroundColor = Color(0xFF425292), contentColor = Color.White)

                        ) {
                            Text("Land")
                        }
                    }
                    Column {
                        Text(
                            text = battery.format("Battery: %.0f%%"),
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(padding),
                            color = Color.White
                        )
                        Text(
                            text = height.format("Height: %.1fcm"),
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(padding),
                            color = Color.White
                        )
                    }
                    Column {
                        Text(
                            text = speed.format("Speed: %.1fm/s"),
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(padding),
                            color = Color.White
                        )
                        Text(
//                        text = wifiStrength.format("Wifi: %.0f%% "),
                            text = "Time: ${flightTime}s",                    //        (this is the string version)
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(padding),
                            color = Color.White
                        )
                    }
                }
        }
    }
}

@Composable
private fun Body(model: YelloAppModel) {
    model.apply {
        Box(Modifier.fillMaxSize().padding(start = 6.dp, end = 6.dp, top = 35.dp, bottom = 0.dp)) {
            Column(Modifier.align(Alignment.TopCenter).fillMaxHeight(0.5f).fillMaxWidth(0.8f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FlightControlUI(
                        modifier = Modifier.size(168.dp),
                        isLeftControl = true,
                        flightControl = flightControlLeft,
                        onInput = {
                            flightControlLeft = it
                            fly()
                        }
                    )
                    FlightControlUI(
                        modifier = Modifier.size(168.dp),
                        isLeftControl = false,
                        flightControl = flightControlRight,
                        onInput = {
                            flightControlRight = it
                            fly()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomBar(model: YelloAppModel) {
    model.apply {
        Box(Modifier.fillMaxSize().padding(start = 120.dp, end = 120.dp, top = 6.dp, bottom = 6.dp)) {
            Column(Modifier.align(Alignment.BottomStart).align(Alignment.Center)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { flip('f') },
                        enabled = available,
                        modifier = Modifier.padding(padding).width(90.dp),
                        colors = ButtonConstants.defaultButtonColors(backgroundColor = Color(0xFF425292), contentColor = Color.White)

                    ) {
                        Text("Flip f")
                    }
                    Button(
                        onClick = { flip('b') },
                        enabled = available,
                        modifier = Modifier.padding(padding).width(90.dp),
                        colors = ButtonConstants.defaultButtonColors(backgroundColor = Color(0xFF425292), contentColor = Color.White)

                    ) {
                        Text("Flip b")
                    }
                    Button(
                        onClick = { flip('l') },
                        enabled = available,
                        modifier = Modifier.padding(padding).width(90.dp),
                        colors = ButtonConstants.defaultButtonColors(backgroundColor = Color(0xFF425292), contentColor = Color.White)

                    ) {
                        Text("Flip l")
                    }
                    Button(
                        onClick = { flip('r') },
                        enabled = available,
                        modifier = Modifier.padding(padding).width(90.dp),
                        colors = ButtonConstants.defaultButtonColors(backgroundColor = Color(0xFF425292), contentColor = Color.White)

                    ) {
                        Text("Flip r")
                    }
                }
            }
        }
    }
}

private fun Float.format(pattern: String): String = String.format(pattern, this)