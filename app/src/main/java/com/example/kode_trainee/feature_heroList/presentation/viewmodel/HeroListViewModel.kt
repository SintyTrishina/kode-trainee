package com.example.kode_trainee.feature_heroList.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kode_trainee.feature_heroList.domain.ResourcesProvider
import com.example.kode_trainee.feature_heroList.domain.api.HeroListInteractor
import com.example.kode_trainee.feature_heroList.domain.models.Hero
import com.example.kode_trainee.feature_heroList.presentation.SingleLiveEvent

class HeroListViewModel(
    private val interactor: HeroListInteractor,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    private val _searchState = MutableLiveData<State>()
    val searchState: LiveData<State> get() = _searchState

    private val _publishers = MutableLiveData<List<String>>()
    val publishers: LiveData<List<String>> get() = _publishers

    private val _toastState = SingleLiveEvent<String?>()
    val toastState: LiveData<String?> get() = _toastState

    private val _navigateToHero = SingleLiveEvent<Hero>()
    val navigateToHero: LiveData<Hero> get() = _navigateToHero
    private var allHeroes: List<Hero> = emptyList()


    init {
        loadPublishers()
    }

    fun searchByPublisher(publisher: String) {
        _searchState.postValue(State.Loading)

        if (publisher == "All") {
            interactor.getHeroesByPublisher(
                "",
                object : HeroListInteractor.TrackConsumer {
                    override fun consume(data: List<Hero>?, errorMessage: String?) {
                        handleHeroResponse(data, errorMessage)
                        data?.let { allHeroes = it }
                    }
                }
            )
        } else {
            interactor.getHeroesByPublisher(
                publisher,
                object : HeroListInteractor.TrackConsumer {
                    override fun consume(data: List<Hero>?, errorMessage: String?) {
                        handleHeroResponse(data, errorMessage)
                        data?.let { allHeroes = it }
                    }
                }
            )
        }
    }

    private fun handleHeroResponse(data: List<Hero>?, errorMessage: String?) {
        when {
            errorMessage != null -> {
                _searchState.postValue(State.Error(resourcesProvider.getSomethingWentWrongText()))
                _toastState.postValue(errorMessage)
            }

            data.isNullOrEmpty() -> {
                _searchState.postValue(State.Empty(resourcesProvider.getNothingFoundText()))
            }

            else -> {
                _searchState.postValue(State.Content(data))
            }
        }
    }

    private fun loadPublishers() {
        val publishersList = listOf(
            "All",
            "Marvel Comics",
            "DC Comics",
            "Dark Horse Comics",
            "George Lucas",
            "NBC - Heroes"
        )
        _publishers.postValue(publishersList)

    }
}