package ru.profitsw2000.dictionarymvp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RestrictTo
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.scope.activityScope
import org.koin.androidx.scope.currentScope
import org.koin.core.scope.Scope
import ru.profitsw2000.dictionarymvp.R
import ru.profitsw2000.dictionarymvp.databinding.ActivityMainBinding
import ru.profitsw2000.dictionarymvp.ui.main.adapter.TranslationAdapter
import ru.profitsw2000.model.entities.Meanings
import ru.profitsw2000.dictionarymvp.ui.description.DescriptionActivity
import ru.profitsw2000.historyscreen.HistoryActivity
import ru.profitsw2000.dictionarymvp.ui.main.dialog.SearchWordInHistoryDialog
import ru.profitsw2000.model.AppState

class MainActivity : AppCompatActivity(), AndroidScopeComponent {

    override val scope: Scope by activityScope()
    private val viewModel: MainViewModel by inject()
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
            }
        }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                with(binding) {
                    translationRecyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                }
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    with(binding) {
                        translationRecyclerView.visibility = View.GONE
                        errorMessage.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
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
                    translationRecyclerView.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                with(binding) {
                    translationRecyclerView.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    errorMessage.setTextColor(resources.getColor(R.color.red))
                    errorMessage.setText(appState.error.message)
                }
            }
        }
    }

}