package apps.ranganathan.configapp

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import apps.ranganathan.configlibrary.base.BaseAppActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseAppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setConnectivityChange()

        btnOpenAlbum.setOnClickListener {

            /*camera*/
            takePhotoInCamera(this, object : ImagePickerListener {
                override fun onPicked(bitmap: Bitmap) {

                    showToast("bitmap")
                }
            })
            /*gallery*/
            selectImageInAlbum(this, object : ImagePickerListener {
                override fun onPicked(bitmap: Bitmap) {
                    imgPhoto.setImageBitmap(bitmap)
                    showToast("bitmap")
                }
            })


        }

        imgPhoto.setOnClickListener {
            loadImage(
                "https://cdn-images-1.medium.com/max/1600/1*PG7q_78CFDUWF4Q8z247Lw.png",
                imgPhoto,
                R.drawable.abc_seekbar_thumb_material, R.drawable.abc_ic_star_black_16dp, progress
            )

            attentionDialog(context, object : AppOkInter {
                override fun onOk() {
                    makeLog("Alert dialog dismissed!")
                }
            }, "You have Displayed a dialog", R.mipmap.ic_launcher, "Sample Alert")
        }
        btnAskPermission.setOnClickListener {


            getPermission(
                this, object : PermissionListener {

                    override fun onGranted() {

                    }

                    override fun onDenied(deniedPermissions: List<String>) {
                        showToast("deniedPermissions")
                    }

                    override fun onDeniedForeEver(deniedPermissions: List<String>) {
                        showToast("onDeniedForeEver")
                    }
                }, Manifest.permission.CALL_PHONE,
                Manifest.permission.CAMERA
            )
        }


    }


    override fun onStart() {
        super.onStart()


    }
}
