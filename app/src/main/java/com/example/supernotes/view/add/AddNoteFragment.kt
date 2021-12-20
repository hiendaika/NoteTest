package com.example.supernotes.view.add

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.supernotes.base.BaseFragment
import com.example.supernotes.databinding.FragmentAddNoteBinding
import com.example.supernotes.view.viewmodel.AddNoteViewModel

class AddNoteFragment: BaseFragment<FragmentAddNoteBinding, AddNoteViewModel>() {
    override val viewModel: AddNoteViewModel
        get() = TODO("Not yet implemented")

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddNoteBinding {
        TODO("Not yet implemented")
    }
}