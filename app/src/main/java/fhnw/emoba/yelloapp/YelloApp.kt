package fhnw.emoba.yelloapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.yelloapp.data.TelloConnector
import fhnw.emoba.yelloapp.model.YelloAppModel
import fhnw.emoba.yelloapp.ui.YelloAppUI


object YelloApp : EmobaApp {
    lateinit var model: YelloAppModel

    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {

        /// For simulator:
//        val connector = TelloConnector(ip          = "192.168.1.6",
//            commandPort = 8879,
//            statePort   = 8890)

        /// For the real drone:
//        val connector = TelloConnector(ip          = "192.168.10.1",
//            commandPort = 8889,
//            statePort   = 8890)

        val connector = TelloConnector()


        model = YelloAppModel(connector)
    }

    @Composable
    override fun CreateAppUI() {
        YelloAppUI(model)
    }

}

