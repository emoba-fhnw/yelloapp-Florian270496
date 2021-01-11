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
val flipBtnWidth = 90.dp
val btnHeight= 35.dp
val flightControlHeight = 216.dp    // 216 = 756/3.5
const val iconScale = 1.0f
val btnActiveColor = Color(0xFF425292)
val btnSecondColor= Color(0xFFAAA259)
val btnRedColor = Color(0xFFE60000)
val textColor = Color.White
val topBarColor = Color(0xFF404040)
val backgroundColor = Color(0xFF5C5959)
val fontSize = 18.sp
val fontStyle = FontStyle.Italic


@Composable
fun YelloAppUI(model: YelloAppModel) {
    MaterialTheme {
        Scaffold(
            topBar      = { TopBar(model) },
            bodyContent = { Body(model) },
        )
    }
}

@Composable
private fun TopBar(model: YelloAppModel) {

    // State to manage if the alert dialog is showing or not.
    // Default is false (not showing)
    val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }

    // Create alert dialog, pass the showDialog state to this Composable
    DialogIpAddress(showDialog, model, setShowDialog)

    model.apply {
        Box(Modifier.background(topBarColor)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = padDouble, end = padDouble, top = padding, bottom = padding),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        MyIconButton(
                            onClick = { if (connected) disconnect() else setShowDialog(true) },
                            enabled = true,
                            iconVectorDrawable = if (connected) R.drawable.ic_icon_disconnect else R.drawable.ic_icon_connect,
                            text = if (connected) "Disconnect" else "Connect"
                        )
                        MyIconButton(
                            onClick = { takeoff() },
                            enabled = available && !isFlying,
                            iconVectorDrawable = R.drawable.ic_icon_takeoff,
                            text = "Takeoff"
                        )
                    }
                    Column {
                        MyIconButton(
                            onClick = { emergency() },
                            enabled = connected,
                            iconVectorDrawable = R.drawable.ic_icon_emergency,
                            backgroundColor = btnRedColor,
                            text = "Emergency"
                        )
                        MyIconButton(
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
        Box(Modifier.background(backgroundColor)) {
            Box(Modifier.fillMaxSize().padding(horizontal = padDouble, vertical = padding)) {
//                Column(Modifier.align(Alignment.Center).fillMaxHeight(0.9f).fillMaxWidth(0.9f)) {
                Column(Modifier.align(Alignment.Center)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        FlightControlUI(
                            modifier = Modifier.size(flightControlHeight),
                            isLeftControl = true,
                            flightControl = flightControlLeft,
                            onInput = {
                                flightControlLeft = it
                                fly()
                            }
                        )

                        FlipButtonsUI(model)

                        FlightControlUI(
                            modifier = Modifier.size(flightControlHeight),
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
private fun FlipButtonsUI(model: YelloAppModel) {
    model.apply {
        Box(Modifier.fillMaxHeight().padding(horizontal = padding, vertical = padding)) {
            Box(Modifier.align(Alignment.Center)) {
                Column {
                    Row {
                        FlipButton(
                            onClick = { flip('f') },
                            enabled = isFlyingAndAvailable,
                            iconVectorDrawable = R.drawable.ic_icon_flip_forward
                        )
                        FlipButton(
                            onClick = { flip('b') },
                            enabled = isFlyingAndAvailable,
                            iconVectorDrawable = R.drawable.ic_icon_flip_backward
                        )}
                    Row {
                        FlipButton(
                            onClick = { flip('l') },
                            enabled = isFlyingAndAvailable,
                            iconVectorDrawable = R.drawable.ic_icon_flip_left
                        )
                        FlipButton(
                            onClick = { flip('r') },
                            enabled = isFlyingAndAvailable,
                            iconVectorDrawable = R.drawable.ic_icon_flip_right
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MyIconButton(
    onClick : () -> Unit,
    backgroundColor: Color = btnActiveColor,
    contentColor: Color = textColor,
    enabled : Boolean,
    iconVectorDrawable : Int,
    width: Dp = btnWidth,
    height: Dp = btnHeight,
    iconScale : Float = fhnw.emoba.yelloapp.ui.iconScale,
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
fun FlipButton(
    onClick : () -> Unit,
    enabled : Boolean,
    iconVectorDrawable : Int,
) {
    MyIconButton(
        onClick = onClick,
        enabled = enabled,
        iconVectorDrawable = iconVectorDrawable,
        backgroundColor = btnSecondColor,
        iconFirst = false,
        width = flipBtnWidth,
        text = "Flip"
    )
}


@Composable
fun StatusText(
    text : String,
    fontSize : TextUnit = fhnw.emoba.yelloapp.ui.fontSize,
    fontStyle : FontStyle = fhnw.emoba.yelloapp.ui.fontStyle,
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontStyle = fontStyle,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(padSmall),
        color = textColor
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
                    colors = ButtonConstants.defaultButtonColors(backgroundColor = btnActiveColor, contentColor = textColor)
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
                    colors = ButtonConstants.defaultButtonColors(backgroundColor = btnActiveColor, contentColor = textColor)
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

private fun Float.format(pattern: String): String = String.format(pattern, this)

