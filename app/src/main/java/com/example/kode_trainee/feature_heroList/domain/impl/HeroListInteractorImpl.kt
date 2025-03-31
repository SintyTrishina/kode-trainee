package com.example.kode_trainee.feature_heroList.domain.impl

import com.example.kode_trainee.feature_heroList.domain.api.HeroListInteractor
import com.example.kode_trainee.feature_heroList.domain.api.HeroRepository
import com.example.kode_trainee.utils.Resource
import java.util.concurrent.Executors

class HeroListInteractorImpl(private val repository: HeroRepository): HeroListInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun getHeroesByPublisher(term: String, consumer: HeroListInteractor.TrackConsumer) {

        executor.execute {
            when (val resource = repository.getHeroesByPublisher(term)) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }

                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }
            }
        }
    }
}