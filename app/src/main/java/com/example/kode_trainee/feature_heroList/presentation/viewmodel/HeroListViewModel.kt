package com.example.kode_trainee.feature_heroList.presentation.viewmodel

import android.os.Handler
import android.os.Looper
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

    private val _toastState = SingleLiveEvent<String?>()
    val toastState: LiveData<String?> get() = _toastState


    private val _navigateToHero = SingleLiveEvent<Hero>()
    val navigateToHero: LiveData<Hero> get() = _navigateToHero

    private var userChoice = ""

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val handler = Handler(Looper.getMainLooper())


    private val searchRunnable = Runnable {
        search(publisher = userChoice)
    }

    override fun onCleared() {
        handler.removeCallbacks(searchRunnable)
    }

    // Обработка нажатия
    fun onTrackClicked(hero: Hero) {
        _navigateToHero.postValue(hero)
    }

    fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }


    fun search(publisher: String) {
        if (publisher.isNotEmpty()) {
            userChoice = publisher
            renderState(State.Loading)

            interactor.getHeroesByPublisher(
                publisher,
                object : HeroListInteractor.TrackConsumer {
                    override fun consume(data: List<Hero>?, errorMessage: String?) {

                        renderState(State.Content(emptyList()))

                        if (!data.isNullOrEmpty()) {
                            renderState(State.Content(data))
                        } else {
                            renderState(State.Empty(resourcesProvider.getNothingFoundText()))
                        }
                        if (errorMessage != null) {
                            renderState(State.Error(resourcesProvider.getSomethingWentWrongText()))
                            _toastState.postValue(errorMessage)

                        }
                    }
                })
        }
    }

    private fun renderState(state: State) {
        _searchState.postValue(state)
    }


//    fun showSearchHistory() {
//        handler.post {
//            val history = searchHistoryInteractor.getHistory()
//            if (history.isNotEmpty()) {
//                renderState(SearchState.History(history))
//            } else {
//                hideSearchHistory()
//            }
//        }
//    }
//
//    fun hideSearchHistory() {
//        handler.post {
//            renderState(SearchState.Content(ArrayList()))
//        }
//    }
}