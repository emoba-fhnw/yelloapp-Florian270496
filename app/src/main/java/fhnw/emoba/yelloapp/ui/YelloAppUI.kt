package fhnw.emoba.yelloapp.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.emoba.R
import fhnw.emoba.yelloapp.model.YelloAppModel
import fhnw.emoba.yelloapp.ui.theme.YelloAppTheme

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
        Box(Modifier.padding(start = 6.dp, end = 6.dp, top = 6.dp, bottom = 0.dp)) {
            Column(Modifier.align(Alignment.TopStart)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { if (connected) disconnect() else connect() },
                        modifier = Modifier.padding(padding).width(120.dp),
                        colors = ButtonConstants.defaultButtonColors(backgroundColor = Color(0xFF425292), contentColor = Color.White)
                    ) {
                        Text(if (connected) "Disconnect" else "Connect")
                    }
                    Button(
                        onClick = { emergency() },
                        enabled = connected,
                        modifier = Modifier.padding(padding).width(120.dp),
                        colors = ButtonConstants.defaultButtonColors(backgroundColor = Color(0xFFE60000), contentColor = Color.White)
                    ) {
                        Text("Emergency")
                    }
                    Text(
                        text = battery.format("Battery: %.0f%%"),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(padding),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = speed.format("Speed: %.1fm/s"),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(padding),
                        textAlign = TextAlign.Center
                    )
                }
                Divider(modifier = Modifier.padding(bottom = 3.dp, top = 3.dp), Color.LightGray)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { takeoff() },
                        enabled = available,
                        modifier = Modifier.padding(padding).width(120.dp),
                        colors = ButtonConstants.defaultButtonColors(backgroundColor = Color(0xFF425292), contentColor = Color.White)

                    ) {
                        Text("Takeoff")
                    }
                    Button(
                        onClick = { land() },
                        enabled = available,
                        modifier = Modifier.padding(padding).width(120.dp),
                        colors = ButtonConstants.defaultButtonColors(backgroundColor = Color(0xFF425292), contentColor = Color.White)

                    ) {
                        Text("Land")
                    }
                    Text(
                        text = height.format("Height: %.1fcm"),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(padding)
                    )
                    Text(
//                        text = wifiStrength.format("Wifi: %.0f%% "),
                        text = "Time: ${flightTime}s",                    //        (this is the string version)
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(padding)
                    )
                }

            }
        }
    }
}

@Composable
private fun Body(model: YelloAppModel) {
    model.apply {
        Box(Modifier.fillMaxSize().padding(start = 6.dp, end = 6.dp, top = 35.dp, bottom = 0.dp)){
            if(connected){
                Box(Modifier.align(Alignment.TopCenter).fillMaxHeight(0.5f).fillMaxWidth(0.8f)){
                    Column(Modifier.align(Alignment.TopStart)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            val imgAsset = imageResource(id = R.drawable.joystick1)
                            Image(imgAsset)
                            val imgAsset2 = imageResource(id = R.drawable.joystick2)
                            Image(imgAsset2)
                        }
                    }
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

private fun Float.format(pattern: String):String =  String.format(pattern, this)