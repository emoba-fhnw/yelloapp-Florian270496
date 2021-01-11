package fhnw.emoba.yelloapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonConstants
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fhnw.emoba.yelloapp.model.YelloAppModel
import fhnw.emoba.yelloapp.ui.theme.*


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
                    colors = ButtonConstants.defaultButtonColors(backgroundColor = btnActiveColor, contentColor = myTextColor)
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
                    colors = ButtonConstants.defaultButtonColors(backgroundColor = btnActiveColor, contentColor = myTextColor)
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
