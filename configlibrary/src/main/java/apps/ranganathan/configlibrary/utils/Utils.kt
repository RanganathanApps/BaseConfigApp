package apps.ranganathan.configlibrary.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import java.text.FieldPosition

open class Utils {
    interface OnClickListener {
        fun onClick(v: View)
    }

    interface OnItemClickListener {
        fun onClick(v: View, item: Any)
    }



}