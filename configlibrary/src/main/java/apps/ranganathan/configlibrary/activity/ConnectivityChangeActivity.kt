package apps.ranganathan.configlibrary.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import apps.ranganathan.configlibrary.base.NetworkUtil

open class ConnectivityChangeActivity : UtilActivity() {

    internal var internetConnectionListener: InternetConnectionListener? = null

    private val networkChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d("app", "Network connectivity change")

            if (NetworkUtil.isNetworkAvailable(context)) {
                if (internetConnectionListener != null)
                    internetConnectionListener!!.onInternetConnected()
            } else {
                if (internetConnectionListener != null)
                    internetConnectionListener!!.onInternetDisConnected()
            }

        }
    }

    interface InternetConnectionListener {
        fun onInternetConnected()

        fun onInternetDisConnected()
    }

    fun setInternetConnectionListener(  activity: AppCompatActivity,internetConnectionListener: InternetConnectionListener) {
        this.internetConnectionListener = internetConnectionListener
    }


    override fun onResume() {
        super.onResume()


    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, intentFilter)
    }
    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)
    }
}