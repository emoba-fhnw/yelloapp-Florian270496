package fhnw.emoba

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import fhnw.emoba.yelloapp.YelloApp


class MainActivity : AppCompatActivity() {
    private lateinit var app: EmobaApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        // ToDo: hier die emoba App eintragen, die gestartet werden soll
        app = YelloApp

        app.initialize(activity = this, savedInstanceState = savedInstanceState)

        setContent {
            app.CreateAppUI()
        }

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    /**
     * Eine der Activity-LiveCycle-Methoden. Im Laufe des Semesters werden weitere benötigt
     * werden. Auch die leiten den Aufruf lediglich an die EmobaApp weiter.
     */
    override fun onStop() {
        super.onStop()
        app.onStop(activity = this)
    }
}

