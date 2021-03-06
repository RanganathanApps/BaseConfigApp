package apps.ranganathan.configlibrary.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import androidx.appcompat.app.AppCompatActivity
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import apps.ranganathan.configlibrary.R
import apps.ranganathan.configlibrary.base.BaseAppActivity
import apps.ranganathan.configlibrary.utils.*
import com.google.android.material.snackbar.Snackbar

open class UtilActivity : AppCompatActivity() , Utils {



    override val context: Context
        get() = this


    open fun showMsg(view: View, msg: String) {
        BaseAppActivity.makeLog(msg)
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    open fun hideSoftKey(mCon: Context) {
        val activity = mCon as AppCompatActivity
        if (activity.currentFocus != null) {
            val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }

    open fun capitalize(s: String): String {
        if (s.isEmpty())
            return s
        val sb = StringBuilder(s)
        sb.setCharAt(0, Character.toUpperCase(sb[0]))
        return sb.toString()
    }
}
