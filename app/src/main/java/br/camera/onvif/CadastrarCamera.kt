package br.camera.onvif
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rvirin.onvif.onvifcamera.*

/*
Criado em 30/11/2020
Evalton Gomes

* */

const val RTSP_URL = "com.rvirin.onvif.onvifcamera.demo.RTSP_URL"

class CadastrarCamera : AppCompatActivity(), OnvifListener {

    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_camera)
      // supportActionBar!!.hide()
    }

    override fun requestPerformed(response: OnvifResponse) {

        Log.d("INFO", response.parsingUIMessage)
        toast?.cancel()

        if (!response.success) {
            Log.e("ERROR", "request failed: ${response.request.type} \n Response: ${response.error}")
            toast = Toast.makeText(this, "⛔️ Request failed: ${response.request.type}", Toast.LENGTH_SHORT)
            toast?.show()
        }
        // Solicita Informações do Dispositivo
        else if (response.request.type == OnvifRequest.Type.GetServices) {
            currentDevice.getDeviceInformation()
        }
        // Faz a solicitação do perfil, caso as informações do dispositivo esteja preenchida
        else if (response.request.type == OnvifRequest.Type.GetDeviceInformation) {
            val textView = findViewById<TextView>(R.id.explanationTextView)
            textView.text = response.parsingUIMessage
            currentDevice.getProfiles()
        }
        // Caso tenha passado pelos fluxos, solicita a uri para visualizar o vido
        else if (response.request.type == OnvifRequest.Type.GetProfiles) {
            val profilesCount = currentDevice.mediaProfiles.count()
            currentDevice.getStreamURI()
        }
        // Prepara a visualização do video
        else if (response.request.type == OnvifRequest.Type.GetStreamURI) {
            val button = findViewById<TextView>(R.id.button)
            button.text = "Vizualizar"
            toast = Toast.makeText(this, "Conexão realizada com Sucesso!", Toast.LENGTH_SHORT)
            toast?.show()
        }
    }

    fun buttonClicked(view: View) {

        //Verifica se existe conexão ativa e salva os dados
        if (currentDevice.isConnected) {
            currentDevice.rtspURI?.let { uri ->
               var url = uri;
               val intent = Intent(this, StreamActivity::class.java).apply {
                   putExtra(RTSP_URL, uri)
               }
               startActivity(intent)
           }
        } else {

            // recebe os dados de acesso da camera
            val ipAddress = (findViewById<EditText>(R.id.ipAddress)).text.toString()
            val login = (findViewById<EditText>(R.id.login)).text.toString()
            val password = (findViewById<EditText>(R.id.password)).text.toString()

            if (ipAddress.isNotEmpty() &&
                login.isNotEmpty() &&
                password.isNotEmpty()) {

                // Inicia Chamada para o web service, envia os dados e recebe as informações da camera
                currentDevice = OnvifDevice(ipAddress, login, password)
                currentDevice.listener = this
                currentDevice.getServices()

            } else {
                toast?.cancel()
                toast = Toast.makeText(this,
                        "IP Login e Chave de Acesso São Obrigatorios!",
                        Toast.LENGTH_LONG)
                toast?.show()
            }
        }
    }


}

