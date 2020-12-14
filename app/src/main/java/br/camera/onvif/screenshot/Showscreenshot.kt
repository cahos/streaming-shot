package br.camera.onvif.screenshot

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import br.camera.onvif.R

class Showscreenshot : AppCompatActivity() {

    private lateinit var viewscreenshot : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showscreenshot)
        this.setTitle("Print Camera.")

        viewscreenshot = findViewById(R.id.viewscreenshot)

        viewscreenshot.setImageBitmap(getFilescreenshoot())

    }

    fun getFilescreenshoot() : Bitmap {
        val fileScreenshot      = this.getExternalFilesDir(null)!!.absolutePath + "/Screenshot/Screenshot.png"
        val screenshotBitmap    = BitmapFactory.decodeFile(fileScreenshot)

        return screenshotBitmap
    }
}
