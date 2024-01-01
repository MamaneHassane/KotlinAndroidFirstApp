package com.example.usingapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MusicAdapter(private val musicList: ArrayList<Music>) : RecyclerView.Adapter<MusicAdapter.ViewHolder>(){
    private lateinit var context : Context;
    class ViewHolder(itemView: View ): RecyclerView.ViewHolder(itemView){
        val img = itemView.findViewById<ImageView>(R.id.imageView);
        val rank_songname = itemView.findViewById<TextView>(R.id.rank_songname);
        // val artist_year = itemView.findViewById<TextView>(R.id.artist_year);
        val artist_streams = itemView.findViewById<TextView>(R.id.artist_year);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context;
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_item,parent,false);
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return musicList.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val music = musicList[position]
        Glide.with(context).load(music.imageUrl).into(holder.img);
        holder.rank_songname.text = music.songname  //music.rank.toString() + "-"music.songname;
        // holder.artist_year.text = music.artist //+ "  " + music.year.toString();
        holder.artist_streams.text = music.artist + "  " + music.streams.toString();
    }
}