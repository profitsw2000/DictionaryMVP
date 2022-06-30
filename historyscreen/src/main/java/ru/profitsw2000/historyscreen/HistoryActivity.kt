package ru.profitsw2000.historyscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.profitsw2000.historyscreen.adapter.HistoryAdapter
import ru.profitsw2000.historyscreen.databinding.ActivityHistoryBinding
import ru.profitsw2000.model.AppState

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val historyViewModel: HistoryViewModel by viewModel()
    private var adapter: HistoryAdapter? = null

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
                with(binding) {
                    historyRecyclerView.visibility = android.view.View.VISIBLE
                    progressLinearLayout.visibility = android.view.View.GONE
                    errorMessage.visibility = android.view.View.GONE
                }
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    with(binding) {
                        historyRecyclerView.visibility = android.view.View.GONE
                        errorMessage.visibility = android.view.View.VISIBLE
                        progressLinearLayout.visibility = android.view.View.GONE
                        errorMessage.setTextColor(resources.getColor(R.color.blue))
                        errorMessage.text = getString(R.string.empty_history_message_text)
                    }
                } else {
                    if(adapter == null){
                        binding.historyRecyclerView.adapter = HistoryAdapter(dataModel)
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
                with(binding) {
                    historyRecyclerView.visibility = android.view.View.GONE
                    errorMessage.visibility = android.view.View.GONE
                    progressLinearLayout.visibility = android.view.View.VISIBLE
                }
            }
            is AppState.Error -> {
                with(binding) {
                    historyRecyclerView.visibility = android.view.View.GONE
                    errorMessage.visibility = android.view.View.VISIBLE
                    progressLinearLayout.visibility = android.view.View.GONE
                    errorMessage.setTextColor(resources.getColor(R.color.red))
                    errorMessage.text = appState.error.message
                }
            }
        }
    }
}