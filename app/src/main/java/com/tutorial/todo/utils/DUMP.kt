package com.tutorial.todo.utils
/*BottomSheetDrawer
*
*         //REVIEW AND TEST THE SORT FUNCTIONALITY//
      //  viewModel.setUpSorts("")

//        val controller = BottomEpoxyController {
////            ListFragment().controller.currentSort = it
//            viewModel.getCatId(it) //one
//            viewModel.setUpSorts(it.name)
//            dismiss()
//        }
//
//        viewModel.selectedSort.observe(viewLifecycleOwner) {
//            viewModel.setSortedData(it) // two
//        }
//        viewModel.setUpSort.observe(viewLifecycleOwner){
//            controller.sorts = it
//        }
//
//        binding.recyclerView.setControllerAndBuildModels(controller)
////        controller.sort = Sort.values()

 */

/* VM
 /* val _itemsCategories = MutableLiveData<CategoryAppData>()
     val itemsCategory: LiveData<CategoryAppData>
         get() = _itemsCategories
     fun selectedCategory(categoryId: String) {
         val selected = categoryData_LD.value?.find { it.id == categoryId }
         _itemsCategories.postValue(selected)
     }*/



    private val _homeViewStateLiveData = MutableLiveData<HomeViewState>()
    val homeViewStateLiveData: LiveData<HomeViewState>
        get() = _homeViewStateLiveData
    var currentSort: HomeViewState.Sort = HomeViewState.Sort.NONE


    fun updateHome() {
        val allHomeData = ArrayList<HomeViewState.HomeViewData>()
        val allItemsAndCategory = itemsWithCategory.value ?: return

        allItemsAndCategory.forEach {
            allHomeData.add(
                HomeViewState.HomeViewData(it)
            )
        }
        val value  =  HomeViewState(allHomeData, sort = currentSort)
        _homeViewStateLiveData.postValue(value)

    }

    data class HomeViewState(
        val itemList: List<HomeViewData> = emptyList(),
        val sort: Sort = Sort.NONE,
        val isLoading: Boolean = false
    ) {
        data class HomeViewData(
            val ItemsAndCategory: ItemsAndCategory,
        )

        enum class Sort(val displayName: String) {
            NONE("None"),
            CATEGORY("Category"),
            OLDEST("Oldest"),
            NEWEST("Newest")
        }
    }

/////////////////////////////////////////////////////////////////////////////////

private val _homeViewStateLiveData = MutableLiveData<HomeViewState>()
val homeViewStateLiveData: LiveData<HomeViewState>
   get() = _homeViewStateLiveData
var currentSort: HomeViewState.Sort = HomeViewState.Sort.NONE


data class HomeViewState(
   val itemList: List<DataItems<*>> = emptyList(),
   val isLoading: Boolean = false,
   val sort: Sort = Sort.NONE
) {
   data class DataItems<T>(
       val data: T,
       val isHeader: Boolean = false
   )

   enum class Sort(val displayName: String) {
       NONE("None"),
       CATEGORY("Category"),
       OLDEST("Oldest"),
       NEWEST("Newest")
   }
}

private fun updateHomeViewState(allData: List<ItemsAndCategory>) {
   val dataList = ArrayList<HomeViewState.DataItems<*>>()
   when (currentSort) {
       HomeViewState.Sort.NONE -> {
           var currentPriority = 0
           allData.sortedByDescending { it.AppData.priority }.forEach { data ->
               if (data.AppData.priority != currentPriority) {
                   currentPriority = data.AppData.priority
                   val headerItem = HomeViewState.DataItems(
                       data = getHeaderText(currentPriority), isHeader = true
                   )
                   dataList.add(headerItem)
               }

               val dataItem = HomeViewState.DataItems(data = allData)
               dataList.add(dataItem)
           }

       }
       HomeViewState.Sort.CATEGORY -> {
           //ToDo
       }
       HomeViewState.Sort.OLDEST -> {
           //T0Do
       }
       HomeViewState.Sort.NEWEST -> {
           //ToDo
       }
   }

   _homeViewStateLiveData.postValue(
       HomeViewState(
           itemList = dataList,
           isLoading = false,
           sort = currentSort
       )
   )


}



private fun getHeaderText(priority: Int): String {
   return when (priority) {
       1 -> "Low"
       2 -> "Medium"
       else -> "High"
   }
}*/

/* LIST CONTROLLER
* /* if (currentSort == Sort.NONE) {
            allData.sortedByDescending { it.AppData.priority }.forEach { data ->
                if (data.AppData.priority != currentPriority) {
                    currentPriority = data.AppData.priority
                    val priority = data.AppData.priority
                    HeaderViewEpoxyModel(getHeaderText(priority)).id("HeaderText").addTo(this)
                }
                DataEpoxyModel(data, onClicked).id(data.AppData.id).addTo(this)

            }
            return
        }

        if (currentSort == Sort.CATEGORY) {
            allData.sortedBy {
                it.CategoryAppData?.name ?: CategoryAppData.DEFAULT_CATEGORY_ID
            }
                .forEach { data ->
                    if (data.AppData.category != currentCategoryId) {
                        currentCategoryId = data.AppData.category
                        val category =
                            data.CategoryAppData?.name ?: CategoryAppData.DEFAULT_CATEGORY_ID
                        HeaderViewEpoxyModel(category).id("HeaderText").addTo(this)
                    }
                    DataEpoxyModel(data, onClicked).id(data.AppData.id).addTo(this)
                }
            return
        }

        if (currentSort == Sort.OLDEST) {
            HeaderViewEpoxyModel("oldest").id("HeaderText").addTo(this)
            allData.sortedBy { it.AppData.createdAt }.forEach { data ->
                DataEpoxyModel(data, onClicked).id(data.AppData.id).addTo(this)
            }
            return
        }

        if (currentSort == Sort.NEWEST) {
            HeaderViewEpoxyModel("Newest").id("HeaderText").addTo(this)
            allData.sortedByDescending { it.AppData.createdAt }.forEach { data ->
                DataEpoxyModel(data, onClicked).id(data.AppData.id).addTo(this)
            }
            return
        }*/
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//        var currentPriority = 0
//        var currentCategoryId = "No_ID"

//        when (currentSort) {
//            Sort.NONE -> allData.sortedByDescending { it.AppData.priority }.forEach { data ->
//                if (data.AppData.priority != currentPriority) {
//                    currentPriority = data.AppData.priority
//                    val priority = data.AppData.priority
//                    HeaderViewEpoxyModel(getHeaderText(priority)).id("HeaderText").addTo(this)
//                }
//                DataEpoxyModel(data, onClicked).id(data.AppData.id).addTo(this)
//            }
//            Sort.CATEGORY -> {
//
//                allData.sortedBy {
//                    it.CategoryAppData?.name ?: CategoryAppData.DEFAULT_CATGORY_ID
//                }
//                    .forEach { data ->
//                        if (data.AppData.category != currentCategoryId) {
//                            currentCategoryId = data.AppData.category
//                            val category =
//                                data.CategoryAppData?.name ?: CategoryAppData.DEFAULT_CATGORY_ID
//                            HeaderViewEpoxyModel(category).id("HeaderText").addTo(this)
//                        }
//                        DataEpoxyModel(data, onClicked).id(data.AppData.id).addTo(this)
//                    }
//
//            }
//            Sort.OLDEST -> {
//                HeaderViewEpoxyModel("oldest").id("HeaderText").addTo(this)
//                allData.sortedBy { it.AppData.createdAt }.forEach { data ->
//                    DataEpoxyModel(data, onClicked).id(data.AppData.id).addTo(this)
//                }
//            }
//            Sort.NEWEST -> {
//                HeaderViewEpoxyModel("Newest").id("HeaderText").addTo(this)
//                allData.sortedByDescending { it.AppData.createdAt }.forEach { data ->
//                    DataEpoxyModel(data, onClicked).id(data.AppData.id).addTo(this)
//                }
//            }
//        }

/////////////////////////

//    enum class Sort(val displayName: String) {
//        NONE("None"),
//        CATEGORY("Category"),
//        OLDEST("Oldest"),
//        NEWEST("Newest")
//    }


///////////////////////////

//    var allData = ArrayList<ItemsAndCategory>()
//        set(value) {
//            field = value
//            isLoading = false
//            requestModelBuild()
//        }
//

////////////////////////////////////////////////

/*  var homeViewState: ActivityViewModel.HomeViewState =
      ActivityViewModel.HomeViewState(isLoading = true)
      set(value) {
          field = value
          requestModelBuild()
      }

     ////////////////////////////////////////////////////////////////
      if (homeViewState.isLoading) {
           LoadingEpoxyModel().id("Loading").addTo(this)
           return
       }

       if (homeViewState.itemList.isEmpty()) {
           IsEmptyEpoxyModel().id("Empty-List").addTo(this)
           return
       }
       homeViewState.itemList.forEach {
           if (it.isHeader) {
               HeaderViewEpoxyModel(it.data as String).id("header").addTo(this)
               return@forEach
           }
           val itemsAndCategoryData = it.data as ItemsAndCategory
           DataEpoxyModel(itemsAndCategoryData, onClicked).id(itemsAndCategoryData.AppData.id)
               .addTo(this)

       }*/


/*  if (isLoading) {
           LoadingEpoxyModel().id("Loading").addTo(this)
           return
       }

       if (allData.isEmpty()) {
           IsEmptyEpoxyModel().id("Empty").addTo(this)
           return
       }


       var currentPriority = 0
       var currentCategoryId = "No_ID"

       when (currentSort) {
           Sort.NONE -> allData.sortedByDescending { it.AppData.priority }.forEach { data ->
               if (data.AppData.priority != currentPriority) {
                   currentPriority = data.AppData.priority
                   val priority = data.AppData.priority
                   HeaderViewEpoxyModel(getHeaderText(priority)).id("HeaderText").addTo(this)
               }
               DataEpoxyModel(data, onClicked).id(data.AppData.id).addTo(this)
           }
           Sort.CATEGORY -> {

               allData.sortedBy { it.CategoryAppData?.name ?: CategoryAppData.DEFAULT_CATGORY_ID }
                   .forEach { data ->
                       if (data.AppData.category != currentCategoryId) {
                           currentCategoryId = data.AppData.category
                           val category =
                               data.CategoryAppData?.name ?: CategoryAppData.DEFAULT_CATGORY_ID
                           HeaderViewEpoxyModel(category).id("HeaderText").addTo(this)
                       }
                       DataEpoxyModel(data, onClicked).id(data.AppData.id).addTo(this)
                   }

           }
           Sort.OLDEST -> {
               HeaderViewEpoxyModel("oldest").id("HeaderText").addTo(this)
               allData.sortedBy { it.AppData.createdAt }.forEach { data ->
                   DataEpoxyModel(data, onClicked).id(data.AppData.id).addTo(this)
               }
           }
           Sort.NEWEST -> {
               HeaderViewEpoxyModel("Newest").id("HeaderText").addTo(this)
               allData.sortedByDescending { it.AppData.createdAt }.forEach { data ->
                   DataEpoxyModel(data, onClicked).id(data.AppData.id).addTo(this)
               }
           }
       }*/
 */
