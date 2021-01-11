package fhnw.emoba.yelloapp.ui


import android.graphics.drawable.Icon
import android.graphics.drawable.VectorDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.emoba.R
import fhnw.emoba.yelloapp.model.YelloAppModel

val padding = 6.dp
val btnWidth = 150.dp
val btnActiveColor = Color(0xFF425292)
val btnRedColor = Color(0xFFE60000)
val icnScale = 0.75f

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

    // State to manage if the alert dialog is showing or not.
    // Default is false (not showing)
    val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }

    model.apply {
        Box(Modifier.background(Color(0xFF404040))) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 6.dp, end = 6.dp, top = 6.dp, bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        TopButton(
                            onClick = { if (connected) disconnect() else setShowDialog(true) },
                            enabled = true,
                            iconVectorDrawable = if (connected) R.drawable.ic_icon_disconnect else R.drawable.ic_icon_connect,
                            text = if (connected) "Disconnect" else "Connect"
                        )

//                        Button(
//                            onClick = { if (connected) disconnect() else setShowDialog(true) },
//                            modifier = Modifier.padding(padding).width(btnWidth),
//                            colors = ButtonConstants.defaultButtonColors(backgroundColor = btnActiveColor, contentColor = Color.White)
//                        ) {
//                            Icon(vectorResource(R.drawable.ic_icon_connect), modifier = Modifier.scale(icnScale))
//                            Text(if (connected) "Disconnect" else "Connect")
//                        }
                        // Create alert dialog, pass the showDialog state to this Composable
                        DialogIpAddress(showDialog, model, setShowDialog)
                        TopButton(
                            onClick = { takeoff() },
                            enabled = available,
                            iconVectorDrawable = R.drawable.ic_icon_takeoff,
                            text = "Takeoff"
                        )
//                        Button(
//                            onClick = { takeoff() },
//                            enabled = available,
//                            modifier = Modifier.padding(padding).width(btnWidth),
//                            colors = ButtonConstants.defaultButtonColors(backgroundColor = btnActiveColor, contentColor = Color.White)
//
//                        ) {
//                            Icon(vectorResource(R.drawable.ic_icon_takeoff), modifier = Modifier.scale(icnScale))
//                            Text("Takeoff")
//                        }
                    }
                    Column {
                        TopButton(
                            onClick = { emergency() },
                            enabled = connected,
                            iconVectorDrawable = R.drawable.ic_icon_emergency,
                            backgroundColor = btnRedColor,
                            text = "Emergency"
                        )
//                        Button(
//                            onClick = { emergency() },
//                            enabled = connected,
//                            modifier = Modifier.padding(padding).width(btnWidth),
//                            colors = ButtonConstants.defaultButtonColors(backgroundColor = btnRedColor, contentColor = Color.White)
//                        ) {
//                            Text("Emergency")
//                        }
                        TopButton(
                            onClick = { land() },
                            enabled = available,
                            iconVectorDrawable = R.drawable.ic_icon_land,
                            text = "Land"
                        )
//                        Button(
//                            onClick = { land() },
//                            enabled = available,
//                            modifier = Modifier.padding(padding).width(btnWidth),
//                            colors = ButtonConstants.defaultButtonColors(backgroundColor = btnActiveColor, contentColor = Color.White)
//
//                        ) {
//                            Text("Land")
//                        }
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
        Box(Modifier.background(Color(0xFF5C5959))) {
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

@Composable
fun TopButton(
    onClick : () -> Unit,
    backgroundColor: Color = Color(0xFF425292),
    contentColor: Color = Color.White,
    enabled : Boolean,
    iconVectorDrawable : Int,
    text : String
) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(padding).width(150.dp),
        enabled = enabled,
        colors = ButtonConstants.defaultButtonColors(backgroundColor = backgroundColor, contentColor = contentColor)
    ) {
        Icon(vectorResource(iconVectorDrawable), modifier = Modifier.scale(0.75f))
        Text(text)
    }
}

@Composable
fun DialogIpAddress(showDialog: Boolean, model: YelloAppModel, setShowDialog: (Boolean) -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("Choose Drone (IP / Ports)")
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Change the state to close the dialog
                        setShowDialog(false)
                        model.connect(true)
                    },
                ) {
                    Text("Real")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Change the state to close the dialog
                        setShowDialog(false)
                        model.connect(false)
                    },
                ) {
                    Text("Simulator")
                }
            },
            text = {
                Column {
                    Text("Real drone:")
                    Text("   ${model.realIpAndPorts()}")
                    Text("Simulator drone:")
                    Text("   ${model.simulatorIpAndPorts()}")
                }
            },
        )
    }
}