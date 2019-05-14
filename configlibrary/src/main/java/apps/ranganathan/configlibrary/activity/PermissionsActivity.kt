package apps.ranganathan.configlibrary.activity

import android.Manifest
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github.florent37.runtimepermission.kotlin.PermissionException
import com.github.florent37.runtimepermission.kotlin.askPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

open class PermissionsActivity : ConnectivityChangeActivity() {

    public val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    public val baseScope = CoroutineScope(coroutineContext)

    lateinit var permissionListener: PermissionListener
    open interface PermissionListener {
        open fun onGranted()
        open fun onDenied(deniedPermissions: List<String>)
        open fun onDeniedForeEver(deniedPermissions: List<String>)
    }

    open fun getPermission(activity: AppCompatActivity,permissionListener: PermissionListener, vararg permissions: String) {
        askPermission(*permissions) {
            if (it.isAccepted) {
                permissionListener.onGranted()
            }
        }.onDeclined { e ->
            //at least one permission have been declined by the user
            if (e.hasDenied()) {
                e.denied.forEach { permission ->
                    makeLog(permission)
                }
                permissionListener.onDenied(e.denied)
                /*ask again with dialog*/
                AlertDialog.Builder(this@PermissionsActivity)
                    .setMessage("Please accept our permissions")
                    .setPositiveButton("yes") { dialog, which ->
                        e.askAgain();
                    } //ask again
                    .setNegativeButton("no") { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }

            if (e.hasForeverDenied()) {
                e.foreverDenied.forEach { permission ->
                    makeLog(permission)
                }
                permissionListener.onDeniedForeEver(e.foreverDenied)
                //you need to open setting manually if you really need it
                e.goToSettings()
            }
        }
    }

    /*open fun makePermissionsRequest(permissionListener: PermissionListener, vararg permissions: String) {
        try {

            val result = askPermission(*permissions){

            }
            makeLog("Accepted :${result.accepted.toString()}")
            permissionListener.onGranted()

        } catch (e: PermissionException) {
            if (e.hasDenied()) {
                makeLog("Denied :")
                //the list of denied permissions
                e.denied.forEach { permission ->
                    makeLog(permission)
                }
                permissionListener.onDenied(e.denied)
                //but you can ask them again, eg:

                runOnUiThread(Runnable {
                    AlertDialog.Builder(this)
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->
                            e.askAgain()
                        }
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss()
                        }
                        .show()
                })

            }

            if (e.hasForeverDenied()) {
                makeLog("ForeverDenied")
                //the list of forever denied permissions, user has check 'never ask again'
                e.foreverDenied.forEach { permission ->
                    makeLog(permission)
                }
                permissionListener.onDeniedForeEver(e.foreverDenied)
                //you need to open setting manually if you really need it
                e.goToSettings()
            }
        }

    }*/
}