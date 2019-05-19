package apps.ranganathan.configlibrary.base

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
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
import apps.ranganathan.configlibrary.activity.AppImagePickerActivity
import com.bumptech.glide.Glide
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.Serializable


open class BaseAppActivity : AppImagePickerActivity() {

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

    open fun loadImage(url: String, imageView: ImageView, placeHolder: Int) {
        Picasso.get().load(url)
            .placeholder(placeHolder)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    makeLog(e!!.localizedMessage)
                }
            })
    }

    open fun loadImage(context:Context, url: String, imageView: ImageView, placeHolder: Int) {
        Glide.with(context)
            .load(url)
            .placeholder(placeHolder)
            .into(imageView)
    }


    private lateinit var bundle: Bundle

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


}
