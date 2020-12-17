package fhnw.emoba.yelloapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.modules.module09.telloplayground.data.TelloConnector
import fhnw.emoba.yelloapp.model.YelloAppModel
import fhnw.emoba.yelloapp.ui.YelloAppUI


object YelloApp : EmobaApp {
    lateinit var model: YelloAppModel

    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {
        val connector = TelloConnector(ip          = "192.168.0.18",
            commandPort = 8879,
            statePort   = 8890)
        model = YelloAppModel(connector)
    }

    @Composable
    override fun CreateAppUI() {
        YelloAppUI(model)
    }

}

