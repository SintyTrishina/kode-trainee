package com.example.kode_trainee.feature_heroList.presentation.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.bumptech.glide.Glide
import com.example.kode_trainee.R
import com.example.kode_trainee.databinding.ActivityHeroDetailsBinding
import com.example.kode_trainee.feature_heroList.domain.models.Hero
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HeroDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeroDetailsBinding
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var gson: Gson
    private var currentHero: Hero? = null

    companion object {
        private const val EXTRA_HERO = "extra_hero"
        private const val PREFS_NAME = "favorites_prefs"
        private const val FAVORITES_KEY = "favorites_list"

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

        // Инициализация SharedPreferences и Gson
        sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        gson = Gson()

        currentHero = intent.getParcelableExtra<Hero>(EXTRA_HERO)
        currentHero?.let { hero ->
            // Загружаем состояние избранного
            hero.isFavorite = isHeroFavorite(hero.id)
            bindHeroData(hero)
            updateFavoriteButton(hero.isFavorite)
        }

        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        binding.buttonLike.setOnClickListener {
            currentHero?.let { hero ->
                val newFavoriteState = !hero.isFavorite
                hero.isFavorite = newFavoriteState
                updateFavoriteButton(newFavoriteState)
                saveFavoriteState(hero)
            }
        }

        binding.back.setOnClickListener { finish() }
    }

    private fun bindHeroData(hero: Hero) {
        binding.heroName.text = hero.name
        binding.realName.text = hero.biography.fullName ?: "Unknown"

        val detailsText = buildString {
            append("Характеристики:\n")
            append("Интеллект - ${hero.powerstats.intelligence}\n")
            append("Сила - ${hero.powerstats.power}\n")
            append("Скорость - ${hero.powerstats.speed}\n")
            append("Выносливость - ${hero.powerstats.durability}\n")
            append("Бой - ${hero.powerstats.combat}\n\n")

            append("Внешность:\n")
            append("Пол - ${hero.appearance?.gender ?: "Unknown"}\n")
            append("Раса - ${hero.appearance?.race ?: "Unknown"}\n")
            append("Рост - ${hero.appearance?.height?.get(1) ?: "Unknown"}, ")
            append("Вес - ${hero.appearance?.weight?.get(1) ?: "Unknown"}\n\n")

            append("Биография:\n")
            append("Публикация: ${hero.biography.publisher ?: "Unknown"}\n")
            append("Место рождения: ${hero.biography.placeOfBirth ?: "Unknown"}\n")
            append("Первое появление: ${hero.biography.firstAppearance ?: "Unknown"}\n")
            append("Стиль героя: ${hero.biography.alignment ?: "Unknown"}")
        }
        binding.placeOfBirth.text = detailsText

        Glide.with(this)
            .load(hero.images)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .centerCrop()
            .into(binding.heroImage)
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        binding.buttonLike.setImageResource(
            if (isFavorite) R.drawable.ic_star_filled
            else R.drawable.ic_star_outline
        )

        binding.buttonLike.animate()
            .scaleX(0.8f).scaleY(0.8f)
            .setDuration(100)
            .withEndAction {
                binding.buttonLike.animate()
                    .scaleX(1.2f).scaleY(1.2f)
                    .setDuration(100)
                    .withEndAction {
                        binding.buttonLike.animate()
                            .scaleX(1f).scaleY(1f)
                            .setDuration(100)
                            .start()
                    }
                    .start()
            }
            .start()
    }

    private fun isHeroFavorite(heroId: String): Boolean {
        val favorites = getFavorites()
        return favorites.any { it.id == heroId }
    }

    private fun saveFavoriteState(hero: Hero) {
        val favorites = getFavorites().toMutableList()

        if (hero.isFavorite) {
            if (!favorites.any { it.id == hero.id }) {
                favorites.add(hero)
            }
        } else {
            favorites.removeAll { it.id == hero.id }
        }

        saveFavorites(favorites)
    }

    private fun getFavorites(): List<Hero> {
        val json = sharedPrefs.getString(FAVORITES_KEY, "[]") ?: "[]"
        val type = object : TypeToken<List<Hero>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    private fun saveFavorites(favorites: List<Hero>) {
        val json = gson.toJson(favorites)
        sharedPrefs.edit { putString(FAVORITES_KEY, json) }
    }
}