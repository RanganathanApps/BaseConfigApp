package apps.ranganathan.configlibrary.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class ConnectivityViewModel : ViewModel() {

    open var isConnected = MutableLiveData<Boolean>()



    init {

    }


}