package apps.ranganathan.configlibrary.utils

import android.util.Log

interface LogManager {


    open fun makeLog(key: String, value: String) {
        Log.w(key, value)
    }


    open fun makeLog(msg: String) {
        Log.w("base", msg)
    }
}