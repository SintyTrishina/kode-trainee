package com.example.kode_trainee.feature_heroList.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kode_trainee.feature_heroList.domain.models.Hero
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {

    private val _favoriteHeroes = MutableStateFlow<List<Hero>>(emptyList())
    val favoriteHeroes: StateFlow<List<Hero>> = _favoriteHeroes.asStateFlow()

    // Добавляем или удаляем героя из избранного
    fun toggleFavorite(hero: Hero) {
        viewModelScope.launch {
            val currentFavorites = _favoriteHeroes.value.toMutableList()
            if (hero.isFavorite) {
                // Удаляем из избранного
                currentFavorites.removeAll { it.id == hero.id }
            } else {
                // Добавляем в избранное
                currentFavorites.add(hero.copy(isFavorite = true))
            }
            _favoriteHeroes.value = currentFavorites
        }
    }

    // Проверяем, есть ли герой в избранном
    fun isFavorite(heroId: String): Boolean {
        return _favoriteHeroes.value.any { it.id == heroId }
    }

    // Обновляем состояние героя
    fun updateHero(hero: Hero) {
        viewModelScope.launch {
            val currentFavorites = _favoriteHeroes.value.toMutableList()
            val index = currentFavorites.indexOfFirst { it.id == hero.id }
            if (index != -1) {
                currentFavorites[index] = hero
                _favoriteHeroes.value = currentFavorites
            }
        }
    }
}