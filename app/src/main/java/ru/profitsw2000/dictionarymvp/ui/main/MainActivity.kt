package ru.profitsw2000.dictionarymvp.ui.main

import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RestrictTo
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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
import ru.profitsw2000.utils.ui.OnlineLiveData
import ru.profitsw2000.utils.ui.viewById

class MainActivity : AppCompatActivity(), AndroidScopeComponent {

    override val scope: Scope by activityScope()
    private val viewModel: MainViewModel by inject()
    private lateinit var binding: ActivityMainBinding
    private var adapter: TranslationAdapter? = null
    protected var isNetworkAvailable: Boolean = true
    //Views by delegate
    private val errorMessageTextView by viewById<TextView>(R.id.error_message)
    private val mainProgressBar by viewById<ProgressBar>(R.id.progress_bar)
    private val mainActivityRecyclerview by viewById<RecyclerView>(R.id.translation_recycler_view)
    private val searchWordInputLayout by viewById<TextInputLayout>(R.id.search_word_translation_input_layout)
    private val searchWordEditText by viewById<TextInputEditText>(R.id.search_word_translation_edit_text)
    private val mainActivityFrameLayout by viewById<FrameLayout>(R.id.main_activity_frame_layout)
    //SnackBar
    private var snackbar: Snackbar? = null

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
        Thread.sleep(500)
        val splashScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        snackbar = Snackbar.make(mainActivityFrameLayout, getString(R.string.internet_offline_message_text),Snackbar.LENGTH_INDEFINITE)

        subscribeToNetworkChange()
        viewModel.subscribe().observe(this@MainActivity) { renderData(it) }

        searchWordInputLayout.setEndIconOnClickListener {
            startTranslationSearch()
        }
    }

    override fun onResume() {
        super.onResume()
        startTranslationSearch()
    }

    private fun startTranslationSearch() {
        val word = searchWordEditText.text.toString()
        if (!word.isNullOrEmpty()) viewModel.getData(word, true)
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setRenderEffect(15f,15f)
        }

        val searchWordInHistoryDialog = SearchWordInHistoryDialog.newInstance()
        searchWordInHistoryDialog.setOnSearchClickListener(onSearchClickListener)
        searchWordInHistoryDialog.show(supportFragmentManager, "NewDialog")
    }

    private val onSearchClickListener: SearchWordInHistoryDialog.OnSearchClickListener =
        object : SearchWordInHistoryDialog.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                viewModel.getHistoryDataByWord(searchWord)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    setRenderEffect(1f,1f)
                }
            }
        }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                mainActivityRecyclerview.visibility = View.VISIBLE
                mainProgressBar.visibility = View.GONE
                errorMessageTextView.visibility = View.GONE

                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    mainActivityRecyclerview.visibility = View.GONE
                    mainProgressBar.visibility = View.GONE
                    errorMessageTextView.visibility = View.VISIBLE
                    errorMessageTextView.setTextColor(resources.getColor(R.color.blue))
                    errorMessageTextView.setText(getString(R.string.no_translation_found_message_text))
                } else {
                    if(adapter == null){
                        mainActivityRecyclerview.adapter = dataModel[0].meanings?.let {TranslationAdapter(it, onItemClickListener)}
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
                mainActivityRecyclerview.visibility = View.GONE
                errorMessageTextView.visibility = View.GONE
                mainProgressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                mainActivityRecyclerview.visibility = View.GONE
                errorMessageTextView.visibility = View.VISIBLE
                mainProgressBar.visibility = View.GONE
                errorMessageTextView.setTextColor(resources.getColor(R.color.red))
                errorMessageTextView.setText(appState.error.message)
            }
        }
    }

    private fun subscribeToNetworkChange() {
        OnlineLiveData(this).observe(
            this@MainActivity,
            checkNetwork()
        )
    }

    private fun checkNetwork(): (t: Boolean) -> Unit = {
        isNetworkAvailable = it
        if (!isNetworkAvailable) {
            snackbar?.show()
        } else {
            snackbar?.dismiss()
        }
    }

    private fun setRenderEffect(radiusX: Float, radiusY: Float) {
        var blurEffect = RenderEffect.createBlurEffect(radiusX, radiusY, Shader.TileMode.MIRROR)
        binding.mainActivityFrameLayout.setRenderEffect(blurEffect)
    }

}