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


    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    fun bind(item: Hero) {
        aliasView.text = item.biography.aliases[1]
        realNameView.text = item.biography.fullName

        val cornerRadius = dpToPx(2f, itemView.context)

        Glide.with(itemView)
            .load(item.image.url)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(cornerRadius))
            .into(imageView)

    }
}