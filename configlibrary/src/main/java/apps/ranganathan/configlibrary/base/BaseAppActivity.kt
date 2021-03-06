package apps.ranganathan.configlibrary.base

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import apps.ranganathan.configlibrary.R
import apps.ranganathan.configlibrary.activity.AppImagePickerActivity
import apps.ranganathan.configlibrary.utils.Utils
import com.bumptech.glide.Glide
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.Serializable
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.NetworkPolicy
import kotlinx.android.synthetic.main.alert_ly.view.*
import kotlinx.android.synthetic.main.error_ly.view.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*


open class BaseAppActivity : AppImagePickerActivity() {

    val bundle = Bundle()
    val requestOptions = RequestOptions()

    open fun setAppBar(title: String) {
        try {
            toolbar.title = title
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar.navigationIcon = getDrawable(R.drawable.ic_arrow_back_black_24dp)
            }
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        } catch (e: Exception) {
            showToast(e.localizedMessage?:"Some error occurred!")
        }
    }

    open fun setToolBarTitle(title: String) {
        try {
            toolbar.txtToolbarTitle.text = title
        } catch (e: Exception) {
            showToast(e.localizedMessage?:"Some error occurred!")
        }
    }

    open fun changeToolbarNavIconColor(color : Int ){
        toolbar.navigationIcon!!.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(this, color), PorterDuff.Mode.SRC_IN)
        txtToolbarTitle.setTextColor(ContextCompat.getColor(this,R.color.colorWhite))
    }

    companion object {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun makeIntent(context: Context, cls: Class<*>, intentData: Any): Intent {
            val intent = Intent(context, cls)
            if (intentData != "") {
                intent.putExtra("intent_data", intentData as Serializable)
            }
            val options = ActivityOptions.makeSceneTransitionAnimation(context as Activity?)
            ContextCompat.startActivity(context, intent, options.toBundle())
            //ContextCompat.startActivity(context, intent, null)
            return intent
        }
        open fun makeLog(msg: String) {
            Log.w("base", msg)
        }

    }

    open fun loadImage(url: String, imageView: ImageView, placeHolder: Int, placeHolderError: Int, progressBar: View) {
        progressBar.visibility = View.VISIBLE
        Picasso.get().load(url)
            .placeholder(placeHolder)
            .error(placeHolderError)
            .fit()
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    progressBar.visibility = View.GONE
                    makeLog(e!!.localizedMessage)
                }
            })
    }
    open fun loadImagePicasso(url: String, imageView: ImageView, placeHolder: Int) {
        Picasso.get().load(url)
            .placeholder(placeHolder)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .fit()
            .into(imageView)
    }

    open fun loadImage(url: String, imageView: ImageView, placeHolder: Int){

        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context)
            .load(url).apply(requestOptions)
            .into(imageView)
    }


    open fun startActivityputExtra(mCon: Context, cls: Class<*>, key: String, o: Any) {
        try {
            val intent = Intent(mCon, cls)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or
                    Intent.FLAG_ACTIVITY_NEW_TASK


            bundle.clear()
            bundle.putSerializable(key, o as Serializable)
            intent.putExtras(bundle)
            mCon.startActivity(intent)
        } catch (e: Exception) {
            showToast(e.message.toString())
        }

    }

    open fun startActivityputExtra(mCon: Context, cls: Class<*>,map: Map<String, Any>) {
        try {
            val intent = Intent(mCon, cls)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or
                    Intent.FLAG_ACTIVITY_NEW_TASK


            bundle.clear()
            for (pair in map) {
                bundle.putSerializable(pair.key, pair.value as Serializable)

            }
            intent.putExtras(bundle)
            mCon.startActivity(intent)
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    open fun startAppActivity(mCon: Context, cls: Class<*>) {
        try {
            val intent = Intent(mCon, cls)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or
                    Intent.FLAG_ACTIVITY_NEW_TASK


            mCon.startActivity(intent)
        } catch (e: Exception) {
            showToast(e.message.toString())
        }

    }


    open fun showSmallNotification(
        mCon: Context, mBuilder: NotificationCompat.Builder,
        icon: Int, title: String,
        message: String,
        resultPendingIntent: PendingIntent,
        alarmSound: Uri, autoCancel: Boolean,
        notification_id: Int,
        iconIdSmall: Int
    ) {

        val inboxStyle = NotificationCompat.InboxStyle()

        inboxStyle.addLine(message)

        val notification: Notification
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentIntent(resultPendingIntent)
            .setSound(alarmSound)
            .setStyle(inboxStyle)
            .setSmallIcon(iconIdSmall)
            .setLargeIcon(BitmapFactory.decodeResource(mCon.resources, icon))
            .setContentText(message)
            .setAutoCancel(autoCancel)
            .build()
        if (!autoCancel) {
            notification.flags = Notification.FLAG_ONGOING_EVENT
        }


        val notificationManager = mCon.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notification_id, notification)
    }

    open interface AppOkInter {
        open fun onOk()
    }


    open fun attentionDialog(mCon: Context, appOkInter: AppOkInter, msg: String, iconId: Int, Alerttitle: String) {
        val activity = mCon as AppCompatActivity
        activity.runOnUiThread {
            val alertDialogBuilder = AlertDialog.Builder(mCon)
            alertDialogBuilder.setIcon(ContextCompat.getDrawable(mCon, iconId))
            alertDialogBuilder.setTitle(Alerttitle)
            alertDialogBuilder.setMessage(msg)
            alertDialogBuilder.setPositiveButton("ok",
                DialogInterface.OnClickListener { arg0, arg1 -> appOkInter.onOk() })

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
            alertDialog.setCancelable(false)
        }
    }

    @SuppressLint("SetTextI18n")
    open fun showException(message: String) {
        try {
            val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet_ex, null)
            view.errorLy.txtErrorMessage.text = message
            view.alertLy.visibility = View.GONE
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            if (!isFinishing) {
                dialog.show()
            }
        } catch (e: Exception) {
            Log.w("App Error:", e.localizedMessage)
        }
    }

    @SuppressLint("SetTextI18n")
    open fun showAlert(title: String, message: String, alertOkListner: Any?) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet_ex, null)
        view.errorLy.visibility = View.GONE
        view.alertLy.txtAlertMessage.text = message
        view.alertLy.txtAlertTitle.text = title
        view.alertLy.btnYes.visibility = View.VISIBLE
        view.alertLy.btnNo.visibility = View.GONE
        view.alertLy.btnYes.text = "Ok"

        view.alertLy.btnYes.setOnClickListener {
            if (alertOkListner!=null) {
                when(alertOkListner){
                    is Utils.OnClickListener ->{
                        alertOkListner.onClick(it)
                    }
                }
            }
            dialog.dismiss()
        }


        dialog.setContentView(view)
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    open fun showConfirmationAlert(title: String, message: String, alertOkListner: Any?, cancelListner: Any?) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet_ex, null)
        view.errorLy.visibility = View.GONE
        view.alertLy.txtAlertMessage.text = message
        view.alertLy.txtAlertTitle.text = title

        view.alertLy.btnYes.setOnClickListener {
            alertOkListner as Utils.OnClickListener
            alertOkListner.onClick(it)
            dialog.dismiss()
        }
        view.alertLy.btnNo.setOnClickListener {
            cancelListner as Utils.OnClickListener
            cancelListner.onClick(it)
            dialog.dismiss()
        }


        dialog.setContentView(view)
        dialog.show()
    }

    open fun redirectStore(updateUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


}
