package com.example.supernotes.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.supernotes.base.BaseFragment
import com.example.supernotes.databinding.FragmentDetailNoteBinding
import com.example.supernotes.view.viewmodel.NoteViewModel

class DetailNoteFragment: BaseFragment<FragmentDetailNoteBinding, NoteViewModel>() {
    override val viewModel: NoteViewModel by activityViewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailNoteBinding {
        return FragmentDetailNoteBinding.inflate(inflater,container,false)
    }
}