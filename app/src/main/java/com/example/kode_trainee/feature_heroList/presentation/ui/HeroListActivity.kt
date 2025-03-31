package com.example.kode_trainee.feature_heroList.presentation.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kode_trainee.R
import com.example.kode_trainee.databinding.ActivityHeroListBinding
import com.example.kode_trainee.feature_heroList.domain.models.Hero
import com.example.kode_trainee.feature_heroList.presentation.viewmodel.HeroListViewModel
import com.example.kode_trainee.feature_heroList.presentation.viewmodel.State
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeroListActivity : AppCompatActivity() {

    private val viewModel by viewModel<HeroListViewModel>()
    private lateinit var binding: ActivityHeroListBinding
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var gson: Gson
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .load("https://avatars.mds.yandex.net/get-yapic/69015/jVwB7TFNQt1FSSccebL8NULM6fk-1/orig")
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .into(binding.photoImageView)

        binding.menuButton.setOnClickListener {
           AlertDialog.Builder(this)
               .setTitle("Эксклюзивное предложение")
               .setMessage("Нанять лучшего стажера")
               .setPositiveButton("БЕРУ!!!")
               {
                   dialog,_ -> dialog.dismiss()
               }
               .setNegativeButton("КОНЕЧНО,ДА!")
               {
                   dialog,_ -> dialog.dismiss()
               }
               .create()
               .show()
        }

        sharedPrefs = getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)
        gson = Gson()

        setupAdapter()
        setupSpinner()
        setupObservers()
    }

    private fun getFavorites(): List<Hero> {
        val json = sharedPrefs.getString("favorites_list", "[]") ?: "[]"
        val type = object : TypeToken<List<Hero>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    private fun setupAdapter() {
        adapter = Adapter { hero ->
            startActivity(HeroDetailsActivity.newIntent(this, hero))
        }
        binding.heroesRecyclerView.adapter = adapter
    }

    private fun setupSpinner() {
        viewModel.publishers.observe(this) { publishers ->
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                publishers
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

            binding.publisherSpinner.adapter = adapter

            binding.publisherSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedPublisher = parent?.getItemAtPosition(position).toString()
                        if (selectedPublisher.isNotEmpty()) {
                            viewModel.searchByPublisher(selectedPublisher)
                            binding.heroesRecyclerView.smoothScrollToPosition(0)
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    private fun setupObservers() {
        viewModel.searchState.observe(this) { state ->
            when (state) {
                is State.Loading -> showLoading()
                is State.Content -> {
                    val favorites = getFavorites()
                    val sortedHeroes = state.heroes.sortedByDescending { hero ->
                        favorites.any { it.id == hero.id }
                    }.map { hero ->
                        hero.copy(isFavorite = favorites.any { it.id == hero.id })
                    }
                    showContent(sortedHeroes)
                }

                is State.Error -> showError(state.errorMessage)
                is State.Empty -> showEmpty(state.message)
            }
        }

        viewModel.toastState.observe(this) { message ->
            message?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }


        viewModel.toastState.observe(this)
        { message ->
            message?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }

        viewModel.navigateToHero.observe(this)
        { hero ->
            startActivity(
                HeroDetailsActivity.newIntent(this, hero)
            )
        }
    }


    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.placeholderImage.visibility = View.GONE
        binding.placeholderMessage.visibility = View.GONE
    }

    private fun showContent(heroes: List<Hero>) {
        binding.progressBar.visibility = View.GONE
        binding.placeholderImage.visibility = View.GONE
        binding.placeholderMessage.visibility = View.GONE
        adapter.heroes.clear()
        adapter.heroes.addAll(heroes)
        adapter.notifyDataSetChanged()
    }

    private fun showError(errorMessage: String) {
        binding.progressBar.visibility = View.GONE
        binding.placeholderImage.setImageResource(R.drawable.vecteezy_no_internet_network_icon_vector_design_15938171)
        binding.placeholderImage.visibility = View.VISIBLE
        binding.placeholderMessage.visibility = View.VISIBLE
        binding.placeholderMessage.text = errorMessage
        adapter.heroes.clear()
        adapter.heroes.addAll(emptyList())
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty(emptyMessage: String) {
        binding.progressBar.visibility = View.GONE
        binding.placeholderImage.setImageResource(R.drawable.error)
        binding.placeholderImage.visibility = View.VISIBLE
        binding.placeholderMessage.visibility = View.VISIBLE
        binding.placeholderMessage.text = emptyMessage
        adapter.heroes.clear()
        adapter.heroes.addAll(emptyList())
        adapter.notifyDataSetChanged()
    }
}