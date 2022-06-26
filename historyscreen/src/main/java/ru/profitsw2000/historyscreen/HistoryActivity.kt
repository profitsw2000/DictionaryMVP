package ru.profitsw2000.historyscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import ru.profitsw2000.historyscreen.adapter.HistoryAdapter
import ru.profitsw2000.historyscreen.databinding.ActivityHistoryBinding
import ru.profitsw2000.model.AppState
import ru.profitsw2000.utils.ui.viewById

class HistoryActivity : AppCompatActivity(), AndroidScopeComponent {

    override val scope: Scope by activityScope()
    private lateinit var binding: ActivityHistoryBinding
    private val historyViewModel: HistoryViewModel by inject()
    private var adapter: HistoryAdapter? = null
    //Views by delegate
    private val historyActivityRecyclerView by viewById<RecyclerView>(R.id.history_recycler_view)
    private val progressBarLinearLayout by viewById<LinearLayout>(R.id.progress_linear_layout)
    private val errorMessageTextView by viewById<TextView>(R.id.error_message)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        historyViewModel.subscribe().observe(this@HistoryActivity) { renderData(it) }
    }

    override fun onResume() {
        super.onResume()
        historyViewModel.getData("",false)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                historyActivityRecyclerView.visibility = android.view.View.VISIBLE
                progressBarLinearLayout.visibility = android.view.View.GONE
                errorMessageTextView.visibility = android.view.View.GONE

                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    historyActivityRecyclerView.visibility = android.view.View.GONE
                    errorMessageTextView.visibility = android.view.View.VISIBLE
                    progressBarLinearLayout.visibility = android.view.View.GONE
                    errorMessageTextView.setTextColor(resources.getColor(R.color.blue))
                    errorMessageTextView.text = getString(R.string.empty_history_message_text)
                } else {
                    if(adapter == null){
                        historyActivityRecyclerView.adapter = HistoryAdapter(dataModel)
                    } else {
                        dataModel.let {
                            with(adapter) {
                                this?.setData(it)
                            }
                        }
                    }
                }
            }
            is AppState.Loading -> {
                historyActivityRecyclerView.visibility = android.view.View.GONE
                errorMessageTextView.visibility = android.view.View.GONE
                progressBarLinearLayout.visibility = android.view.View.VISIBLE
            }
            is AppState.Error -> {
                historyActivityRecyclerView.visibility = android.view.View.GONE
                errorMessageTextView.visibility = android.view.View.VISIBLE
                progressBarLinearLayout.visibility = android.view.View.GONE
                errorMessageTextView.setTextColor(resources.getColor(R.color.red))
                errorMessageTextView.text = appState.error.message
            }
        }
    }
}