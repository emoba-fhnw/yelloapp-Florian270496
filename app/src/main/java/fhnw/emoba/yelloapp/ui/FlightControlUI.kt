package fhnw.emoba.yelloapp.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.gesture.DragObserver
import androidx.compose.ui.gesture.dragGestureFilter
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.res.imageResource
import fhnw.emoba.R
import fhnw.emoba.yelloapp.model.FlightControl

@Composable
fun FlightControlUI(
    modifier: Modifier = Modifier,
    isLeftControl: Boolean,
    flightControl: FlightControl,
    onInput: (FlightControl) -> Unit,
) {
    WithConstraints(modifier) {
        val backgroundImage =
            imageResource(if (isLeftControl) R.drawable.control_left else R.drawable.control_right)
        val thumbImage = imageResource(R.drawable.stick)

        val canvasSize = Integer.min(constraints.maxHeight, constraints.maxWidth)
        val maxDrag = canvasSize.toFloat() / 2
        val dragOffset = remember { mutableStateOf(Offset.Zero) }
        val scaleFactor = canvasSize.toFloat() / backgroundImage.width
        val thumbCenterOffset =
            Offset(thumbImage.width.toFloat() / 2, thumbImage.width.toFloat() / 2)

        Box(contentAlignment = Alignment.Center) {
            Canvas(
                modifier = modifier
                    .dragGestureFilter(object : DragObserver {
                        override fun onStart(downPosition: Offset) {
                            dragOffset.value = Offset.Zero
                            super.onStart(downPosition)
                        }

                        override fun onDrag(dragDistance: Offset): Offset {
                            dragOffset.value += dragDistance
                            val dragClamped = flightControl.clampOffset(dragOffset.value, maxDrag)
                            onInput.invoke(FlightControl.fromOffset(dragClamped, maxDrag))
                            return super.onDrag(dragDistance)
                        }

                        override fun onStop(velocity: Offset) {
                            dragOffset.value = Offset.Zero
                            onInput.invoke(FlightControl.Zero)
                            super.onStop(velocity)
                        }
                    })
            ) {
                scale(scaleFactor, pivot = Offset.Zero) {
                    drawImage(backgroundImage)

                    val stickOffset = flightControl.toOffset(maxDrag)
                    drawImage(
                        thumbImage,
                        topLeft = (center / scaleFactor) - thumbCenterOffset + (stickOffset / scaleFactor)
                    )
                }
            }
        }
    }
}
