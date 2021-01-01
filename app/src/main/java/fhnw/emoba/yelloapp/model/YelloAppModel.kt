package fhnw.emoba.yelloapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.modules.module09.telloplayground.data.TelloConnector
import kotlinx.coroutines.*

class YelloAppModel(private val tello: TelloConnector) {

    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    var connected by mutableStateOf(false)
    var readyForNextCommand by mutableStateOf(false)

    val available
        get() = connected && readyForNextCommand


    var height by mutableStateOf(0.0f)

    var forwardBackward by mutableStateOf(0)
        private set

    fun updateForwardBackward(value: Int) {
        if (value == forwardBackward || !connected) {
            return
        }
        forwardBackward = value
        rc(leftRight, forwardBackward, upwardDownward, 0)
    }

    var leftRight by mutableStateOf(0)
        private set

    fun updateLeftRight(value: Int) {
        if (value == leftRight  || !connected) {
            return
        }
        leftRight = value
        rc(leftRight, forwardBackward, upwardDownward, 0)
    }

    var upwardDownward by mutableStateOf(0)
        private set

    fun updateUpwardDownward(value: Int) {
        if (value == upwardDownward || !connected) {
            return
        }
        upwardDownward = value
        rc(leftRight, forwardBackward, upwardDownward, 0)
    }

    fun connect() {
        modelScope.launch {
            tello.connect {
                connected = true
                readyForNextCommand = true
                continuousStatus()
            }
        }
    }

    fun continuousStatus(){
        modelScope.launch {
            tello.startStatusNotification{ height = it.split(";")[13].split(":")[1].toFloat()}
        }
    }

    private var rcJob: Job? = null

    fun rc(leftRight: Int, forwardBack: Int, upDown: Int, yaw: Int) {
        rcJob?.apply {
            if(isActive){
                cancel()
            }
        }
        rcJob = modelScope.launch {
            delay(50L)  // nur alle 50 ms ein neues rc Command an Tello schicken
            if(isActive){
                tello.rc(leftRight, forwardBack, upDown, yaw)
            }
        }
    }

    fun stop() {
        leftRight       = 0
        forwardBackward = 0
        upwardDownward = 0
        //rc(0,0,0,0)
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
    fun forward() = onTello { forward(50, defaultOnFinished) }
    fun flip(dir: Char) = onTello { flip(dir, defaultOnFinished) }

    private val defaultOnFinished: (response: String) -> Unit = { readyForNextCommand = true }

    private fun onTello(todo: TelloConnector.() -> Unit) {
        if(connected && readyForNextCommand){
            readyForNextCommand = false
            modelScope.launch {
                todo.invoke(tello)
            }
        }
    }
}