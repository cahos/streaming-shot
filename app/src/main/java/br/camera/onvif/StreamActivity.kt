package br.camera.onvif

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import br.camera.onvif.screenshot.Screenshot
import br.camera.onvif.screenshot.Sharescreenshot
import br.camera.onvif.screenshot.Showscreenshot
import br.camera.onvif.service.ServiceSegundoPlano
import com.pedro.vlc.VlcListener
import com.pedro.vlc.VlcVideoLibrary
import kotlinx.android.synthetic.main.activity_stream.*
import java.io.File

/*
Criado em 30/11/2020
Evalton Gomes
* */
class StreamActivity : AppCompatActivity(), VlcListener, View.OnClickListener {

    private var vlcVideoLibrary: VlcVideoLibrary? = null

    lateinit var screenshot : Screenshot
    lateinit var sharescreenshot: Sharescreenshot
    lateinit var constsView : ConstraintLayout
    lateinit var service: ServiceSegundoPlano
    var _URL: String=""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stream)
        supportActionBar!!.hide()

        val surfaceView = findViewById<TextureView>(R.id.surfaceView)
        val bStartStop = findViewById<Button>(R.id.b_start_stop)
        bStartStop.setOnClickListener(this)
        vlcVideoLibrary = VlcVideoLibrary(this , this , surfaceView)

        constsView = findViewById(R.id.ConstsView)
        screenshot = Screenshot(this)
        sharescreenshot = Sharescreenshot()
        startService(Intent(baseContext , ServiceSegundoPlano::class.java))

    }

    fun startService(url: String) {
        var serviceIntent =Intent(this , ServiceSegundoPlano::class.java)
        serviceIntent.putExtra("url",url)
        startService(serviceIntent)
    }


    /*---------------------------------PRINT SCREEM----------------------------------*/

    fun ambilscreenshot(){
        var bitmap: Bitmap? = null
        bitmap = screenshot.getViewScreenshot(
            surfaceView ,
            constsView.height ,
            constsView.width
        )
        bitmap?.let { screenshot.gravarFotos(bitmap) }
    }

    fun tirarFoto(){
        var thread: Mythread = Mythread()
        thread.start()
       /* var start: Boolean = true
           Thread(Runnable {
            while (start) {
                ambilscreenshot()
                Thread.sleep(10000)
            }
        }).start()*/
    }

    inner class  Mythread:Thread() {
        var start: Boolean = true
        override fun run() {
            while (start) {
                verificaCamera(_URL)
                ambilscreenshot()
                Thread.sleep(300000)
            }
        }
    }

    class Timer {
        companion object {
            @JvmStatic
            fun call(ms: Long , f: () -> Unit) {
                object : CountDownTimer(ms , ms){
                    override fun onFinish() { f() }
                    override fun onTick(millisUntilFinished: Long) {}
                }.start()
            }
        }
    }

    fun bagiscreenshot() {
        val fileScreenshot = File(this.getExternalFilesDir(null)!!.absolutePath + "/Screenshot/Screenshot.jpg")
        startActivity(sharescreenshot.ShareFileScreensshot(fileScreenshot))
    }

    fun lihatfilescreenshot(){
        startActivity(Intent(this , Showscreenshot::class.java))
    }

    /**
     * Chamada que indica que o video esta carregando
     */
    override fun onComplete() {
        Toast.makeText(this , "Carregando video Aguarde..." , Toast.LENGTH_LONG).show()
        Handler().postDelayed({tirarFoto()},10000)
    }

    /**
     * retorna erro, caso não se conecte na camera
     */
    override fun onError() {
        Toast.makeText(this , "Error, camera não conectada." , Toast.LENGTH_SHORT).show()
        vlcVideoLibrary?.stop()
    }

    override fun onClick(v: View?) {
        vlcVideoLibrary?.let { vlcVideoLibrary ->
            if (!vlcVideoLibrary.isPlaying) {
                val url = intent.getStringExtra(RTSP_URL)
                _URL = url;
                vlcVideoLibrary.play(url)
                startService(url)
            } else {
                vlcVideoLibrary.stop()
            }
        }
    }

    private fun voltar(){
        var intent =Intent(this , CadastrarCamera::class.java)
        startActivity(intent)
        finish()
    }

    private fun verificaCamera(url:String) {
        vlcVideoLibrary?.let { vlcVideoLibrary ->
            if (!vlcVideoLibrary.isPlaying) {
                vlcVideoLibrary.stop()
                vlcVideoLibrary.play(url)
            }
        }
    }

}




