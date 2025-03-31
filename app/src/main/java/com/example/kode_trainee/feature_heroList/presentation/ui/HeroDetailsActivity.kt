package com.example.kode_trainee.feature_heroList.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kode_trainee.R
import com.example.kode_trainee.databinding.ActivityHeroDetailsBinding
import com.example.kode_trainee.feature_heroList.domain.models.Hero

class HeroDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeroDetailsBinding
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
        binding = ActivityHeroDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hero = intent.getParcelableExtra<Hero>(EXTRA_HERO)
        hero?.let {
            bindHeroData(it)
            isFavorite = hero.isFavorite
            updateFavoriteButton()
        }

        binding.buttonLike.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteButton()
            hero?.isFavorite = isFavorite
        }

        binding.back.setOnClickListener { finish() }
    }

    private fun bindHeroData(hero: Hero) {
        println("Image URL: ${hero.images}")
        binding.heroName.text = hero.biography.aliases?.joinToString(",")
        binding.realName.text = hero.biography.fullName
        binding.placeOfBirth.text = buildString {
            append("Характеристики:\nИнтеллект - ${hero.powerstats.intelligence}\nСила - ${hero.powerstats.power}\nСкорость - ${hero.powerstats.speed}\nВыносливость - ${hero.powerstats.durability}\nБой - ${hero.powerstats.combat}\n")
            append("Пол - ${hero.appearance?.gender}\nРаса - ${hero.appearance?.race}\nРост - ${(hero.appearance?.height?.get(1) ?: "")}, Вес - ${hero.appearance?.weight?.get(1) ?: ""}\n")
            append("Публикация: ${hero.biography.publisher}\n")
            append("Место рождения: ${hero.biography.placeOfBirth}\n")
            append("Первое появление: ${hero.biography.firstAppearance}\n")
            append("Стиль героя: ${hero.biography.alignment}")
        }

        Glide.with(this)
            .load(hero.images)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .into(binding.heroImage)


    }

    private fun updateFavoriteButton() {
        binding.buttonLike.setImageResource(
            if (isFavorite) R.drawable.ic_star_filled
            else R.drawable.ic_star_outline
        )

        // Можно добавить анимацию:
        binding.buttonLike.animate()
            .scaleX(0.8f)
            .scaleY(0.8f)
            .setDuration(100)
            .withEndAction {
                binding.buttonLike.animate()
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .setDuration(100)
                    .withEndAction {
                        binding.buttonLike.animate()
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
