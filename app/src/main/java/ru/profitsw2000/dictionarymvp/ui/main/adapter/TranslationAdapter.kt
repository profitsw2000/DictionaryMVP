package ru.profitsw2000.dictionarymvp.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.profitsw2000.dictionarymvp.data.entities.Meanings
import ru.profitsw2000.dictionarymvp.databinding.MainRecyclerViewItemViewBinding

class TranslationAdapter (private var data: List<Meanings>) : RecyclerView.Adapter<TranslationAdapter.ViewHolder>() {

    private lateinit var binding: MainRecyclerViewItemViewBinding

    fun setData (data: List<Meanings>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslationAdapter.ViewHolder {
        binding = MainRecyclerViewItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun bind(meanings: Meanings) {
            with(binding) {
                mainRecyclerViewTranslationTextView.text = meanings.translation?.text
                if (!meanings.translation?.note.isNullOrEmpty()) {
                    mainRecyclerViewNoteTextView.text = meanings.translation?.note
                }
            }
        }
    }
}