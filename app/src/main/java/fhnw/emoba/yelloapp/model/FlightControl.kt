package fhnw.emoba.yelloapp.model

import androidx.compose.ui.geometry.Offset


data class FlightControl(val x : Float, val y: Float) {
    fun toOffset(size: Float) : Offset {
        return Offset(x * size, y * size)
    }

    fun clampOffset(offset: Offset, maxDistance: Float) : Offset {
        var result = offset
        val distance = offset.getDistance()
        if (distance > maxDistance) result /= distance / maxDistance

        return result
    }

    companion object {
        val Zero = FlightControl(0f, 0f)

        fun fromOffset(offset: Offset, maxValue: Float) : FlightControl {
            return FlightControl(offset.x / maxValue, offset.y / maxValue)
        }
    }
}
