package br.camera.onvif

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

/*
Criado em 30/11/2020
Evalton Gomes
evalton.nunes@gmail.com
* */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        Handler().postDelayed({iniciar()},3000)
    }

    private fun iniciar(){
        var intent = Intent(this, CadastrarCamera::class.java)
        startActivity(intent)
        finish()
    }


}