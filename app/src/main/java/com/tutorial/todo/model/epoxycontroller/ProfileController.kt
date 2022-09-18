package com.tutorial.todo.model.epoxycontroller

import com.airbnb.epoxy.EpoxyController
import com.tutorial.todo.R
import com.tutorial.todo.database.CategoryAppData
import com.tutorial.todo.databinding.ButtonBinding
import com.tutorial.todo.databinding.HeaderViewBinding
import com.tutorial.todo.databinding.ProfileViewHolderBinding
import com.tutorial.todo.utils.ViewBindingKotlinModel

class ProfileController(private val onClicked: () -> Unit) :
    EpoxyController() {

    private var deleteListener: (() -> Unit)? = null

    var categoryList = ArrayList<CategoryAppData>()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {

        HeadEpoxyController("CATEGORIES") { deleteListener?.let { it() } }.id("header").addTo(this)
        categoryList.forEach {
            ProfileEpoxyController(it).id(it.id).addTo(this)
        }

        SaveButton("Add Category", onClicked).id("button").addTo(this)
    }


    fun deleteBump(delete: () -> Unit) {
        deleteListener = delete
    }

    data class ProfileEpoxyController(
        val categoryAppData: CategoryAppData
    ) :
        ViewBindingKotlinModel<ProfileViewHolderBinding>(R.layout.profile_view_holder) {
        override fun ProfileViewHolderBinding.bind() {
            categoryText.text = categoryAppData.name
        }
    }

    data class HeadEpoxyController(val text: String,private val onClicked: () -> Unit) :
        ViewBindingKotlinModel<HeaderViewBinding>(R.layout.header_view) {
        override fun HeaderViewBinding.bind() {
            headerText.text = text
            deleteAll.setOnClickListener {
                onClicked()
            }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

    data class SaveButton(val text: String, val onClicked: () -> Unit) :
        ViewBindingKotlinModel<ButtonBinding>(R.layout.button) {
        override fun ButtonBinding.bind() {
            categorySavebtn.text = text
            categorySavebtn.setOnClickListener { onClicked.invoke() }

        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}

