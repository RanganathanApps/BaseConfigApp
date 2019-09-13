package apps.ranganathan.configlibrary.utils

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import apps.ranganathan.configlibrary.R
import java.text.FieldPosition

open interface Utils : ToastManager,VibrateManager,LogManager, ForceUpdateChecker.OnUpdateNeededListener {


    override val context: Context
        get() = context

    open override fun vibrate(context: Context) {
        val vibratorService = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibratorService.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibratorService.vibrate(50)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateNeeded(updateUrl: String) {
        makeLog("onUpdateNeeded :$updateUrl")
        val dialog = AlertDialog.Builder(context)
            .setTitle("New version available")
            .setMessage("Please, update app to the newer version to continue..")
            .setPositiveButton("Update", DialogInterface.OnClickListener { dialog, which ->
                redirectStore(updateUrl)
            }).setNegativeButton("cancel", null).create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                ContextCompat.getColor(context,
                    R.color.colorGrey));
        }
        dialog.setCancelable(false)

        dialog.show()
    }

    override fun onUpToDate() {
        makeLog("onUpToDate :")
        val dialog = AlertDialog.Builder(context)
            .setTitle("App is up to date!")
            .setMessage("You are currently using a latest one!")
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->
            }).create()
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun redirectStore(updateUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }


    interface OnClickListener {
        fun onClick(v: View)
    }

    interface OnItemClickListener {
        fun onClick(v: View, item: Any)
    }



}