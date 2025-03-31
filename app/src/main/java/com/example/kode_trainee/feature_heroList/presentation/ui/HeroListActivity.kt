package com.example.kode_trainee.feature_heroList.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kode_trainee.R
import com.example.kode_trainee.databinding.ActivityHeroListBinding
import com.example.kode_trainee.feature_heroList.domain.models.Hero
import com.example.kode_trainee.feature_heroList.presentation.viewmodel.HeroListViewModel
import com.example.kode_trainee.feature_heroList.presentation.viewmodel.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeroListActivity : AppCompatActivity() {

    private val viewModel by viewModel<HeroListViewModel>()
    private lateinit var binding: ActivityHeroListBinding
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

        setupAdapter()
        setupSpinner()
        setupObservers()

    }

    private fun setupAdapter() {
        adapter = Adapter { hero ->
            viewModel.onHeroClicked(hero)
        }
        binding.heroesRecyclerView.adapter = adapter
    }

    private fun setupSpinner() {
        viewModel.publishers.observe(this) { publishers ->
            val adapter = ArrayAdapter(
                this,
                org.koin.android.R.layout.support_simple_spinner_dropdown_item,
                publishers
            ).apply {
                setDropDownViewResource(org.koin.android.R.layout.support_simple_spinner_dropdown_item)
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
                is State.Content -> showContent(state.heroes)
                is State.Error -> showError(state.errorMessage)
                is State.Empty -> showEmpty(state.message)
            }
        }

        viewModel.toastState.observe(this) { message ->
            message?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }

        viewModel.navigateToHero.observe(this) { hero ->
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
        binding.placeholderImage.setImageResource(R.drawable.errorconnection)
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