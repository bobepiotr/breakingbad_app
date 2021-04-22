package com.example.bb_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bb_app.R
import com.example.bb_app.models.Character

class CharactersAdapter(
    private val characters: List<Character>
                        ): RecyclerView.Adapter<CharactersAdapter.CustomViewHolder>() {
    private lateinit var clickListener: ClickListener

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemLayout = layoutInflater.inflate(R.layout.character_item, parent, false)

        return CustomViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val character = characters[position]
        val nickname = "\taka ${character.nickname}"

        holder.nameText.text = character.name
        holder.nicknameText.text = nickname
        holder.view.setOnClickListener(View.OnClickListener {
            clickListener.onItemClick(position)
        })
    }

    fun setOnItemClickListener(listener: ClickListener) {
        this.clickListener = listener
    }

    class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val nameText: TextView
        val nicknameText: TextView
        val avatar: ImageView

        init {
            nameText = view.findViewById(R.id.name_text)
            nicknameText = view.findViewById(R.id.nickname_text)
            avatar = view.findViewById(R.id.character_avatar)
        }
    }

    interface ClickListener {
        fun onItemClick(position: Int)
        //fun onLongItemClick(position: Int, view: View?)
    }
}