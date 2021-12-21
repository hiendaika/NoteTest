package com.example.supernotes.view.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.supernotes.base.BaseFragment
import com.example.supernotes.databinding.FragmentAddNoteBinding
import com.example.supernotes.view.viewmodel.AddNoteViewModel

class AddNoteFragment: BaseFragment<FragmentAddNoteBinding, AddNoteViewModel>() {
    override val viewModel: AddNoteViewModel by activityViewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddNoteBinding {
        return FragmentAddNoteBinding.inflate(inflater,container,false)
    }

}