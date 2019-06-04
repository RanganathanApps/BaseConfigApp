package apps.ranganathan.configlibrary.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

open class AppImagePickerActivity : PermissionsActivity() {

    lateinit var imagePickerListner: ImagePickerListener



    open interface ImagePickerListener{
        open fun onPicked(bitmap: Bitmap)
        open fun onCancelled()
    }
    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
        private val IMAGE_DIRECTORY = "/imageDocuments"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
         if (resultCode == Activity.RESULT_CANCELED){
             imagePickerListner.onCancelled()
             return
         }else{
             if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM) {
                 if (data != null) {
                     val contentURI = data!!.data
                     try {
                         val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                         imagePickerListner.onPicked(bitmap)
                     } catch (e: IOException) {
                         e.printStackTrace()
                         showToast(e.localizedMessage)
                     }

                 }

             } else if (requestCode == REQUEST_TAKE_PHOTO) {
                 val thumbnail = data!!.extras!!.get("data") as Bitmap
                 imagePickerListner.onPicked(thumbnail)
             }
         }

    }

    open fun selectImageInAlbum(activity: AppCompatActivity, imagePickerListener1: ImagePickerListener) {

        try {
            this@AppImagePickerActivity.imagePickerListner = imagePickerListener1
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                    openAlbum()
                }else{
                    getPermission( activity,object : PermissionListener {

                        override fun onGranted() {
                            openAlbum()
                        }

                        override fun onDenied(deniedPermissions: List<String>) {
                            showToast("deniedPermissions")
                        }

                        override fun onDeniedForeEver(deniedPermissions: List<String>) {
                            showToast("onDeniedForeEver")
                        }
                    }, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        } catch (e: Exception) {
            showToast(e.localizedMessage)
        }


    }

    open fun takePhotoInCamera(activity: AppCompatActivity, imagePickerListener1: ImagePickerListener) {

        try {
            this@AppImagePickerActivity.imagePickerListner = imagePickerListener1
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
                    takePhoto(imagePickerListener1)
                }else{
                    getPermission( activity,object : PermissionListener {

                        override fun onGranted() {
                            makeLog("Permissions granted!")
                            takePhoto(imagePickerListener1)
                        }

                        override fun onDenied(deniedPermissions: List<String>) {
                            showToast("deniedPermissions")
                        }

                        override fun onDeniedForeEver(deniedPermissions: List<String>) {
                            showToast("onDeniedForeEver")
                        }
                    }, Manifest.permission.CAMERA)
                }
            }
        } catch (e: Exception) {
            showToast(e.localizedMessage)
        }


    }

    private fun openAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent,
                REQUEST_SELECT_IMAGE_IN_ALBUM
            )
        }
    }

    open fun takePhoto(imagePickerListener1: ImagePickerListener) {
        this@AppImagePickerActivity.imagePickerListner = imagePickerListener1
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent1.resolveActivity(packageManager) != null) {
            startActivityForResult(intent1,
                REQUEST_TAKE_PHOTO
            )
        }
    }

}