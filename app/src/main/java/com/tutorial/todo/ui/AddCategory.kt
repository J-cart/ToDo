package com.tutorial.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tutorial.todo.database.CategoryAppData
import com.tutorial.todo.databinding.FragmentAddCategoryBinding
import java.util.*


class AddCategory : BaseFragment() {

    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveCategoryBtn.setOnClickListener {
            val name = binding.categoryEditField.text.toString().trim()
            if (name.isEmpty()){
                binding.categoryTextField.error = "* Require Field"
                return@setOnClickListener
            }
            val category =  CategoryAppData(id = UUID.randomUUID().toString(), name)
            activityViewModel.insert(category)
        }

        activityViewModel.processComplete.observe(viewLifecycleOwner){
            it.getContentIfNotHandledOrNull()?.let {
                findNavController().navigateUp()
            }
        }

    }

}