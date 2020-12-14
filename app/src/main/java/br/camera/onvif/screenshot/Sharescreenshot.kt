package br.camera.onvif.screenshot

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.StrictMode
import android.util.Log
import java.io.File


class Sharescreenshot {

    private var bmpUri: Uri? = null

    init {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    fun ShareFileScreensshot(file: File?): Intent? {
        bmpUri = Uri.fromFile(file)
        return if (bmpUri != null) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri)
            shareIntent.type = "image/*"
            shareIntent
        } else {
            null
        }
    }

}