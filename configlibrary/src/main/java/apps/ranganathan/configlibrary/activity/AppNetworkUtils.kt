package apps.ranganathan.configlibrary.activity

import android.content.Context
import android.net.ConnectivityManager

open class AppNetworkUtils {
    val TYPE_WIFI = 1
    val TYPE_MOBILE = 2
    val TYPE_NOT_CONNECTED = 0
    val NETWORK_STATUS_NOT_CONNECTED = 0
    val NETWORK_STATUS_WIFI = 1
    val NETWORK_STATUS_MOBILE = 2

    companion object  Factory{
        open  fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }

    }


    open fun getConnectivityStatus(context: Context): Int {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI

            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE
        }
        return TYPE_NOT_CONNECTED
    }

    open fun getConnectivityStatusString(context: Context): Int {
        val conn = getConnectivityStatus(context)
        var status = 0
        if (conn == TYPE_WIFI) {
            status = NETWORK_STATUS_WIFI
        } else if (conn == TYPE_MOBILE) {
            status = NETWORK_STATUS_MOBILE
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = NETWORK_STATUS_NOT_CONNECTED
        }
        return status
    }
}