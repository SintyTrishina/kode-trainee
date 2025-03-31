package com.example.kode_trainee.feature_heroList.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kode_trainee.R
import com.example.kode_trainee.databinding.ActivityHeroListBinding
import com.example.kode_trainee.feature_heroList.domain.models.Hero
import com.example.kode_trainee.feature_heroList.presentation.viewmodel.HeroListViewModel
import com.example.kode_trainee.feature_heroList.presentation.viewmodel.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeroListActivity : AppCompatActivity() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private val viewModel by viewModel<HeroListViewModel>()


    private lateinit var binding: ActivityHeroListBinding

    private val adapter = Adapter {
        if (clickDebounce()) {
            viewModel.onTrackClicked(it)
        }
    }

    private val handler = Handler(Looper.getMainLooper())
    private var isClickAllowed = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.heroesRecyclerView.adapter = adapter


        viewModel.searchState.observe(this) {
            render(it)
        }

        viewModel.toastState.observe(this) {
            it?.let { showToast(it) }
        }
        viewModel.navigateToHero.observe(this) {
            val intent = Intent(this, HeroDetailsActivity::class.java).apply {
//                putExtra("APPEARANCE", it.appearance)
                putExtra("NAME", it.name)
                putExtra("ID", it.id)
//                putExtra("BIOGRAPHY", it.biography)
//                putExtra("CONNECTIONS", it.connections)
//                putExtra("IMAGE", it.image)
                putExtra("RESPONSE", it.response)
//                putExtra("WORK", it.work)
            }
            startActivity(intent)
        }
    }



    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun render(state: State) {
        when (state) {
            is State.Loading -> showLoading()
            is State.Content -> showContent(state.heroes)
            is State.Error -> showError(state.errorMessage)
            is State.Empty -> showEmpty(state.message)
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.progressBar.visibility = View.GONE
        binding.placeholderImage.setImageResource(R.drawable.errorconnection)
        binding.placeholderImage.visibility = View.VISIBLE
        binding.placeholderMessage.visibility = View.VISIBLE
        binding.placeholderMessage.text = errorMessage

    }

    private fun showEmpty(emptyMessage: String) {
        binding.progressBar.visibility = View.GONE
        binding.placeholderImage.setImageResource(R.drawable.error)
        binding.placeholderImage.visibility = View.VISIBLE
        binding.placeholderMessage.visibility = View.VISIBLE
        binding.placeholderMessage.text = emptyMessage
    }

    private fun showContent(heroes: List<Hero>) {
        binding.progressBar.visibility = View.GONE
        binding.placeholderImage.visibility = View.GONE
        binding.placeholderMessage.visibility = View.GONE
        adapter.heroes.clear()
        adapter.heroes.addAll(heroes)
        adapter.notifyDataSetChanged()

    }
}
