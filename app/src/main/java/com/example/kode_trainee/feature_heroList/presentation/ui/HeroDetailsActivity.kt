package com.example.kode_trainee.feature_heroList.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kode_trainee.R
import com.example.kode_trainee.feature_heroList.domain.models.Hero

class HeroDetailsActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_HERO = "extra_hero"

        fun newIntent(context: Context, hero: Hero): Intent {
            return Intent(context, HeroDetailsActivity::class.java).apply {
                putExtra(EXTRA_HERO, hero)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_details)

        val hero = intent.getParcelableExtra<Hero>(EXTRA_HERO)
        hero?.let { bindHeroData(it) }
    }

    private fun bindHeroData(hero: Hero) {
        // Находим View и заполняем данными
        println("Image URL: ${hero.images}")
        findViewById<TextView>(R.id.heroName).text = hero.biography.aliases?.joinToString(",")
        findViewById<TextView>(R.id.realName).text = hero.biography.fullName
        findViewById<TextView>(R.id.placeOfBirth).text = buildString {
            append("Публикация: ${hero.biography.publisher}\n")
            append("Место рождения: ${hero.biography.placeOfBirth}\n")
            append("Первое появление: ${hero.biography.firstAppearance}\n")
        }
        val imageView = findViewById<ImageView>(R.id.heroImage)
        Glide.with(this)
            .load(hero.images)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .into(imageView)

        }

    }
