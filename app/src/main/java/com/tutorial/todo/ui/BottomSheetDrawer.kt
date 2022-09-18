package com.tutorial.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tutorial.todo.Interface.Sort
import com.tutorial.todo.databinding.BottomSheetViewBinding
import com.tutorial.todo.model.arch.ActivityViewModel
import com.tutorial.todo.model.epoxycontroller.BottomEpoxyController

class BottomSheetDrawer : BottomSheetDialogFragment() {

    private val viewModel: ActivityViewModel by activityViewModels()

    private var _binding: BottomSheetViewBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "BottomSheetDrawer"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = BottomSheetViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = BottomEpoxyController {
            viewModel.mainSort.postValue(it)
            viewModel.getCatId(it)
            dismiss()
        }

        viewModel.selectedSort.observe(viewLifecycleOwner) {
            viewModel.setSortedData(it)
        }

        controller.sortList = Sort.values()

        viewModel.mainSort.observe(viewLifecycleOwner) {
            controller.selectedSort = it
        }
        binding.recyclerView.setControllerAndBuildModels(controller)


        controller.requestModelBuild()


    }

}
