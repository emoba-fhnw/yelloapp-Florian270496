package fhnw.emoba.yelloapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonConstants
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import fhnw.emoba.R
import fhnw.emoba.yelloapp.model.YelloAppModel
import fhnw.emoba.yelloapp.ui.theme.*


@Composable
fun GeneralButtonsAndPopupDialog(model: YelloAppModel) {
    // State to manage if the alert dialog is showing or not.
    // Default is false (not showing)
    val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }

    // Create alert dialog, pass the showDialog state to this Composable
    DialogIpAddress(showDialog, model, setShowDialog)

    model.apply {
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
    }
}


@Composable
fun FlipButtonsUI(model: YelloAppModel) {
    model.apply {
        Box(Modifier.fillMaxHeight().padding(horizontal = padNormal, vertical = padNormal)) {
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
    contentColor: Color = myTextColor,
    enabled : Boolean,
    iconVectorDrawable : Int,
    width: Dp = btnWidth,
    height: Dp = btnHeight,
    iconScale : Float = myIconScale,
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
        Icon(vectorResource(iconVectorDrawable), modifier = Modifier.scale(iconScale).padding(horizontal = padNormal))
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
