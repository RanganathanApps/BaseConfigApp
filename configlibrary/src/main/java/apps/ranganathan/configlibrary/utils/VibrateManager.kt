package apps.ranganathan.configlibrary.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast

interface VibrateManager {
    abstract fun vibrate(context: Context)
}