package br.camera.onvif.screenshot

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceView
import android.view.TextureView
import android.view.View
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class Screenshot(private val mContext: Context){

    fun getViewScreenshot(view: View, height: Int, width: Int): Bitmap? {
        val bitmap: Bitmap
        return if (view is TextureView) {
            bitmap = (view as TextureView).getBitmap()
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            canvas.setBitmap(null)
            bitmap
        } else {
            bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.RGB_565)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            canvas.setBitmap(null)
            bitmap
        }
    }

 /*   fun saveScreenshot(bitmap: Bitmap) {

        val filename    = "Screenshot.png"
        val defaultFile = File(mContext.getExternalFilesDir(null)!!.absolutePath + "/Screenshot")

        if (!defaultFile.exists()) defaultFile.mkdirs()
        var file = File(defaultFile, filename)
        if (file.exists()) {
            file.delete()
            file = File(defaultFile, filename)
        }
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.e("Filenotfound", e.message, e)
        } catch (e: IOException) {
            Log.e("IOException", e.message, e)
        }

    }


    fun getCapturaFotos(view: View, height: Int, width: Int): Bitmap? {
        val bitmap: Bitmap
        return if (view is TextureView) {
            bitmap = (view as TextureView).getBitmap()
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            canvas.setBitmap(null)
            bitmap
        } else {
            bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.RGB_565)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            canvas.setBitmap(null)
            bitmap
        }
    }

    interface QuickShotListener {
        fun onQuickShotSuccess(path: String?)
        fun onQuickShotFailed(path: String?, errorMsg: String?)
    }*/

    fun getCurSysDate(): String {
        return SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Date())
    }


    fun gravarFotos(bitmap: Bitmap) {
            val filename="capture_" + getCurSysDate() + ".png"
            val defaultFile=
                File(mContext.getExternalFilesDir(null)!!.absolutePath + "/Captura_Fotos")
            if (!defaultFile.exists()) defaultFile.mkdirs()
            var file=File(defaultFile , filename)
            if (file.exists()) {
                file.delete()
                file=File(defaultFile , filename)
            }
            val fos: FileOutputStream
            try {
                fos=FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG , 80 , fos)
                fos.flush()
                fos.close()
            } catch (e: FileNotFoundException) {
                Log.e("Filenotfound" , e.message , e)
            } catch (e: IOException) {
                Log.e("IOException" , e.message , e)
            }
    }
}