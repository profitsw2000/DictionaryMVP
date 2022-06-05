package ru.profitsw2000.dictionarymvp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.profitsw2000.dictionarymvp.R
import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.databinding.ActivityMainBinding
import ru.profitsw2000.dictionarymvp.ui.base.View

class MainActivity : AppCompatActivity(), View {

    private lateinit var binding: ActivityMainBinding
    private var presenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = restorePresenter()
        presenter?.onAttach(this)

        binding.searchWordTranslationInputLayout.setEndIconOnClickListener {
            val word = binding.searchWordTranslationEditText.text.toString()

            presenter?.getData(word, true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDetach(this)
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    Toast.makeText(this, "Пусто!", Toast.LENGTH_SHORT).show()
                } else {
                    val translatedWord = dataModel[0].meanings?.get(0)?.translation?.text
                    Toast.makeText(this, translatedWord, Toast.LENGTH_SHORT).show()
                }
            }
            is AppState.Loading -> {
                Toast.makeText(this, "Loading!", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun restorePresenter(): MainPresenter {
        val presenter = lastCustomNonConfigurationInstance as? MainPresenter
        return presenter ?: MainPresenter()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }
}