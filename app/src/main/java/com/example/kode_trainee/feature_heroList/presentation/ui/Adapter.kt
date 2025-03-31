package com.example.kode_trainee.feature_heroList.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kode_trainee.R
import com.example.kode_trainee.feature_heroList.domain.models.Hero

class Adapter(
    private val onItemClickListener: (Hero) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    var heroes: ArrayList<Hero> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hero_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return heroes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hero = heroes[position]
        holder.bind(hero)
        holder.itemView.setOnClickListener {
            onItemClickListener(hero)
        }
    }
}