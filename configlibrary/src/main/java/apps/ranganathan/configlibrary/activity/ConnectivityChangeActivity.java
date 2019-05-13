package apps.ranganathan.configlibrary.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import apps.ranganathan.configlibrary.base.NetworkUtil;

public class ConnectivityChangeActivity extends UtilActivity {

    InternetConnectionListener internetConnectionListener;

    public interface InternetConnectionListener {
        public void onInternetConnected();

        public void onInternetDisConnected();
    }

    public void setInternetConnectionListener(AppCompatActivity activity) {
        internetConnectionListener = (InternetConnectionListener) activity;
    }

    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("app", "Network connectivity change");

            if (NetworkUtil.INSTANCE.isNetworkAvailable(context)) {
                if (internetConnectionListener != null)
                    internetConnectionListener.onInternetConnected();
            } else {
                if (internetConnectionListener != null)
                    internetConnectionListener.onInternetDisConnected();
            }

        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(networkChangeReceiver);
    }
}