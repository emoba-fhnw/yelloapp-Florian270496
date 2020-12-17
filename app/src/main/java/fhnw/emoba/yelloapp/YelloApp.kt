package fhnw.emoba.yelloapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.yelloapp.model.YelloAppModel
import fhnw.emoba.yelloapp.ui.YelloAppUI


object YelloApp : EmobaApp {

    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {

    }

    @Composable
    override fun CreateAppUI() {
        YelloAppUI(YelloAppModel)
    }

}

