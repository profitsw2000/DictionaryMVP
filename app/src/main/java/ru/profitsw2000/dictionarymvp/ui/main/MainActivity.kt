package ru.profitsw2000.dictionarymvp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.profitsw2000.dictionarymvp.R
import ru.profitsw2000.dictionarymvp.data.AppState
import ru.profitsw2000.dictionarymvp.databinding.ActivityMainBinding
import ru.profitsw2000.dictionarymvp.ui.main.adapter.TranslationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.profitsw2000.dictionarymvp.data.entities.Meanings
import ru.profitsw2000.dictionarymvp.ui.description.DescriptionActivity
import ru.profitsw2000.dictionarymvp.ui.history.HistoryActivity
import ru.profitsw2000.dictionarymvp.ui.history.dialog.SearchWordInHistoryDialog

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private var adapter: TranslationAdapter? = null

    private val onItemClickListener: TranslationAdapter.OnItemClickListener =
        object : TranslationAdapter.OnItemClickListener {
            override fun onItemClick(meanings: Meanings) {
                val text = if (meanings.translation?.text.isNullOrEmpty()) "" else meanings.translation?.text
                val note = if (meanings.translation?.note.isNullOrEmpty()) "" else meanings.translation?.note
                val url = if (meanings.imageUrl.isNullOrEmpty()) "" else meanings.imageUrl

                startActivity(text?.let {
                    note?.let { it1 ->
                        DescriptionActivity.getIntent(this@MainActivity, it, it1, url)
                    }
                })
            }
        }
=======

class MainActivity : AppCompatActivity() {


    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            R.id.menu_search_word_in_history -> {
                startSearchWordInHistoryDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startSearchWordInHistoryDialog() {
        val searchWordInHistoryDialog = SearchWordInHistoryDialog.newInstance()
        searchWordInHistoryDialog.setOnSearchClickListener(onSearchClickListener)
        searchWordInHistoryDialog.show(supportFragmentManager, "NewDialog")
    }

    private val onSearchClickListener: SearchWordInHistoryDialog.OnSearchClickListener =
        object : SearchWordInHistoryDialog.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                viewModel.getHistoryDataByWord(searchWord)
                //Toast.makeText(this@MainActivity, searchWord, Toast.LENGTH_SHORT).show()
            }
        }

    private fun renderData(appState: AppState) {
        AndroidInjection.inject(this)
        viewModel = viewModelFactory.create(MainViewModel::class.java)

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
                        dataModel[0].meanings?.let { adapter!!.setData(it) }
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