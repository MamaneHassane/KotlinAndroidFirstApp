package com.example.usingapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    private val apiUrl = "http://192.168.0.23:3500/musicrank";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val requestQueue = Volley.newRequestQueue(this);
        val request = JsonObjectRequest(
            Request.Method.GET,
            apiUrl,
            null,
            {
                result ->
                    val musicArray = result.getJSONArray("data");
                    Log.d("Resultat de la requête", musicArray.toString());
                for(i in 0 until musicArray.length()) {
                    val music = musicArray.getJSONObject(i);
                    Log.d("Une musique", music.toString());

                }

            },
            {
                error ->
                    Log.d("Erreur de la requête", error.toString());
            }
        );
        requestQueue.add(request)
    }
}
