package com.tutorial.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tutorial.todo.R
import com.tutorial.todo.database.AppData
import com.tutorial.todo.database.CategoryAppData
import com.tutorial.todo.databinding.FragmentAddItemBinding
import com.tutorial.todo.model.epoxycontroller.CategoryController
import java.util.*

class AddItem : BaseFragment() {
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!
    private val safeArgs: AddItemArgs by navArgs()
    private val selectedItem: AppData? by lazy {
        activityViewModel.itemsWithCategory.value?.find {
            it.AppData.id == safeArgs.listItem
        }?.AppData
    }
    private var isEditable = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.TitleTextField.requestFocus()
        mainActivity.showKeyboard()


        binding.saveBtn.setOnClickListener {
            addToDao()
        }

        activityViewModel.processComplete.observe(viewLifecycleOwner) { completed ->
            completed.getContentIfNotHandledOrNull()?.let {
                Toast.makeText(requireActivity(), "Item saved", Toast.LENGTH_SHORT).show()
                binding.descEditText.text = null
                binding.TitleTextField.requestFocus()
                findNavController().navigateUp()
            }
        }


        selectedItem?.let { appdata ->
            isEditable = true
            binding.titleEditText.setText(appdata.title)
            binding.descEditText.setText(appdata.description)
            val priority = when (appdata.priority) {
                1 -> R.id.lowPriority
                2 -> R.id.mediumPriority
                3 -> R.id.highPriority
                else -> R.id.lowPriority
            }
            binding.radioGroup.check(priority)
            binding.saveBtn.text = "Update"
            mainActivity.supportActionBar?.title = "Update"
        }

        activityViewModel.onSelectedCategory(
            selectedItem?.category ?: CategoryAppData.DEFAULT_CATEGORY_ID, true
        )
        val controller = CategoryController {
            activityViewModel.onSelectedCategory(it)
        }
        binding.recyclerView.setController(controller)
        activityViewModel.addICViewState.observe(viewLifecycleOwner) { viewState ->
            controller.categoryData = viewState
        }

    }

    /* override fun onPause() {
         super.onPause()
         activityViewModel.processComplete.postValue(false)
        activityViewModel.selectedCategory("")
     }*/


    private fun addToDao() {
        val id = UUID.randomUUID().toString()
        val title = binding.titleEditText.text.toString().trim()

        if (title.isEmpty()) {
            binding.TitleTextField.error = "* Require field"
            return
        }
        binding.TitleTextField.error = null

        val desc: String? = binding.descEditText.text.toString().trim()
        val priority = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.lowPriority -> 1
            R.id.mediumPriority -> 2
            R.id.highPriority -> 3
            else -> 0
        }
        val time = System.currentTimeMillis()
//        val category = activityViewModel.itemsCategory.value?.name ?: ""
        val category = activityViewModel.addICViewState.value?.getSelectedCategory() ?: return


        val data = AppData(
            id = id,
            title = title,
            description = desc,
            priority = priority,
            createdAt = time,
            category = category
        )

        if (isEditable) {

            val editedData = selectedItem!!.copy(
                title = title,
                description = desc,
                priority = priority,
                category = category
            )

            activityViewModel.update(editedData)
            return
        }
        activityViewModel.insert(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}