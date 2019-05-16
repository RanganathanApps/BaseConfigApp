package apps.ranganathan.configlibrary.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import apps.ranganathan.configlibrary.base.NetworkUtil
import apps.ranganathan.configlibrary.utils.InternetBroadCast
import apps.ranganathan.configlibrary.utils.InternetConnectionListener

open class ConnectivityChangeActivity : UtilActivity() {

    open var isDisconnected = false;
    val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    open fun setConnectivityChange(){
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(InternetBroadCast(), intentFilter)

        InternetBroadCast.setInternetConnectionListener(this, object : InternetConnectionListener {

            override fun onInternetConnected() {
                if (isDisconnected) {
                    isDisconnected = false
                    showToast("Connected..")
                }
            }

            override fun onInternetDisConnected() {
                isDisconnected = true
                showToast("Internet  disConnected!")

            }
        })


    }







    override fun onResume() {
        super.onResume()


    }

    override fun onStart() {
        super.onStart()
       /* val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, intentFilter)*/
    }
    override fun onPause() {
        super.onPause()
        //unregisterReceiver(networkChangeReceiver)
    }
}