package com.example.bb_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bb_app.R
import com.example.bb_app.models.Character

class SeasonsAdapter(
    private val seasons: List<String>
                        ): RecyclerView.Adapter<SeasonsAdapter.CustomViewHolder>() {
    private lateinit var clickListener: ClickListener

    override fun getItemCount(): Int {
        return seasons.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemLayout = layoutInflater.inflate(R.layout.season_item, parent, false)

        return CustomViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.seasonName.text = seasons[position]
        holder.view.setOnClickListener(View.OnClickListener {
            clickListener.onItemClick(position)
        })
    }

    fun setOnItemClickListener(listener: ClickListener) {
        this.clickListener = listener
    }

    class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val seasonName: TextView
        val seasonImage: ImageView

        init {
            seasonName = view.findViewById(R.id.season_name)
            seasonImage = view.findViewById(R.id.season_image)
        }
    }

    interface ClickListener {
        fun onItemClick(position: Int)
        //fun onLongItemClick(position: Int, view: View?)
    }
}