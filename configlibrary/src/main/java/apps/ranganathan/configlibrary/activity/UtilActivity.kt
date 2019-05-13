package apps.ranganathan.configlibrary.activity

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import apps.ranganathan.configlibrary.base.BaseAppActivity
import com.google.android.material.snackbar.Snackbar

open class UtilActivity : AppCompatActivity() {

    open fun makeLog(key: String, value: String) {
        Log.w(key, value)
    }

    open fun makeLog(msg: String) {
        Log.w("base", msg)
    }

    open fun showMsg(view: View, msg: String) {
        BaseAppActivity.makeLog(msg)
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    open fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    open fun hideSoftKey(mCon: Context) {
        val activity = mCon as AppCompatActivity
        if (activity.currentFocus != null) {
            val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }

    open fun vibrate(mCon: Context) {
        val v = mCon.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        // Vibrate for 500 milliseconds
        v.vibrate(40)
    }

    open fun capitalize(s: String): String {
        if (s.isEmpty())
            return s
        val sb = StringBuilder(s)
        sb.setCharAt(0, Character.toUpperCase(sb[0]))
        return sb.toString()
    }
}
