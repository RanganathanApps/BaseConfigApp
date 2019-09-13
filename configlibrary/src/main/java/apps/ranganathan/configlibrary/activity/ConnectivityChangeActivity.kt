package apps.ranganathan.configlibrary.activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import apps.ranganathan.configlibrary.utils.InternetBroadCast
import apps.ranganathan.configlibrary.utils.InternetConnectionListener
import apps.ranganathan.configlibrary.viewModel.ConnectivityViewModel

open class ConnectivityChangeActivity : UtilActivity() {

    lateinit var connectivityViewModel: ConnectivityViewModel
    val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"

    open fun getIsConnected(): LiveData<Boolean> {
        showToast("hai")
        return connectivityViewModel.isConnected
    }

    open fun setConnectivityChange() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(InternetBroadCast(), intentFilter)
        connectivityViewModel = ViewModelProviders.of(this).get(ConnectivityViewModel::class.java)
        InternetBroadCast.setInternetConnectionListener(this, object : InternetConnectionListener {

            override fun onInternetConnected() {
                connectivityViewModel.isConnected.value = true
            }

            override fun onInternetDisConnected() {
                connectivityViewModel.isConnected.value = false

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