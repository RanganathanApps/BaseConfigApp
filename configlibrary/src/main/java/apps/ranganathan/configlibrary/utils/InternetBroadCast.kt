package apps.ranganathan.configlibrary.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import apps.ranganathan.configlibrary.base.NetworkUtil

open class InternetBroadCast : BroadcastReceiver() {

    companion object {
        internal var internetConnectionListener: InternetConnectionListener? = null
        fun setInternetConnectionListener(activity: AppCompatActivity, internetConnectionListener: InternetConnectionListener) {
            this.internetConnectionListener = internetConnectionListener
        }
    }


    override fun onReceive(context: Context, intent: Intent) {
        Log.w("app", "Network connectivity change")

        if (intent.action!!.equals(CONNECTIVITY_ACTION)) {
            if (NetworkUtil.isNetworkAvailable(context)) {
                if (internetConnectionListener != null)
                    internetConnectionListener!!.onInternetConnected()
            } else {
                if (internetConnectionListener != null)
                    internetConnectionListener!!.onInternetDisConnected()
            }
        }

    }
}