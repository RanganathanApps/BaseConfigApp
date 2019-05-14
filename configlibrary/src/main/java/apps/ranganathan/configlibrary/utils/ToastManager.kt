package apps.ranganathan.configlibrary.utils

import android.content.Context
import android.widget.Toast

interface ToastManager {

    val context: Context

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun longToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}