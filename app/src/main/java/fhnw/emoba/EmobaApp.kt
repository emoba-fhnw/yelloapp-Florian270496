package fhnw.emoba

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable

/**
 * Trennt, so weit es geht, die "alte" Welt der Activities von der "neuen" Welt von JetPack Compose
 *
 * Jede Emoba-Applikation sollte dieses Interface implementieren
 */
interface EmobaApp {

    /**
     * Initialer Aufbau des App-Status.
     *
     * Bereitstellen aller initial notwendigen Daten (Daten werden normalerweise erst
     * geladen, wenn sie fuer die Anzeige benötigt werden).
     */
    fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?)


    /**
     * Das gesamte UI der App.
     */
    @Composable
    fun CreateAppUI()


    /**
     * Wird aufgerufen sobald die App nicht mehr im Vordergrund sichtbar ist.
     */
    fun onStop(activity: AppCompatActivity) {
        // Default: Nichts zu tun
    }
}