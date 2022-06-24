package ru.profitsw2000.historyscreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.profitsw2000.model.entities.DataModel
import ru.profitsw2000.historyscreen.databinding.HistoryRecyclerViewItemViewBinding

class HistoryAdapter (private var data: List<DataModel>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private lateinit var binding: HistoryRecyclerViewItemViewBinding

    fun setData (data: List<DataModel>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = HistoryRecyclerViewItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(dataModel: DataModel) {
            with(binding) {
                historyRecyclerViewSavedWordTextView.text = dataModel.text
                historyRecyclerViewTranslationTextView.text = dataModel.meanings?.get(0)?.translation?.text
                if (!dataModel.meanings?.get(0)?.translation?.note.isNullOrEmpty()) {
                    historyRecyclerViewNoteTextView.text = dataModel.meanings?.get(0)?.translation?.note
                }
            }
        }
    }
}