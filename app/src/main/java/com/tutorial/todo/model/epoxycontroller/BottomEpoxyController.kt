package com.tutorial.todo.model.epoxycontroller

import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyController
import com.tutorial.todo.Interface.Sort
import com.tutorial.todo.R
import com.tutorial.todo.databinding.BottomSheetViewHolderBinding
import com.tutorial.todo.utils.ViewBindingKotlinModel

class BottomEpoxyController(
    private val onclicked: (Sort) -> Unit
) : EpoxyController() {

    var selectedSort: Sort = Sort.NONE
    var sortList: Array<Sort> = emptyArray()

    override fun buildModels() {

        sortList.forEach {
            val isSelected = it.name == selectedSort.name
            ItemEpoxyController(isSelected, it, onclicked).id(selectedSort.name).addTo(this)
        }


    }

    data class ItemEpoxyController(
        var isSelected: Boolean,
        val sort: Sort,
        val onclicked: (Sort) -> Unit
    ) : ViewBindingKotlinModel<BottomSheetViewHolderBinding>(R.layout.bottom_sheet_view_holder) {
        override fun BottomSheetViewHolderBinding.bind() {
            categoryText.text = sort.displayName

            root.setOnClickListener {
                onclicked(sort)
            }
            checkbox.isChecked = isSelected
            val colorRes = if (isSelected) ContextCompat.getColor(
                root.context,
                R.color.red
            ) else ContextCompat.getColor(root.context, R.color.black)
            categoryText.setTextColor(colorRes)

        }
    }


}
