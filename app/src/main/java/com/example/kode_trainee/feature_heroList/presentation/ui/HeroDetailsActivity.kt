package com.example.kode_trainee.feature_heroList.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kode_trainee.R
import com.example.kode_trainee.feature_heroList.domain.models.Hero

class HeroDetailsActivity : AppCompatActivity() {
private lateinit var buttonLike: ImageButton
    private var isFavorite = false
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

        buttonLike = findViewById(R.id.buttonLike)

        val hero = intent.getParcelableExtra<Hero>(EXTRA_HERO)
        hero?.let {
            bindHeroData(it)
            isFavorite = hero.isFavorite
            updateFavoriteButton()
        }

        buttonLike.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteButton()

            // Здесь можно добавить логику сохранения в избранное
            hero?.isFavorite = isFavorite
        }
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
    private fun updateFavoriteButton() {
        buttonLike.setImageResource(
            if (isFavorite) R.drawable.ic_star_filled
            else R.drawable.ic_star_outline
        )

        // Можно добавить анимацию:
        buttonLike.animate()
            .scaleX(0.8f)
            .scaleY(0.8f)
            .setDuration(100)
            .withEndAction {
                buttonLike.animate()
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .setDuration(100)
                    .withEndAction {
                        buttonLike.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .start()
                    }
                    .start()
            }
            .start()
    }

    }
