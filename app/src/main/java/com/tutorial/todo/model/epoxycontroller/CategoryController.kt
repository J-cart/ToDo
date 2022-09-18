package com.tutorial.todo.model.epoxycontroller

import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.tutorial.todo.R
import com.tutorial.todo.databinding.AddCategoryBinding
import com.tutorial.todo.databinding.LoadingBinding
import com.tutorial.todo.model.arch.ActivityViewModel
import com.tutorial.todo.utils.ViewBindingKotlinModel

class CategoryController(private val onClicked: (String) -> Unit) : EpoxyController() {

    var categoryData = ActivityViewModel.AddICViewState()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {

        if(categoryData.isLoading){
            LoadingEpoxyModel2().id("Loading").addTo(this)
            return
        }
        categoryData.allItemsViewState.forEach {
            CategoryViewController(it,onClicked).id(it.categoryAppData.id).addTo(this)
        }

    }

    data class CategoryViewController(
        val categoryName: ActivityViewModel.AddICViewState.AllItemsViewState,
        val onClicked: (String) -> Unit
    ) :
        ViewBindingKotlinModel<AddCategoryBinding>(R.layout.add_category) {
        override fun AddCategoryBinding.bind() {
            categoryText.text = categoryName.categoryAppData.name
            root.setOnClickListener {
                onClicked(categoryName.categoryAppData.id)
            }
            val colorRes = if(categoryName.isSelected)ContextCompat.getColor(root.context,R.color.red ) else ContextCompat.getColor(root.context,R.color.black)
            categoryText.setTextColor(colorRes)
        }
    }

    class LoadingEpoxyModel2 : ViewBindingKotlinModel<LoadingBinding>(R.layout.loading) {
        override fun LoadingBinding.bind() {

        }
    }
}