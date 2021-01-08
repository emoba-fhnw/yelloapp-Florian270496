package fhnw.emoba.yelloapp.data

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.nio.charset.StandardCharsets

class TelloConnector(private val ip: String,
                     private val commandPort: Int,
                     private val statePort: Int) {

    private lateinit var commandSocket: DatagramSocket
    private lateinit var statusSocket: DatagramSocket

    fun connect(onFinished: (response: String) -> Unit) {
        commandSocket = DatagramSocket(commandPort).apply {
            connect(InetAddress.getByName(ip), commandPort)
        }

        onFinished.invoke(sendCommandAndWait("command"))
    }

    fun disconnect() {
        commandSocket.disconnect()
    }

    fun startStatusNotification(onNewStatus: (String) -> Unit){
        statusSocket = DatagramSocket(statePort)

        while(true){
            val packetSize    = 512
            val receivePacket = DatagramPacket(ByteArray(packetSize), packetSize)

            statusSocket.receive(receivePacket)

            onNewStatus.invoke(String(receivePacket.data, 0, receivePacket.length, StandardCharsets.UTF_8))
        }
    }


    fun takeoff(onFinished: (response: String) -> Unit)         = onFinished.invoke(sendCommandAndWait("takeoff"))
    fun land(onFinished: (response: String) -> Unit)            = onFinished.invoke(sendCommandAndWait("land"))
    fun emergency(onFinished: (response: String) -> Unit)       = onFinished.invoke(sendCommandAndWait("emergency"))
    fun forward(y: Int, onFinished: (response: String) -> Unit) = onFinished.invoke(sendCommandAndWait("forward $y"))
    fun flip(dir: Char, onFinished: (response: String) -> Unit) = onFinished.invoke(sendCommandAndWait("flip $dir"))

    fun rc(leftRight: Int, forwardBack: Int, upDown: Int, yaw: Int) = fireAndForgetCommand("rc $leftRight $forwardBack $upDown $yaw")
    fun stop()                                                      = fireAndForgetCommand("stop")

    fun askWifi(onFinished: (response: String) -> Unit) = onFinished.invoke(sendCommandAndWait("wifi?"))

    private fun sendCommandAndWait(command: String) : String {
        if (!commandSocket.isConnected){
            return "not connected"
        }
        fireAndForgetCommand(command)

        val packetSize    = 256
        val receivePacket = DatagramPacket(ByteArray(packetSize), packetSize)

        commandSocket.receive(receivePacket)

        return String(receivePacket.data, StandardCharsets.UTF_8)
    }

    private fun fireAndForgetCommand(command: String){
        if (!commandSocket.isConnected){
            return
        }

        val buf    = command.toByteArray(StandardCharsets.UTF_8)
        val packet = DatagramPacket(buf, buf.size, commandSocket.inetAddress, commandSocket.port)

        commandSocket.send(packet)
    }
}