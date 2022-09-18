package com.tutorial.todo.model.epoxycontroller

import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.tutorial.todo.Interface.OnClicked
import com.tutorial.todo.Interface.Sort
import com.tutorial.todo.R
import com.tutorial.todo.database.dao.ItemsAndCategory
import com.tutorial.todo.databinding.EmptyLayoutBinding
import com.tutorial.todo.databinding.HeaderViewBinding
import com.tutorial.todo.databinding.ListViewHolderBinding
import com.tutorial.todo.databinding.LoadingBinding
import com.tutorial.todo.utils.ViewBindingKotlinModel
import java.text.SimpleDateFormat
import java.util.*

class ListController(private val onClicked: OnClicked) : EpoxyController() {


    private var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var allData : List<ItemsAndCategory> = emptyList()
    set(value) {
        field = value
        isLoading = false
        requestModelBuild()
    }

    var currentSort: Sort = Sort.NONE
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {

        var currentPriority = 0
        var currentCategoryId = "No_ID"

        if (isLoading) {
            LoadingEpoxyModel().id("Loading").addTo(this)
            return
        }

        if (allData.isEmpty()) {
            IsEmptyEpoxyModel().id("Empty").addTo(this)
            return
        }

        allData.forEach { data->
            DataEpoxyModel(data, onClicked).id(data.AppData.id).addTo(this)
        }


    }


    data class DataEpoxyModel(
        val data: ItemsAndCategory, val OnClicked: OnClicked
    ) : ViewBindingKotlinModel<ListViewHolderBinding>(R.layout.list_view_holder) {
        override fun ListViewHolderBinding.bind() {
            titleText.text = data.AppData.title
            categoryText.text = data.CategoryAppData?.name
            if (data.AppData.description == null) {
                descText.isGone = true
            } else {
                descText.isVisible = true
                descText.text = data.AppData.description
            }

            val color = when (data.AppData.priority) {
                1 -> R.color.teal_200
                2 -> R.color.orange
                3 -> R.color.red
                else -> R.color.teal_200
            }


            val calender = Calendar.getInstance().apply {
                timeInMillis = data.AppData.createdAt
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            dateTv.text = dateFormat.format(calender.time)


            priorityText.setBackgroundColor(ContextCompat.getColor(root.context, color))

            root.setOnClickListener {
                OnClicked.itemClicked(data.AppData)
            }

        }
    }

    class LoadingEpoxyModel : ViewBindingKotlinModel<LoadingBinding>(R.layout.loading) {
        override fun LoadingBinding.bind() {

        }
    }

    class IsEmptyEpoxyModel :
        ViewBindingKotlinModel<EmptyLayoutBinding>(R.layout.empty_layout) {
        override fun EmptyLayoutBinding.bind() {
            //TODO
        }
    }

    class HeaderViewEpoxyModel(private val header: String) :
        ViewBindingKotlinModel<HeaderViewBinding>(R.layout.header_view) {
        override fun HeaderViewBinding.bind() {
            headerText.text = header
        }
    }


    private fun getHeaderText(priority: Int): String {
        return when (priority) {
            1 -> "Low"
            2 -> "Medium"
            else -> "High"
        }
    }
}


