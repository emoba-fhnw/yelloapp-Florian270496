package fhnw.emoba.yelloapp.ui


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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.emoba.R
import fhnw.emoba.yelloapp.model.YelloAppModel

// Some design constants:
val padSmall = 4.dp
val padding = 6.dp
val padDouble = 12.dp
val btnWidth = 150.dp
val btnHeight= 35.dp
val btnActiveColor = Color(0xFF425292)
val btnSecondColor= Color(0xFFAAA259)
val btnRedColor = Color(0xFFE60000)
const val iconScaleDefault = 1.0f
val defaultFontSize = 18.sp
val defaultFontStyle = FontStyle.Italic


@Composable
fun YelloAppUI(model: YelloAppModel) {
    MaterialTheme {
        Scaffold(
            topBar      = { TopBar(model) },
            bodyContent = { Body(model) },
            bottomBar   = { BottomBar(model)}
        )
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
                    modifier = Modifier.fillMaxWidth().padding(start = padDouble, end = padDouble, top = padding, bottom = padding),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        MyIcnBtn(
                            onClick = { if (connected) disconnect() else setShowDialog(true) },
                            enabled = true,
                            iconVectorDrawable = if (connected) R.drawable.ic_icon_disconnect else R.drawable.ic_icon_connect,
                            text = if (connected) "Disconnect" else "Connect"
                        )
                        // Create alert dialog, pass the showDialog state to this Composable
                        DialogIpAddress(showDialog, model, setShowDialog)
                        MyIcnBtn(
                            onClick = { takeoff() },
                            enabled = available && !isFlying,
                            iconVectorDrawable = R.drawable.ic_icon_takeoff,
                            text = "Takeoff"
                        )
                    }
                    Column {
                        MyIcnBtn(
                            onClick = { emergency() },
                            enabled = connected,
                            iconVectorDrawable = R.drawable.ic_icon_emergency,
                            backgroundColor = btnRedColor,
                            text = "Emergency"
                        )
                        MyIcnBtn(
                            onClick = { land() },
                            enabled = available && isFlying,
                            iconVectorDrawable = R.drawable.ic_icon_land,
                            text = "Land"
                        )
                    }
                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                        StatusText(battery.format("Battery: %.0f%%"))
                        StatusText(height.format("Height: %.1fcm"))
                    }
                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                        StatusText(speed.format("Speed: %.1fm/s"))
                        StatusText("Time: ${flightTime}s")
                    }
                }
        }
    }
}

@Composable
private fun Body(model: YelloAppModel) {
    model.apply {
        Box(Modifier.background(Color(0xFF5C5959))) {
            Box(Modifier.fillMaxSize().padding(start = padding, end = padding, top = 35.dp, bottom = 0.dp)) {
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
        Box(Modifier.fillMaxSize().padding(start = 120.dp, end = 120.dp, top = padding, bottom = padding)) {
            Column(Modifier.align(Alignment.BottomStart).align(Alignment.Center)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MyIcnBtn(
                        onClick = { flip('f') },
                        enabled = isFlyingAndAvailable,
                        iconVectorDrawable = R.drawable.ic_icon_flip_forward,
                        backgroundColor = btnSecondColor,
                        iconFirst = false,
                        width = 90.dp,
                        text = "Flip"
                    )
                    MyIcnBtn(
                        onClick = { flip('b') },
                        enabled = isFlyingAndAvailable,
                        iconVectorDrawable = R.drawable.ic_icon_flip_backward,
                        backgroundColor = btnSecondColor,
                        iconFirst = false,
                        width = 90.dp,
                        text = "Flip"
                    )
                    MyIcnBtn(
                        onClick = { flip('f') },
                        enabled = isFlyingAndAvailable,
                        iconVectorDrawable = R.drawable.ic_icon_flip_left,
                        backgroundColor = btnSecondColor,
                        iconFirst = false,
                        width = 90.dp,
                        text = "Flip"
                    )
                    MyIcnBtn(
                        onClick = { flip('r') },
                        enabled = isFlyingAndAvailable,
                        iconVectorDrawable = R.drawable.ic_icon_flip_right,
                        backgroundColor = btnSecondColor,
                        iconFirst = false,
                        width = 90.dp,
                        text = "Flip"
                    )
                }
            }
        }
    }
}

private fun Float.format(pattern: String): String = String.format(pattern, this)

@Composable
fun MyIcnBtn(
    onClick : () -> Unit,
    backgroundColor: Color = btnActiveColor,
    contentColor: Color = Color.White,
    enabled : Boolean,
    iconVectorDrawable : Int,
    width: Dp = btnWidth,
    height: Dp = btnHeight,
    iconScale : Float = iconScaleDefault,
    iconFirst : Boolean = true,
    text : String
) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(padSmall).width(width).height(height),
        enabled = enabled,
        colors = ButtonConstants.defaultButtonColors(backgroundColor = backgroundColor, contentColor = contentColor)
    ) {
        if (!iconFirst) Text(text)
        Icon(vectorResource(iconVectorDrawable), modifier = Modifier.scale(iconScale).padding(horizontal = padding))
        if (iconFirst) Text(text)
    }
}

@Composable
fun StatusText(
    text : String,
    fontSize : TextUnit = defaultFontSize,
    fontStyle : FontStyle = defaultFontStyle,
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontStyle = fontStyle,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(padSmall),
        color = Color.White
    )
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
                    modifier = Modifier.padding(padSmall).height(btnHeight),
                    colors = ButtonConstants.defaultButtonColors(backgroundColor = btnActiveColor, contentColor = Color.White)
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
                    modifier = Modifier.padding(padSmall).height(btnHeight),
                    colors = ButtonConstants.defaultButtonColors(backgroundColor = btnActiveColor, contentColor = Color.White)
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