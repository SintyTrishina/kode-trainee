package com.example.kode_trainee.feature_heroList.presentation.ui

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.kode_trainee.R
import com.example.kode_trainee.feature_heroList.domain.models.Hero

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.image)
    private val aliasView: TextView = itemView.findViewById(R.id.alias)
    private val realNameView: TextView = itemView.findViewById(R.id.realName)
    private val publisher: TextView = itemView.findViewById(R.id.publisher)
    private val imagePublisher: ImageView = itemView.findViewById(R.id.imagePublisher)


    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    fun bind(item: Hero) {
        aliasView.text = item.name
        realNameView.text = item.biography.fullName
        publisher.text = item.biography.publisher

        val cornerRadius = dpToPx(16f, itemView.context)

        Glide.with(itemView)
            .load(item.images)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(cornerRadius))
            .into(imageView)

        when (publisher.text) {
            "Marvel Comics" -> imagePublisher.setImageResource(R.drawable.marvel)
            "DC Comics" -> imagePublisher.setImageResource(R.drawable.dc)
            "Dark Horse Comics" -> imagePublisher.setImageResource(R.drawable.dark_horse)
            "George Lucas" -> imagePublisher.setImageResource(R.drawable.george_lucas)
            "NBC - Heroes" -> imagePublisher.setImageResource(R.drawable.nbc)
        }
    }
}