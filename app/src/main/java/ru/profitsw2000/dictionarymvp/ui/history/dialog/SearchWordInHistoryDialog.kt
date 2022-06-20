package ru.profitsw2000.dictionarymvp.ui.history.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.profitsw2000.dictionarymvp.databinding.SearchWordInHistoryDialogBinding

class SearchWordInHistoryDialog(): DialogFragment() {

    private lateinit var binding: SearchWordInHistoryDialogBinding
    private var onSearchClickListener: OnSearchClickListener? = null

/*    private val onSearchButtonClickListener =
        View.OnClickListener {
            onSearchClickListener?.onClick(binding.searchWordInHistoryEditText.text.toString())
            dismiss()
        }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SearchWordInHistoryDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchWordInHistoryInputLayout.setEndIconOnClickListener {
            onSearchClickListener?.onClick(binding.searchWordInHistoryEditText.text.toString())
            dismiss()
        }
/*        searchEditText = search_edit_text
        clearTextImageView = clear_text_imageview
        searchButton = search_button_textview

        searchButton.setOnClickListener(onSearchButtonClickListener)
        searchEditText.addTextChangedListener(textWatcher)
        addOnClearClickListener()*/
    }

    override fun onDestroyView() {
        onSearchClickListener = null
        super.onDestroyView()
    }

    internal fun setOnSearchClickListener(listener: OnSearchClickListener) {
        onSearchClickListener = listener
    }

    interface OnSearchClickListener {
        fun onClick(searchWord: String)
    }

    companion object {
        fun newInstance(): SearchWordInHistoryDialog {
            return SearchWordInHistoryDialog()
        }
    }
}