package ru.profitsw2000.dictionarymvp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
=======
import androidx.activity.viewModels
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import ru.profitsw2000.dictionarymvp.R
import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.databinding.ActivityMainBinding
import ru.profitsw2000.dictionarymvp.ui.main.adapter.TranslationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {


    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
=======
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding
=======
import android.os.Bundle
import android.widget.Toast
import ru.profitsw2000.dictionarymvp.R
import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.data.entities.Meanings
import ru.profitsw2000.dictionarymvp.data.entities.Translation
import ru.profitsw2000.dictionarymvp.databinding.ActivityMainBinding
import ru.profitsw2000.dictionarymvp.ui.base.View
import ru.profitsw2000.dictionarymvp.ui.main.adapter.TranslationAdapter

class MainActivity : AppCompatActivity(), View {

    private lateinit var binding: ActivityMainBinding
    private var presenter: MainPresenter? = null
    private var adapter: TranslationAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.subscribe().observe(this@MainActivity) { renderData(it) }

        binding.searchWordTranslationInputLayout.setEndIconOnClickListener {
            val word = binding.searchWordTranslationEditText.text.toString()
            viewModel.getData(word, true)
=======
        AndroidInjection.inject(this)
        viewModel = viewModelFactory.create(MainViewModel::class.java)

        binding.searchWordTranslationInputLayout.setEndIconOnClickListener {
            val word = binding.searchWordTranslationEditText.text.toString()
            viewModel.getData(word, true).observe(this@MainActivity) { renderData(it) }
        }
    }

    private fun renderData(appState: AppState) {
=======
=======
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
                        binding.translationRecyclerView.adapter = dataModel[0].meanings?.let {TranslationAdapter(it)}
                    } else {
                        dataModel[0].meanings?.let {
                            with(adapter) {
                                this?.setData(it)
                            }
                        }
=======
                        dataModel[0].meanings?.let { adapter!!.setData(it) }
=======
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
=======
=======

    private fun restorePresenter(): MainPresenter {
        val presenter = lastCustomNonConfigurationInstance as? MainPresenter
        return presenter ?: MainPresenter()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }
}