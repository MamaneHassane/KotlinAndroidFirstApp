package com.example.usingapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    private val apiUrl = "http://192.168.0.23:3500/musicrank";
    val musicList = arrayListOf<Music>();
    var recyclerView: RecyclerView? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView);
        val requestQueue = Volley.newRequestQueue(this);
        val request = JsonObjectRequest(
            Request.Method.GET,
            apiUrl,
            null,
            {
                result ->
                    val musicArray = result.getJSONArray("data");
                    Log.d("Resultat de la requête", musicArray.toString());
                for( i in 0 until musicArray.length()) {
                    val musicObj = musicArray.getJSONObject(i);
                    Log.d("Une musique", musicObj.toString());
                    val music = Music(
                        musicObj.getInt("rank"),
                        musicObj.getString("songname"),
                        musicObj.getString("artist"),
                        musicObj.getInt("year"),
                        musicObj.getString("imageUrl"),
                    );
                    musicList.add(music);
                }
                Log.d("Liste des musiques", musicList.toString());
            },
            {
                error ->
                    Log.d("Erreur de la requête", error.toString());
            }
        );
        recyclerView?.layoutManager = LinearLayoutManager(this);
        recyclerView?.adapter = MusicAdapter(musicList);
        requestQueue.add(request)
    }
}
