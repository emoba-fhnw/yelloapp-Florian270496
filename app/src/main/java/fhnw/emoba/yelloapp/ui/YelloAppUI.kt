package fhnw.emoba.yelloapp.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fhnw.emoba.yelloapp.model.YelloAppModel
import fhnw.emoba.yelloapp.ui.theme.*


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
    model.apply {
        Box(Modifier.background(topBarColor)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = padDouble, end = padDouble, top = padNormal, bottom = padNormal),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    GeneralButtonsAndPopupDialog(model)

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
        Box(Modifier.background(myBackgroundColor)) {
            Box(Modifier.fillMaxSize().padding(horizontal = padDouble, vertical = padNormal)) {
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


private fun Float.format(pattern: String): String = String.format(pattern, this)

