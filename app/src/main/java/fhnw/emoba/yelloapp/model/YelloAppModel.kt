package fhnw.emoba.yelloapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.yelloapp.data.TelloConnector
import kotlinx.coroutines.*
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

class YelloAppModel(private val tello: TelloConnector) {

    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    var connected by mutableStateOf(false)
    private var readyForNextCommand by mutableStateOf(false)
    var isDarkTheme by mutableStateOf(false)

    val available
        get() = connected && readyForNextCommand

    val isFlying
        get() = height >= 0.1f

    val isFlyingAndAvailable
        get() = connected && readyForNextCommand && height >= 0.1f

    var height by mutableStateOf(0.0f)
    var battery by mutableStateOf(0.0f)
    var wifiStrength by mutableStateOf(0)
    var speed by mutableStateOf(0.0f)
    private var speedX = 0.0f
    private var speedY = 0.0f
    private var speedZ = 0.0f
    var flightTime by mutableStateOf(0)

    private var leftRight by mutableStateOf(0)
    private var forwardBackward by mutableStateOf(0)
    private var upwardDownward by mutableStateOf(0)
    private var rotateLeftRight by mutableStateOf(0)

    var flightControlLeft by mutableStateOf(FlightControl.Zero)
    var flightControlRight by mutableStateOf(FlightControl.Zero)


    fun connect(real : Boolean) {
        modelScope.launch {
            tello.connect(real = real) {
                connected = true
                readyForNextCommand = true
                continuousStatus()
            }
        }
    }


    fun disconnect() {
        modelScope.launch {
            tello.disconnect()
            connected = false
            readyForNextCommand = false
        }
    }


    private fun continuousStatus(){
        modelScope.launch {
            tello.startStatusNotification { status ->
                // Extract each answer of the answer-string:
                val answers = status.split(";")
                // Search for answers I'm interested in:
                answers.forEach {
                    when {
                        it.startsWith("h:") -> height = it.substring(2).toFloat()
                        it.startsWith("bat:") -> battery = it.substring(4).toFloat()
                        it.startsWith("vgx:") -> speedX = it.substring(4).toFloat()
                        it.startsWith("vgy:") -> speedY = it.substring(4).toFloat()
                        it.startsWith("vgz:") -> speedZ = it.substring(4).toFloat()
                        it.startsWith("time") -> flightTime = it.substring(5).toInt()
                    }
                }
                // Calculate the speed in 3D:
                speed = sqrt(speedX.pow(2) + speedY.pow(2) + speedZ.pow(2))
            }
        }
    }


    private var rcJob: Job? = null


    private fun rc(leftRight: Int, forwardBack: Int, upDown: Int, rotateLeftRight: Int) {
        rcJob?.apply {
            if(isActive){
                cancel()
            }
        }
        rcJob = modelScope.launch {
            // Only send every 50 milliseconds a new command:
            delay(50L)
            if(isActive){
                tello.rc(leftRight, forwardBack, upDown, rotateLeftRight)
            }
        }
    }


    fun fly() {
        if (connected) {
            modelScope.launch {
                rc(
                    (flightControlRight.x * 100).roundToInt(),
                    (-flightControlRight.y * 100).roundToInt(),
                    (-flightControlLeft.y * 100).roundToInt(),
                    (flightControlLeft.x * 100).roundToInt()
                )
            }
        }
    }


    fun stop() {
        leftRight       = 0
        forwardBackward = 0
        upwardDownward = 0
        rotateLeftRight = 0
        rc(0,0,0,0)
        rcJob?.apply {
            if(isActive){
                cancel()
            }
        }
        modelScope.launch {
            tello.stop()
        }
    }


    fun takeoff() = onTello { takeoff(defaultOnFinished) }
    fun land()    = onTello { land(defaultOnFinished) }
    fun flip(dir: Char) = onTello { flip(dir, defaultOnFinished) }
    fun emergency() = onTello { emergency(defaultOnFinished) }
    fun updateWifiStrength() = onTello { askWifi(updateWifiOnFinished) }
    fun realIpAndPorts() = "${tello.realIp}: ${tello.realCommandPort} / ${tello.statusPort}"
    fun simulatorIpAndPorts() = "${tello.simulatorIp}: ${tello.simulatorCommandPort} / ${tello.statusPort}"


    private val defaultOnFinished: (response: String) -> Unit = { readyForNextCommand = true }


    private val updateWifiOnFinished: (response: String) -> Unit = {
        wifiStrength = it.toInt()
        readyForNextCommand = true
    }


    private fun onTello(todo: TelloConnector.() -> Unit) {
        if(connected && readyForNextCommand){
            readyForNextCommand = false
            modelScope.launch {
                todo.invoke(tello)
            }
        }
    }
}