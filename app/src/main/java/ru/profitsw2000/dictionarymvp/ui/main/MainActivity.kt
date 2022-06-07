package ru.profitsw2000.dictionarymvp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ru.profitsw2000.dictionarymvp.R
import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.data.entities.Meanings
import ru.profitsw2000.dictionarymvp.data.entities.Translation
import ru.profitsw2000.dictionarymvp.databinding.ActivityMainBinding
import ru.profitsw2000.dictionarymvp.ui.base.View
import ru.profitsw2000.dictionarymvp.ui.main.adapter.TranslationAdapter

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy { ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java) }
    private lateinit var binding: ActivityMainBinding
    private var adapter: TranslationAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchWordTranslationInputLayout.setEndIconOnClickListener {
            val word = binding.searchWordTranslationEditText.text.toString()
            viewModel.getData(word, true).observe(this@MainActivity) { renderData(it) }
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                with(binding) {
                    translationRecyclerView.visibility = android.view.View.VISIBLE
                    progressBar.visibility = android.view.View.GONE
                    errorMessage.visibility = android.view.View.GONE
                }
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    with(binding) {
                        translationRecyclerView.visibility = android.view.View.GONE
                        errorMessage.visibility = android.view.View.VISIBLE
                        progressBar.visibility = android.view.View.GONE
                        errorMessage.setTextColor(resources.getColor(R.color.blue))
                        errorMessage.setText("Перевод введенного слова не найден")
                    }
                } else {
                    if(adapter == null){
                        binding.translationRecyclerView.adapter = TranslationAdapter(dataModel[0].meanings!!)
                    } else {
                        adapter!!.setData(dataModel[0].meanings!!)
                    }
                }
            }
            is AppState.Loading -> {
                with(binding) {
                    translationRecyclerView.visibility = android.view.View.GONE
                    errorMessage.visibility = android.view.View.GONE
                    progressBar.visibility = android.view.View.VISIBLE
                }
            }
            is AppState.Error -> {
                with(binding) {
                    translationRecyclerView.visibility = android.view.View.GONE
                    errorMessage.visibility = android.view.View.VISIBLE
                    progressBar.visibility = android.view.View.GONE
                    errorMessage.setTextColor(resources.getColor(R.color.red))
                    errorMessage.setText(appState.error.message)
                }
            }
        }
    }
}