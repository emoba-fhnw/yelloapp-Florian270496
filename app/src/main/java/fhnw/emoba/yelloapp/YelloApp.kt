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

        // ===================================================================== //
        // IMPORTANT:                                                            //
        // ----------                                                            //
        // Please configure your IP-address and ports in:                        //
        //      src/main/java/fhnw/emoba/yelloapp/data/TelloConnector.kt         //
        //                                                                       //
        // There, you can save two configurations for your convenience:          //
        //      One for your simulator app and one for your real drone           //
        //      for fast switching                                               //
        // ===================================================================== //

        val connector = TelloConnector()

        model = YelloAppModel(connector)
    }

    @Composable
    override fun CreateAppUI() {
        YelloAppUI(model)
    }
}

