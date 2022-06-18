package ru.profitsw2000.dictionarymvp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.profitsw2000.dictionarymvp.R
import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.databinding.ActivityMainBinding
import ru.profitsw2000.dictionarymvp.ui.main.adapter.TranslationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.profitsw2000.dictionarymvp.data.entities.Meanings

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private var adapter: TranslationAdapter? = null

    private val onItemClickListener: TranslationAdapter.OnItemClickListener =
        object : TranslationAdapter.OnItemClickListener {
            override fun onItemClick(meanings: Meanings) {
                Toast.makeText(this@MainActivity, meanings.translation?.text, Toast.LENGTH_SHORT).show()
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.subscribe().observe(this@MainActivity) { renderData(it) }

        binding.searchWordTranslationInputLayout.setEndIconOnClickListener {
            val word = binding.searchWordTranslationEditText.text.toString()
            viewModel.getData(word, true)
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
                        errorMessage.setText(getString(R.string.no_translation_found_message_text))
                    }
                } else {
                    if(adapter == null){
                        binding.translationRecyclerView.adapter = dataModel[0].meanings?.let {TranslationAdapter(it, onItemClickListener)}
                    } else {
                        dataModel[0].meanings?.let {
                            with(adapter) {
                                this?.setData(it)
                            }
                        }
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