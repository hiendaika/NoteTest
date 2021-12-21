package com.example.supernotes.view.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.supernotes.R
import com.example.supernotes.base.BaseFragment
import com.example.supernotes.databinding.FragmentDashboardBinding
import com.example.supernotes.extensions.toast
import com.example.supernotes.view.viewmodel.DashBoardViewModel


class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashBoardViewModel>() {
    override val viewModel: DashBoardViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initViews()
    }

    private fun setupRecyclerView() {

    }

    private fun initViews() {
        with(binding) {
            fabAdd.setOnClickListener {
                findNavController().navigate(R.id.action_dashboard_to_add_note)
            }

            btnAdd.setOnClickListener {
                activity?.toast("Click add")
            }

            btnEdit.setOnClickListener {
                activity?.toast("Edit")
            }

            btnRecord.setOnClickListener {
                activity?.toast("Voice")
            }

            btnTakePhoto.setOnClickListener {
                activity?.toast("Take p")
            }
        }
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }
}