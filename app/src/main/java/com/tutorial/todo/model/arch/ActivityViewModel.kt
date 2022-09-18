package com.tutorial.todo.model.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutorial.todo.Interface.Sort
import com.tutorial.todo.database.AppData
import com.tutorial.todo.database.AppDataBase
import com.tutorial.todo.database.CategoryAppData
import com.tutorial.todo.database.dao.ItemsAndCategory
import com.tutorial.todo.utils.EventHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ActivityViewModel : ViewModel() {
    private lateinit var appRepository: ActivityRepository
    private val _allData = MutableLiveData<List<AppData>>()
    val allData: LiveData<List<AppData>> get() = _allData
    private val _categoryData = MutableLiveData<List<CategoryAppData>>()
    val categoryData: LiveData<List<CategoryAppData>> get() = _categoryData
    private val _itemsWithCategory = MutableLiveData<List<ItemsAndCategory>>()
    val itemsWithCategory: LiveData<List<ItemsAndCategory>> get() = _itemsWithCategory
    private val _processComplete = MutableLiveData<EventHandler<Boolean>>()
    val processComplete: LiveData<EventHandler<Boolean>> get() = _processComplete



    // region SET UP SORTS
    val mainSort = MutableLiveData<Sort>()

    data class SortViewState(
        val allSort: List<EditedSort> = emptyList()
    ) {
        data class EditedSort(
            val sorts: Sort,
            var isSelected: Boolean = false
        )
    }

    private val _setUpSort = MutableLiveData<SortViewState>()
    val setUpSort: LiveData<SortViewState>
        get() = _setUpSort

    fun setUpSorts(id: String) {
        val allList = ArrayList<SortViewState.EditedSort>()
        val sort = Sort.values()
        sort.forEach {
            allList.add(
                SortViewState.EditedSort(it, it.name == id)
            )
        }

        val viewState = SortViewState(allList)
        _setUpSort.value = viewState
    }

    //endregion


    //region SORT ALL DATA
    private val _selectedSort = MutableLiveData<Sort>()
    val selectedSort: LiveData<Sort>
        get() = _selectedSort


    fun getCatId(sort: Sort) {
        _selectedSort.value = sort

    }

    fun setSortedData(sort: Sort) {
        val filter: List<ItemsAndCategory>
        when (sort) {

            Sort.OLDEST -> {

                filter = _itemsWithCategory.value!!.sortedBy {
                    it.AppData.createdAt
                }

            }
            Sort.NEWEST -> {
                filter = _itemsWithCategory.value!!.sortedByDescending {
                    it.AppData.createdAt
                }

            }
            else -> {
                filter = _itemsWithCategory.value!!
            }

        }
        _itemsWithCategory.value = filter

    }
    // endregion

    // region AddItemsAndCategoryViewState
    private val _addICViewState = MutableLiveData<AddICViewState>()
    val addICViewState: LiveData<AddICViewState>
        get() = _addICViewState


    fun onSelectedCategory(categoryId: String, showLoading: Boolean = false) {
        if (showLoading) {
            val isLoading = AddICViewState(isLoading = true)
            _addICViewState.value = isLoading
        }
        val categoryItems = _categoryData.value ?: return


        val addICViewStateList = ArrayList<AddICViewState.AllItemsViewState>()


        addICViewStateList.add(
            AddICViewState.AllItemsViewState(
                CategoryAppData.defaultCategory(),
                isSelected = CategoryAppData.DEFAULT_CATEGORY_ID == categoryId
            )
        )
        categoryItems.forEach {
            addICViewStateList.add(
                AddICViewState.AllItemsViewState(it, isSelected = it.id == categoryId)
            )
        }


        val viewState = AddICViewState(allItemsViewState = addICViewStateList)
        _addICViewState.postValue(viewState)
    }


    data class AddICViewState(
        val isLoading: Boolean = false,
        val allItemsViewState: List<AllItemsViewState> = emptyList()
    ) {
        data class AllItemsViewState(
            val categoryAppData: CategoryAppData = CategoryAppData(),
            val isSelected: Boolean = false
        )

        fun getSelectedCategory(): String {
            return allItemsViewState.find { it.isSelected }?.categoryAppData?.id
                ?: CategoryAppData.DEFAULT_CATEGORY_ID
        }
    }
//endregion

    fun init(appDataBase: AppDataBase) {
        appRepository = ActivityRepository(appDataBase)
        viewModelScope.launch {
            appRepository.getAllData().collect { items ->
                _allData.postValue(items)
            }
        }

        viewModelScope.launch {
            appRepository.getAllCategory().collect { category ->
                _categoryData.postValue(category)
            }
        }

        viewModelScope.launch {
            appRepository.getItemsWithCategory().collect { category ->
                _itemsWithCategory.postValue(category)
            }
        }
    }

    //region AppData
    fun insert(appData: AppData) {
        viewModelScope.launch {
            appRepository.insert(appData)
            _processComplete.postValue(EventHandler(true))
        }
    }

    fun delete(appData: AppData) {
        viewModelScope.launch { appRepository.delete(appData) }
    }

    fun update(appData: AppData) {
        viewModelScope.launch {
            appRepository.update(appData)
            _processComplete.postValue(EventHandler(true))
        }
    }

    fun deleteAllItems() {
        viewModelScope.launch {
            appRepository.deleteAllItems()
        }
    }

//endregion

//region CategoryData

    fun insert(categoryAppData: CategoryAppData) {
        viewModelScope.launch {
            appRepository.insert(categoryAppData)
            _processComplete.postValue(EventHandler(true))
        }
    }

    fun delete(categoryAppData: CategoryAppData) {
        viewModelScope.launch { appRepository.delete(categoryAppData) }
    }

    fun update(categoryAppData: CategoryAppData) {
        viewModelScope.launch {
            appRepository.update(categoryAppData)
            _processComplete.postValue(EventHandler(true))
        }
    }

    fun deleteAllCategory() {
        viewModelScope.launch {
            appRepository.deleteAllCategory()
        }
    }


//endregion

}



