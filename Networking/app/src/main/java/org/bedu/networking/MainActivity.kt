package org.bedu.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var btnRequest: Button

    private val url = "https://my.api.mockaroo.com/create_user_to_bedu_api.json?key=6b123310"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRequest= findViewById(R.id.btnRequest)
        btnRequest.setOnClickListener{
            llamadaAsincrona()
        }
    }
    //ponemos en cola la petición en un hilo, y se llama a un callback en otro hilo cuando la respuesta está lista
    fun llamadaAsincrona(){

        //instanciando al cliente
        val okHttpClient = OkHttpClient()

        //obteniendo la url completa
        Log.d("Response", url)

        //El objeto Request contiene todos los parámetros de la petición (headers, url, body etc)
        val request = Request.Builder()
            .url(url)
            .build()

        //Enviando y recibiendo las llamadas de forma asíncrona
        okHttpClient.newCall(request).enqueue(object : Callback {

            //El callback a ejecutar cuando hubo un error
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Response", e.toString())
                Log.e("Error",e.toString())
            }

            //El callback a ejectutar cuando obtuvimos una respuesta
            override fun onResponse(call: Call, response: Response) {
                try {
                    val json = JSONArray(response.body?.string())
                    Log.d("Response", json.toString())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }
}