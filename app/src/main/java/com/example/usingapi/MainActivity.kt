package com.example.usingapi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    private val apiUrl = "http://192.168.0.23:3500/musicrank"
    val musicList = arrayListOf<Music>()
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)

        val request = object : JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                Response.Listener { result ->
                    // Succès de la requête
                    val musicArray = result.getJSONArray("data")
                    Log.d("Resultat de la requête", musicArray.toString())

                    for (i in 0 until musicArray.length()) {
                        val musicObj = musicArray.getJSONObject(i)
                        Log.d("Une musique", musicObj.toString())
                        val music = Music(
                                musicObj.getInt("ranking"),
                                musicObj.getString("artist_and_title"),
                                musicObj.getInt("daily")
                        )
                        musicList.add(music)
                    }
                    // Mettre à jour l'adaptateur avec la nouvelle liste de musiques
                    recyclerView?.adapter?.notifyDataSetChanged()
                    Log.d("Liste des musiques", musicList.toString())
                },
                Response.ErrorListener { error ->
                    // Erreur de la requête
                    Log.d("Erreur de la requête", error.toString())
                }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["X-RapidAPI-Key"] = '81915a9428msh0c0a9378ee03a9cp15a032jsnd3b15aff1d87'
                headers["X-RapidAPI-Host"] = 'musicdata-api.p.rapidapi.com'
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = MusicAdapter(musicList)
        requestQueue.add(request)
    }
}
